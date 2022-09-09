package com.heka.watchnext.ui.screens.my_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heka.watchnext.data.repository.WatchMediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    private val watchMediaRepository: WatchMediaRepository
): ViewModel() {

    private val vmState = MutableStateFlow(MyListUiState())

    val uiState = vmState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        vmState.value
    )

    private val actionChannel = Channel<MyListAction>()
    val action = actionChannel.receiveAsFlow()

    private var mediaAddedJob: Job? = null

    init {
        viewModelScope.launch {
            watchMediaRepository.getMyList().collect { mediaList ->
                vmState.update { it.copy( myWatchMediaList = mediaList, loading = false ) }
            }
        }
    }

    fun onEvent(event: MyListEvent) {
        when(event) {
            is MyListEvent.OnWatchMediaChanged -> {
                vmState.update { it.copy( watchMedia = event.watchMedia ) }
                mediaAddedJob?.cancel()
                mediaAddedJob = viewModelScope.launch {
                    watchMediaRepository.isMediaAdded(event.watchMedia.id).collect { added ->
                        vmState.update { it.copy( isMediaAdded = added ) }
                    }
                }
                viewModelScope.launch { actionChannel.send(MyListAction.ShowBottomSheet) }
            }
            is MyListEvent.OnListButtonClicked -> {
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

sealed class MyListAction {
    object ShowBottomSheet: MyListAction()
}