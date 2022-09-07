package com.heka.watchnext.ui.screens.home

import com.heka.watchnext.model.WatchMedia

sealed class HomeEvent {
    object RefreshContent: HomeEvent()
    data class OnWatchMediaChanged(val watchMedia: WatchMedia): HomeEvent()
    data class OnListButtonClicked(val watchMedia: WatchMedia): HomeEvent()
}
