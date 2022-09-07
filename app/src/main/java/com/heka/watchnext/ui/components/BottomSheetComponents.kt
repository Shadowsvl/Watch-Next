package com.heka.watchnext.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heka.watchnext.R
import com.heka.watchnext.data.fake.fakeWatchMovie1
import com.heka.watchnext.model.MediaType
import com.heka.watchnext.model.WatchMedia
import com.heka.watchnext.ui.theme.*

@Composable
fun WatchMediaSheet(
    watchMedia: WatchMedia,
    isMediaAdded: Boolean,
    onListButtonClicked: (WatchMedia) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(BaseDP),
        modifier = Modifier
            .fillMaxWidth()
            .padding(BaseDP)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(BaseDP),
            modifier = Modifier.fillMaxWidth()
        ) {
            PosterCard(
                posterUrl = watchMedia.posterUrl,
                posterWidth = MiniPosterWidth,
                posterHeight = MiniPosterHeight
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(MiniPosterHeight)
            ) {
                Text(
                    text = watchMedia.title,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(horizontalArrangement = Arrangement.spacedBy(BaseDP)) {
                    Text(
                        text = watchMedia.releaseDateFormatted,
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = when(watchMedia.mediaType) {
                            MediaType.Movie -> stringResource(id = R.string.media_type_movie)
                            MediaType.Tv -> stringResource(id = R.string.media_type_tv)
                        },
                        style = MaterialTheme.typography.caption
                    )
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    ScoreIndicator(
                        watchMedia = watchMedia,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )
                    MyListButton(
                        isMediaAdded = isMediaAdded,
                        modifier = Modifier
                            .size(44.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        onListButtonClicked(watchMedia)
                    }
                }
            }
        }
        Text(
            text = watchMedia.overview,
            style = MaterialTheme.typography.body1,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview("Watch media sheet", showBackground = true)
@Composable
private fun WatchMediaSheetPreview() {
    WatchNextTheme {
        Surface {
            WatchMediaSheet(
                watchMedia = fakeWatchMovie1,
                isMediaAdded = false,
                onListButtonClicked = {}
            )
        }
    }
}