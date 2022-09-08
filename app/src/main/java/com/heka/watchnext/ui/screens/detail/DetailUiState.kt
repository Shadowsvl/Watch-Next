package com.heka.watchnext.ui.screens.detail

import com.heka.watchnext.model.WatchMedia

data class DetailUiState(
    val watchMedia: WatchMedia? = null,
    val isMediaAdded: Boolean = false,
    val bottomWatchMedia: WatchMedia? = null,
    val bottomIsMediaAdded: Boolean = false,
    val similarMedia: List<WatchMedia> = emptyList(),
    val loading: Boolean = true
)
