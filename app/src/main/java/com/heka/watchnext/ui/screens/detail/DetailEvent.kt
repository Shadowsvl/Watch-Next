package com.heka.watchnext.ui.screens.detail

import com.heka.watchnext.model.WatchMedia

sealed class DetailEvent {
    data class OnWatchMediaChanged(val watchMedia: WatchMedia): DetailEvent()
    data class OnListButtonClicked(val watchMedia: WatchMedia): DetailEvent()
    data class OnBottomListButtonClicked(val watchMedia: WatchMedia): DetailEvent()
}
