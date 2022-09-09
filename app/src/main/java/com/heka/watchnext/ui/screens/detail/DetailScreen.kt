package com.heka.watchnext.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.heka.watchnext.R
import com.heka.watchnext.data.fake.fakeWatchMediaList
import com.heka.watchnext.data.fake.fakeWatchMovie1
import com.heka.watchnext.ui.components.*
import com.heka.watchnext.ui.templates.WatchMediaBottomSheetLayout
import com.heka.watchnext.ui.theme.BaseDP
import com.heka.watchnext.ui.theme.WatchNextTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit,
    navigateToDetail: (mediaId: Long, mediaTypeName: String) -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = bottomSheetState) {
        viewModel.action.collectLatest { action ->
            when(action) {
                DetailAction.ShowBottomSheet -> {
                    if (!bottomSheetState.isVisible) bottomSheetState.show()
                }
            }
        }
    }

    DetailScreen(
        bottomSheetState = bottomSheetState,
        uiState = uiState,
        onBack = onBack,
        navigateToDetail = navigateToDetail,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DetailScreen(
    bottomSheetState: ModalBottomSheetState,
    uiState: DetailUiState,
    onBack: () -> Unit,
    navigateToDetail: (mediaId: Long, mediaTypeName: String) -> Unit,
    onEvent: (DetailEvent) -> Unit
) {
    WatchMediaBottomSheetLayout(
        bottomSheetState = bottomSheetState,
        watchMedia = uiState.bottomWatchMedia,
        isMediaAdded = uiState.bottomIsMediaAdded,
        onListButtonClicked = { onEvent(DetailEvent.OnBottomListButtonClicked(it)) },
        onInfoButtonClicked = { navigateToDetail(it.id, it.mediaType.name) }
    ) {
        LoadingContent(
            empty = uiState.loading,
            emptyContent = { FullScreenLoading() }
        ) {
            uiState.watchMedia?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    BackdropBanner(
                        watchMedia = it,
                        onBack = onBack,
                        modifier = Modifier.fillMaxWidth()
                    )

                    WatchMediaInfo(
                        watchMedia = it,
                        isMediaAdded = uiState.isMediaAdded,
                        modifier = Modifier.fillMaxWidth().padding(BaseDP),
                        onListButtonClicked = { watchMedia -> onEvent(DetailEvent.OnListButtonClicked(watchMedia)) }
                    )

                    if (uiState.similarMedia.isNotEmpty()) {
                        WatchNextSection(
                            labelId = R.string.section_similar_content,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            PosterCarousel(
                                watchMediaList = uiState.similarMedia,
                                onWatchMediaClicked = { onEvent(DetailEvent.OnWatchMediaChanged(it)) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(BaseDP))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview("Detail screen", showBackground = true)
@Composable
private fun DetailScreenPreview() {
    WatchNextTheme {
        Surface {
            DetailScreen(
                bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
                uiState = DetailUiState(
                    watchMedia = fakeWatchMovie1,
                    similarMedia = fakeWatchMediaList,
                    loading = false
                ),
                onBack = {},
                navigateToDetail = {_,_ -> },
                onEvent = {}
            )
        }
    }
}