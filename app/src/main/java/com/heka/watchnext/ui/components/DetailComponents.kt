package com.heka.watchnext.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.heka.watchnext.data.fake.fakeWatchMovie1
import com.heka.watchnext.model.WatchMedia
import com.heka.watchnext.ui.theme.WatchNextTheme

@Composable
fun ScoreIndicator(
    watchMedia: WatchMedia,
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        CircularProgressIndicator(progress = 1.0f, color = Color.LightGray)
        CircularProgressIndicator(progress = watchMedia.scoreProgress, color = MaterialTheme.colors.secondary)
        Text(
            text = watchMedia.score,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview("Score indicator", showBackground = true)
@Composable
private fun ScoreIndicatorPreview() {
    WatchNextTheme {
        Surface {
            ScoreIndicator(watchMedia = fakeWatchMovie1)
        }
    }
}