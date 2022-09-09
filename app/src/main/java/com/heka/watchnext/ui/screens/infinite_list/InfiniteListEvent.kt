package com.heka.watchnext.ui.screens.infinite_list

import com.heka.watchnext.model.WatchMedia

sealed class InfiniteListEvent {
    object RequestMoreMedia: InfiniteListEvent()
    data class OnWatchMediaChanged(val watchMedia: WatchMedia): InfiniteListEvent()
    data class OnListButtonClicked(val watchMedia: WatchMedia): InfiniteListEvent()
}
