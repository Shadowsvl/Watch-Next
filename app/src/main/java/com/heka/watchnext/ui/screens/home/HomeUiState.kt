package com.heka.watchnext.ui.screens.home

import com.heka.watchnext.data.Message
import com.heka.watchnext.model.WatchMedia
import com.heka.watchnext.model.WatchSection

data class HomeUiState(
    val myListLatest: List<WatchMedia> = emptyList(),
    val watchSections: List<WatchSection> = emptyList(),
    val watchMedia: WatchMedia? = null,
    val isMediaAdded: Boolean = false,
    val authErrorMessage: Message? = null,
    val isRefreshing: Boolean = false,
    val loading: Boolean = true
)
