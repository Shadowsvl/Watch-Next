package com.heka.watchnext.data

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class Message {
    data class Text(val value: String): Message()
    class StringResource(@StringRes val resId: Int, vararg val args: Any): Message()
}

@Composable
fun Message.asString(): String {
    return when(this) {
        is Message.Text -> value
        is Message.StringResource -> stringResource(resId, *args)
    }
}

fun Message.asString(context: Context): String {
    return when(this) {
        is Message.Text -> value
        is Message.StringResource -> context.getString(resId, *args)
    }
}
