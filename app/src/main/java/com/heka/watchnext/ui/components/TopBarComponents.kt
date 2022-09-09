package com.heka.watchnext.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heka.watchnext.R
import com.heka.watchnext.ui.theme.BaseDP
import com.heka.watchnext.ui.theme.WatchNextTheme

@Composable
fun WatchNextScreenTopBar(
    @StringRes screenLabelId: Int,
    onBack: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(BaseDP),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.surface,
                        Color.Transparent
                    ),
                    startY = 70f
                )
            )
    ) {
        BackButton(
            modifier = Modifier
                .size(70.dp)
                .padding(BaseDP)
        ) {
            onBack()
        }
        Text(
            text = stringResource(id = screenLabelId),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview("Watch next top bar")
@Composable
private fun WatchNextTopBarPreview() {
    WatchNextTheme {
        Surface {
            WatchNextScreenTopBar(
                screenLabelId = R.string.screen_label_movies,
                onBack = {}
            )
        }
    }
}