package com.heka.watchnext.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.heka.watchnext.R
import com.heka.watchnext.data.fake.fakeWatchMediaList
import com.heka.watchnext.ui.theme.BaseDP
import com.heka.watchnext.ui.theme.HomeSectionSpaceDp
import com.heka.watchnext.ui.theme.WatchNextTheme

@Composable
fun HomeSection(
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

@Preview("Home section", showBackground = true)
@Composable
private fun HomeSectionPreview() {
    WatchNextTheme {
        Surface {
            HomeSection(
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