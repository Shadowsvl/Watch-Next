package com.heka.watchnext.data

sealed class DataResult<out R> {
    data class Success<out T>(val data: T?) : DataResult<T>()
    data class Error(val message: Message, val tag: String = "") : DataResult<Nothing>()
}

fun <T> DataResult<T>.successOr(fallback: T): T {
    return (this as? DataResult.Success<T>)?.data ?: fallback
}
