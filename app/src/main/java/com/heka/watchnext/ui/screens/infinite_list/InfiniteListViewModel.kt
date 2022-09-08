package com.heka.watchnext.ui.screens.infinite_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heka.watchnext.R
import com.heka.watchnext.data.repository.WatchMediaRepository
import com.heka.watchnext.model.MediaType
import com.heka.watchnext.ui.Arguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfiniteListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val watchMediaRepository: WatchMediaRepository
): ViewModel() {

    private val vmState = MutableStateFlow(InfiniteListUiState())

    val uiState = vmState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        vmState.value
    )

    private val actionChannel = Channel<InfiniteListAction>()
    val action = actionChannel.receiveAsFlow()

    private var mediaAddedJob: Job? = null

    init {
        viewModelScope.launch {
            watchMediaRepository.clearMedia()
            val mediaTypeName = savedStateHandle.get<String>(Arguments.MEDIA_TYPE_NAME) ?: return@launch
            val mediaType = MediaType.valueOf(mediaTypeName)
            vmState.update {
                it.copy(
                    screenLabelId = when(mediaType) {
                        MediaType.Movie -> R.string.screen_label_movies
                        MediaType.Tv -> R.string.screen_label_series
                    }
                )
            }
            watchMediaRepository.getInfiniteMedia(mediaType).collect { mediaList ->
                vmState.update { it.copy( infiniteWatchMediaList = mediaList ) }
            }
        }
    }

    fun onEvent(event: InfiniteListEvent) {
        when(event) {
            InfiniteListEvent.RequestMoreMedia -> {
                viewModelScope.launch { watchMediaRepository.requestMoreMedia() }
            }
            is InfiniteListEvent.OnWatchMediaChanged -> {
                vmState.update { it.copy( watchMedia = event.watchMedia ) }
                mediaAddedJob?.cancel()
                mediaAddedJob = viewModelScope.launch {
                    watchMediaRepository.isMediaAdded(event.watchMedia.id).collect { added ->
                        vmState.update { it.copy( isMediaAdded = added ) }
                    }
                }
                viewModelScope.launch { actionChannel.send(InfiniteListAction.ShowBottomSheet) }
            }
            is InfiniteListEvent.OnListButtonClicked -> {
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
}

sealed class InfiniteListAction {
    object ShowBottomSheet: InfiniteListAction()
}