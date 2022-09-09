package com.heka.watchnext.ui.screens.my_list

import com.heka.watchnext.model.WatchMedia

data class MyListUiState(
    val myWatchMediaList: List<WatchMedia> = emptyList(),
    val watchMedia: WatchMedia? = null,
    val isMediaAdded: Boolean = false,
    val loading: Boolean = true
)
