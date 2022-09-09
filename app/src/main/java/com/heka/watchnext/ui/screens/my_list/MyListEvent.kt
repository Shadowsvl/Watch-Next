package com.heka.watchnext.ui.screens.my_list

import com.heka.watchnext.model.WatchMedia

sealed class MyListEvent {
    data class OnWatchMediaChanged(val watchMedia: WatchMedia): MyListEvent()
    data class OnListButtonClicked(val watchMedia: WatchMedia): MyListEvent()
}
