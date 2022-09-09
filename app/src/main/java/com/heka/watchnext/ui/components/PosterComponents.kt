package com.heka.watchnext.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.heka.watchnext.R
import com.heka.watchnext.data.fake.fakeWatchMediaList
import com.heka.watchnext.model.WatchMedia
import com.heka.watchnext.ui.theme.*

@Composable
fun PosterCard(
    posterUrl: String,
    posterWidth: Dp = PosterCardWidth,
    posterHeight: Dp = PosterCardHeight,
    onPosterClicked: () -> Unit = {}
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(posterUrl)
            .build(),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_poster_placeholder),
        error = painterResource(R.drawable.ic_poster_placeholder),
        contentDescription = null,
        modifier = Modifier
            .requiredSize(posterWidth, posterHeight)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onPosterClicked() }
    )
}

@Preview("Poster card", showBackground = true)
@Composable
private fun PosterCardPreview() {
    WatchNextTheme {
        PosterCard(
            posterUrl = ""
        )
    }
}

@Composable
fun PosterCarousel(
    watchMediaList: List<WatchMedia>,
    onWatchMediaClicked: (WatchMedia) -> Unit,
    listState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier
) {
    LazyRow(
        state = listState,
        contentPadding = PaddingValues(horizontal = BaseDP),
        horizontalArrangement = Arrangement.spacedBy(SpaceDP),
        modifier = modifier
    ) {
        items(items = watchMediaList, key = { media -> media.id }) { media ->
            PosterCard(posterUrl = media.posterUrl) {
                onWatchMediaClicked(media)
            }
        }
    }
}

@Preview("Poster carousel", showBackground = true)
@Composable
private fun PosterCarouselPreview() {
    WatchNextTheme {
        PosterCarousel(
            watchMediaList = fakeWatchMediaList,
            onWatchMediaClicked = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PosterGrid(
    watchMediaList: List<WatchMedia>,
    onWatchMediaClicked: (WatchMedia) -> Unit,
    gridState: LazyGridState = rememberLazyGridState(),
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(PosterCardWidth),
        contentPadding = PaddingValues(
            top = 86.dp,
            start = BaseDP,
            end = BaseDP,
            bottom = BaseDP
        ),
        horizontalArrangement = Arrangement.spacedBy(SpaceDP),
        verticalArrangement = Arrangement.spacedBy(SpaceDP),
        state = gridState,
        modifier = modifier
    ) {
        items(items = watchMediaList, key = { media -> media.id }) { media ->
            PosterCard(
                posterUrl = media.posterUrl,
                onPosterClicked = { onWatchMediaClicked(media) }
            )
        }
    }
}

@Preview("Poster grid", showBackground = true)
@Composable
private fun PosterGridPreview() {
    WatchNextTheme {
        PosterGrid(
            watchMediaList = fakeWatchMediaList,
            onWatchMediaClicked = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}