package com.heka.watchnext.data.remote.tmdb_api.dto

import com.google.gson.annotations.SerializedName

data class WatchMediaErrorDto(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("status_message")
    val statusMessage: String?
)