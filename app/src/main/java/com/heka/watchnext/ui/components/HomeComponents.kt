package com.heka.watchnext.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heka.watchnext.R
import com.heka.watchnext.data.fake.fakeWatchMediaList
import com.heka.watchnext.ui.theme.BaseDP
import com.heka.watchnext.ui.theme.HomeSectionSpaceDp
import com.heka.watchnext.ui.theme.SpaceDP
import com.heka.watchnext.ui.theme.WatchNextTheme

@Composable
fun HomeTopBar(
    onMoviesClicked: () -> Unit,
    onSeriesClicked: () -> Unit,
    myListEnabled: Boolean = false,
    onMyListClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.surface)
                .padding(top = BaseDP)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_watch_next_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .requiredSize(40.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(BaseDP),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.surface,
                            Color.Transparent
                        ),
                        startY = 50f
                    )
                )
        ) {
            Spacer(modifier = Modifier.width(SpaceDP))
            TextButton(onClick = onMoviesClicked) {
                Text(
                    text = stringResource(id = R.string.screen_label_movies),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
                )
            }
            TextButton(onClick = onSeriesClicked) {
                Text(
                    text = stringResource(id = R.string.screen_label_series),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
                )
            }
            if (myListEnabled) {
                TextButton(onClick = onMyListClicked) {
                    Text(
                        text = stringResource(id = R.string.screen_label_my_list),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}

@Preview("Home top bar", showBackground = false)
@Composable
private fun HomeTopBarPreview() {
    WatchNextTheme {
        Surface {
            HomeTopBar(
                onMoviesClicked = {},
                onSeriesClicked = {},
                myListEnabled = true,
                onMyListClicked = {}
            )
        }
    }
}

@Composable
fun WatchNextSection(
    @StringRes labelId: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = labelId),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .paddingFromBaseline(top = HomeSectionSpaceDp, bottom = BaseDP)
                .padding(horizontal = BaseDP)
        )
        content()
    }
}

@Preview("Watch next section", showBackground = true)
@Composable
private fun WatchNextSectionPreview() {
    WatchNextTheme {
        Surface {
            WatchNextSection(
                labelId = R.string.app_name,
                modifier = Modifier.fillMaxWidth(),
                content = {
                    PosterCarousel(
                        watchMediaList = fakeWatchMediaList,
                        onWatchMediaClicked = {}
                    )
                }
            )
        }
    }
}