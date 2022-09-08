package com.heka.watchnext.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heka.watchnext.data.repository.WatchMediaRepository
import com.heka.watchnext.data.successOr
import com.heka.watchnext.model.MediaType
import com.heka.watchnext.ui.Arguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val watchMediaRepository: WatchMediaRepository
): ViewModel() {

    private val vmState = MutableStateFlow(DetailUiState())

    val uiState = vmState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        vmState.value
    )

    private val actionChannel = Channel<DetailAction>()
    val action = actionChannel.receiveAsFlow()

    private var mediaAddedJob: Job? = null

    init {
        viewModelScope.launch {
            val mediaId = savedStateHandle.get<Long>(Arguments.MEDIA_ID) ?: run {
                vmState.update { it.copy( loading = false ) }
                return@launch
            }
            val mediaTypeName = savedStateHandle.get<String>(Arguments.MEDIA_TYPE_NAME) ?: run {
                vmState.update { it.copy( loading = false ) }
                return@launch
            }
            val mediaType = MediaType.valueOf(mediaTypeName)

            launch {
                watchMediaRepository.isMediaAdded(mediaId).collect { added ->
                    vmState.update { it.copy( isMediaAdded = added ) }
                }
            }

            val watchMedia = async {
                watchMediaRepository.getMedia(mediaId) ?: when(mediaType) {
                    MediaType.Movie -> watchMediaRepository.getMovie(mediaId)
                    MediaType.Tv -> watchMediaRepository.getTv(mediaId)
                }.successOr(null)
            }

            val similarMedia = async {
                when(mediaType) {
                    MediaType.Movie -> watchMediaRepository.getSimilarMovies(mediaId)
                    MediaType.Tv -> watchMediaRepository.getSimilarSeries(mediaId)
                }.successOr(emptyList())
            }

            vmState.update { it.copy( watchMedia = watchMedia.await(), similarMedia = similarMedia.await(), loading = false ) }
        }
    }

    fun onEvent(event: DetailEvent) {
        when(event) {
            is DetailEvent.OnWatchMediaChanged -> {
                vmState.update { it.copy( bottomWatchMedia = event.watchMedia ) }
                mediaAddedJob?.cancel()
                mediaAddedJob = viewModelScope.launch {
                    watchMediaRepository.isMediaAdded(event.watchMedia.id).collect { added ->
                        vmState.update { it.copy( bottomIsMediaAdded = added ) }
                    }
                }
                viewModelScope.launch { actionChannel.send(DetailAction.ShowBottomSheet) }
            }
            is DetailEvent.OnListButtonClicked -> {
                viewModelScope.launch {
                    if (vmState.value.isMediaAdded) {
                        watchMediaRepository.removeMedia(event.watchMedia)
                    } else {
                        watchMediaRepository.addMedia(event.watchMedia)
                    }
                }
            }
            is DetailEvent.OnBottomListButtonClicked -> {
                viewModelScope.launch {
                    if (vmState.value.bottomIsMediaAdded) {
                        watchMediaRepository.removeMedia(event.watchMedia)
                    } else {
                        watchMediaRepository.addMedia(event.watchMedia)
                    }
                }
            }
        }
    }

}

sealed class DetailAction {
    object ShowBottomSheet: DetailAction()
}