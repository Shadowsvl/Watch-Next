package com.heka.watchnext.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heka.watchnext.R
import com.heka.watchnext.data.repository.WatchMediaRepository
import com.heka.watchnext.data.successOr
import com.heka.watchnext.model.WatchSection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val watchMediaRepository: WatchMediaRepository
): ViewModel() {

    private val vmState = MutableStateFlow(SearchUiState())

    val uiState = vmState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        vmState.value
    )

    private val actionChannel = Channel<SearchAction>()
    val action = actionChannel.receiveAsFlow()

    private var mediaAddedJob: Job? = null
    private var searchMediaJob: Job? = null

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.OnSearchQueryChanged -> {
                vmState.update { it.copy( search = event.query, emptyResultMessageVisible = false ) }
                searchMediaJob?.cancel()
                searchMedia()
            }
            is SearchEvent.OnWatchMediaChanged -> {
                vmState.update { it.copy( watchMedia = event.watchMedia ) }
                mediaAddedJob?.cancel()
                mediaAddedJob = viewModelScope.launch {
                    watchMediaRepository.isMediaAdded(event.watchMedia.id).collect { added ->
                        vmState.update { it.copy( isMediaAdded = added ) }
                    }
                }
                viewModelScope.launch { actionChannel.send(SearchAction.ShowBottomSheet) }
            }
            is SearchEvent.OnListButtonClicked -> {
                viewModelScope.launch {
                    if (vmState.value.isMediaAdded) {
                        watchMediaRepository.removeMedia(event.watchMedia)
                    } else {
                        watchMediaRepository.addMedia(event.watchMedia)
                    }
                }
            }
        }
    }

    private fun searchMedia() {
        val query = vmState.value.search
        searchMediaJob = viewModelScope.launch {

            delay(500)

            if (query.isBlank()) {
                vmState.update { it.copy( resultSections = emptyList() ) }
                return@launch
            }
            vmState.update { it.copy( loading = true ) }

            val movies = async { watchMediaRepository.searchMovies(query).successOr(emptyList()) }
            val series = async { watchMediaRepository.searchSeries(query).successOr(emptyList()) }
            val myList = async { watchMediaRepository.searchMyList(query) }

            val resultSections = mutableListOf<WatchSection>()

            movies.await().let {
                if (it.isNotEmpty()) resultSections.add(
                    WatchSection(
                        labelId = R.string.section_search_result_movies,
                        it
                    )
                )
            }
            series.await().let {
                if (it.isNotEmpty()) resultSections.add(
                    WatchSection(
                        labelId = R.string.section_search_result_series,
                        it
                    )
                )
            }
            myList.await().let {
                if (it.isNotEmpty()) resultSections.add(
                    WatchSection(
                        labelId = R.string.section_search_result_my_list,
                        it
                    )
                )
            }

            vmState.update { it.copy( resultSections = resultSections, emptyResultMessageVisible = true, loading = false ) }
        }
    }
}

sealed class SearchAction {
    object ShowBottomSheet: SearchAction()
}