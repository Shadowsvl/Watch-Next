package com.heka.watchnext.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.heka.watchnext.R
import com.heka.watchnext.ui.theme.BaseDP
import com.heka.watchnext.ui.theme.SpaceDP
import com.heka.watchnext.ui.theme.WatchNextTheme

@Composable
fun NotifyApiKeyValidationError(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(SpaceDP),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(BaseDP)
        ) {
            Icon(
                imageVector = Icons.Outlined.Error,
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.notify_invalid_api_key),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview("Notify Api key validation error")
@Composable
private fun NotifyApiKeyValidationErrorPreview() {
    WatchNextTheme {
        Surface {
            NotifyApiKeyValidationError(
                errorMessage = "Invalid key"
            )
        }
    }
}