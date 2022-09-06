package com.heka.watchnext.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heka.watchnext.R
import com.heka.watchnext.data.repository.WatchMediaRepository
import com.heka.watchnext.data.successOr
import com.heka.watchnext.model.WatchSection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
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

    init {
        refreshContent()
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.OnWatchMediaChanged -> {
                vmState.update { it.copy( watchMedia = event.watchMedia ) }
                viewModelScope.launch { actionChannel.send(HomeAction.ShowBottomSheet) }
            }
            HomeEvent.RefreshContent -> {
                vmState.update { it.copy( isRefreshing = true ) }
                refreshContent()
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

            cinemaMoviesResult.await().successOr(emptyList()).let {
                if (it.isNotEmpty()) watchSections.add(
                    WatchSection(
                        labelId = R.string.section_cinema_movies,
                        watchMediaList = it
                    )
                )
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

            vmState.update { it.copy( watchSections = watchSections, isRefreshing = false, loading = false ) }
        }
    }
}

sealed class HomeAction {
    object ShowBottomSheet: HomeAction()
}