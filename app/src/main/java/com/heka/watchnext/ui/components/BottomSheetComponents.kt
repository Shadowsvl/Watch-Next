package com.heka.watchnext.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.heka.watchnext.data.fake.fakeWatchMovie1
import com.heka.watchnext.model.WatchMedia
import com.heka.watchnext.ui.theme.*

@Composable
fun WatchMediaSheet(
    watchMedia: WatchMedia
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(BaseDP),
        modifier = Modifier
            .fillMaxWidth()
            .padding(BaseDP)
    ) {
        PosterCard(
            posterUrl = watchMedia.posterUrl,
            posterWidth = MiniPosterWidth,
            posterHeight = MiniPosterHeight
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(SpaceDP),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = watchMedia.title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = watchMedia.releaseDate,
                style = MaterialTheme.typography.caption
            )
            Text(
                text = watchMedia.overview,
                style = MaterialTheme.typography.body1,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview("Watch media sheet", showBackground = true)
@Composable
private fun WatchMediaSheetPreview() {
    WatchNextTheme {
        Surface {
            WatchMediaSheet(watchMedia = fakeWatchMovie1)
        }
    }
}