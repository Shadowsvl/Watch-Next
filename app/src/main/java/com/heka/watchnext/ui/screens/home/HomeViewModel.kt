package com.heka.watchnext.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heka.watchnext.R
import com.heka.watchnext.data.DataResult
import com.heka.watchnext.data.repository.WatchMediaRepository
import com.heka.watchnext.data.successOr
import com.heka.watchnext.model.WatchSection
import com.heka.web_helper_kt.HttpError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val watchMediaRepository: WatchMediaRepository
): ViewModel() {

    private val vmState = MutableStateFlow(HomeUiState())

    val uiState = vmState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        vmState.value
    )

    private val actionChannel = Channel<HomeAction>()
    val action = actionChannel.receiveAsFlow()

    private var mediaAddedJob: Job? = null

    init {
        viewModelScope.launch {
            watchMediaRepository.getMyListLatest().collectLatest { latest ->
                vmState.update { it.copy( myListLatest = latest ) }
            }
        }
        refreshContent()
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.OnWatchMediaChanged -> {
                vmState.update { it.copy( watchMedia = event.watchMedia ) }
                mediaAddedJob?.cancel()
                mediaAddedJob = viewModelScope.launch {
                    watchMediaRepository.isMediaAdded(event.watchMedia.id).collect { added ->
                        vmState.update { it.copy( isMediaAdded = added ) }
                    }
                }
                viewModelScope.launch { actionChannel.send(HomeAction.ShowBottomSheet) }
            }
            HomeEvent.RefreshContent -> {
                vmState.update { it.copy( isRefreshing = true ) }
                refreshContent()
            }
            is HomeEvent.OnListButtonClicked -> {
                viewModelScope.launch {
                    if (vmState.value.isMediaAdded) {
                        watchMediaRepository.removeMedia(event.watchMedia)
                    } else {
                        watchMediaRepository.addMedia(event.watchMedia)
                        delay(200)
                        actionChannel.send(HomeAction.AddListStateAnimation)
                    }
                }
            }
        }
    }

    private fun refreshContent() {
        viewModelScope.launch {
            val cinemaMoviesResult = async { watchMediaRepository.getCinemaMovies() }
            val latestMoviesResult = async { watchMediaRepository.getLatestMovies() }
            val trendingMoviesResult = async { watchMediaRepository.getTrendingMovies() }
            val onAirSeriesResult = async { watchMediaRepository.getOnAirSeries() }
            val latestSeriesResult = async { watchMediaRepository.getLatestSeries() }
            val trendingSeriesResult = async { watchMediaRepository.getTrendingSeries() }

            val watchSections = mutableListOf<WatchSection>()

            when(val result = cinemaMoviesResult.await()) {
                is DataResult.Success -> {
                    val mediaList = result.successOr(emptyList())
                    if (mediaList.isNotEmpty()) watchSections.add(
                        WatchSection(
                            labelId = R.string.section_cinema_movies,
                            watchMediaList = mediaList
                        )
                    )
                }
                is DataResult.Error -> {
                    if (result.tag == HttpError.Unauthorized.name) {
                        vmState.update {
                            it.copy(
                                authErrorMessage = result.message,
                                isRefreshing = false,
                                loading = false
                            )
                        }
                        return@launch
                    }
                }
            }
            latestMoviesResult.await().successOr(emptyList()).let {
                if (it.isNotEmpty()) watchSections.add(
                    WatchSection(
                        labelId = R.string.section_latest_movies,
                        watchMediaList = it
                    )
                )
            }
            trendingMoviesResult.await().successOr(emptyList()).let {
                if (it.isNotEmpty()) watchSections.add(
                    WatchSection(
                        labelId = R.string.section_trending_movies,
                        watchMediaList = it
                    )
                )
            }
            onAirSeriesResult.await().successOr(emptyList()).let {
                if (it.isNotEmpty()) watchSections.add(
                    WatchSection(
                        labelId = R.string.section_on_air_series,
                        watchMediaList = it
                    )
                )
            }
            latestSeriesResult.await().successOr(emptyList()).let {
                if (it.isNotEmpty()) watchSections.add(
                    WatchSection(
                        labelId = R.string.section_latest_series,
                        watchMediaList = it
                    )
                )
            }
            trendingSeriesResult.await().successOr(emptyList()).let {
                if (it.isNotEmpty()) watchSections.add(
                    WatchSection(
                        labelId = R.string.section_trending_series,
                        watchMediaList = it
                    )
                )
            }

            vmState.update {
                it.copy(
                    watchSections = watchSections,
                    authErrorMessage = null,
                    isRefreshing = false,
                    loading = false
                )
            }
        }
    }
}

sealed class HomeAction {
    object ShowBottomSheet: HomeAction()
    object AddListStateAnimation: HomeAction()
}