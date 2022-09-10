package com.heka.watchnext.ui.screens.search

import com.heka.watchnext.model.WatchMedia
import com.heka.watchnext.model.WatchSection

data class SearchUiState(
    val resultSections: List<WatchSection> = emptyList(),
    val watchMedia: WatchMedia? = null,
    val isMediaAdded: Boolean = false,
    val search: String = "",
    val emptyResultMessageVisible: Boolean = false,
    val loading: Boolean = false
)
