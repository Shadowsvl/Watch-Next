package com.heka.watchnext.model

import androidx.annotation.StringRes

data class WatchSection(
    @StringRes val labelId: Int,
    val watchMediaList: List<WatchMedia>
)
