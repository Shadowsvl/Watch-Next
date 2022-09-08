package com.heka.watchnext.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.heka.watchnext.R
import com.heka.watchnext.data.fake.fakeWatchMovie1
import com.heka.watchnext.model.MediaType
import com.heka.watchnext.model.WatchMedia
import com.heka.watchnext.ui.theme.*

@Composable
fun BackdropBanner(
    watchMedia: WatchMedia,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(watchMedia.backdropUrl)
                .build(),
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.TopCenter,
            placeholder = painterResource(R.drawable.ic_backdrop_placeholder),
            error = painterResource(R.drawable.ic_backdrop_placeholder),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(200.dp)
        )
        BackButton(
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.TopStart)
                .padding(BaseDP)
        ) {
            onBack()
        }
        Text(
            text = watchMedia.originalTitle,
            style = MaterialTheme.typography.h5.copy(
                shadow = Shadow(
                    Color.Black,
                    offset = Offset(3f, 3f),
                    blurRadius = 2f
                )
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 8.dp)
                .align(Alignment.BottomStart)
        )
    }
}

@Preview("Backdrop banner", showBackground = true)
@Composable
private fun BackdropBannerPreview() {
    WatchNextTheme {
        Surface {
            BackdropBanner(
                watchMedia = fakeWatchMovie1,
                onBack = {}
            )
        }
    }
}

@Composable
fun WatchMediaInfo(
    watchMedia: WatchMedia,
    isMediaAdded: Boolean,
    modifier: Modifier = Modifier,
    onListButtonClicked: (WatchMedia) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(BaseDP),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(BaseDP),
            modifier = Modifier.fillMaxWidth()
        ) {
            PosterCard(
                posterUrl = watchMedia.posterUrl
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(PosterCardHeight)
            ) {
                Text(
                    text = watchMedia.title,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
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
                            .align(Alignment.BottomEnd)
                    ) {
                        onListButtonClicked(watchMedia)
                    }
                }
            }
        }
        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = ElevationDP
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 180.dp)
                    .padding(horizontal = SpaceDP)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(SpaceDP))
                Text(
                    text = watchMedia.overview,
                    style = MaterialTheme.typography.body1
                )
                Spacer(Modifier.height(SpaceDP))
            }
        }
    }
}

@Preview("Watch media info", showBackground = true)
@Composable
private fun WatchMediaInfoPreview() {
    WatchNextTheme {
        Surface {
            WatchMediaInfo(
                watchMedia = fakeWatchMovie1,
                isMediaAdded = false,
                onListButtonClicked = {}
            )
        }
    }
}

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