package com.heka.watchnext.ui.screens.search

import com.heka.watchnext.model.WatchMedia

sealed class SearchEvent {
    data class OnSearchQueryChanged(val query: String): SearchEvent()
    data class OnWatchMediaChanged(val watchMedia: WatchMedia): SearchEvent()
    data class OnListButtonClicked(val watchMedia: WatchMedia): SearchEvent()
}
