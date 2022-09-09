package com.heka.watchnext.ui.screens.infinite_list

import androidx.annotation.StringRes
import com.heka.watchnext.R
import com.heka.watchnext.model.WatchMedia

data class InfiniteListUiState(
    @StringRes val screenLabelId: Int = R.string.screen_label_movies,
    val infiniteWatchMediaList: List<WatchMedia> = emptyList(),
    val watchMedia: WatchMedia? = null,
    val isMediaAdded: Boolean = false
)
