package com.heka.watchnext.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.heka.watchnext.R
import com.heka.watchnext.data.asString
import com.heka.watchnext.data.fake.fakeWatchMediaList
import com.heka.watchnext.model.MediaType
import com.heka.watchnext.ui.components.*
import com.heka.watchnext.ui.templates.WatchMediaBottomSheetLayout
import com.heka.watchnext.ui.theme.BaseDP
import com.heka.watchnext.ui.theme.WatchNextTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navigateToDetail: (mediaId: Long, mediaTypeName: String) -> Unit,
    navigateToInfiniteList: (mediaTypeName: String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val myListState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = bottomSheetState) {
        viewModel.action.collectLatest { action ->
            when(action) {
                HomeAction.ShowBottomSheet -> {
                    if (!bottomSheetState.isVisible) bottomSheetState.show()
                }
                HomeAction.AddListStateAnimation -> {
                    myListState.animateScrollToItem(0)
                }
            }
        }
    }

    HomeScreen(
        bottomSheetState = bottomSheetState,
        myListState = myListState,
        uiState = uiState,
        navigateToDetail = navigateToDetail,
        navigateToInfiniteList = navigateToInfiniteList,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeScreen(
    bottomSheetState: ModalBottomSheetState,
    myListState: LazyListState,
    uiState: HomeUiState,
    navigateToDetail: (mediaId: Long, mediaTypeName: String) -> Unit,
    navigateToInfiniteList: (mediaTypeName: String) -> Unit,
    onEvent: (HomeEvent) -> Unit
) {
    WatchMediaBottomSheetLayout(
        bottomSheetState = bottomSheetState,
        watchMedia = uiState.watchMedia,
        isMediaAdded = uiState.isMediaAdded,
        onListButtonClicked = { onEvent(HomeEvent.OnListButtonClicked(it)) },
        onInfoButtonClicked = { navigateToDetail(it.id, it.mediaType.name) }
    ) {
        LoadingContent(
            empty = uiState.loading,
            emptyContent = { FullScreenLoading() },
            isRefreshing = uiState.isRefreshing,
            onRefresh = { onEvent(HomeEvent.RefreshContent) }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (uiState.authErrorMessage != null) {
                    NotifyApiKeyValidationError(
                        errorMessage = uiState.authErrorMessage.asString(),
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(100.dp))

                        if (uiState.myListLatest.isNotEmpty()) {
                            WatchNextSection(
                                labelId = R.string.section_my_list_latest,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                PosterCarousel(
                                    listState = myListState,
                                    watchMediaList = uiState.myListLatest,
                                    onWatchMediaClicked = { onEvent(HomeEvent.OnWatchMediaChanged(it)) }
                                )
                            }
                        }

                        uiState.watchSections.forEach { section ->
                            WatchNextSection(
                                labelId = section.labelId,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                PosterCarousel(
                                    watchMediaList = section.watchMediaList,
                                    onWatchMediaClicked = { onEvent(HomeEvent.OnWatchMediaChanged(it)) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(BaseDP))
                    }
                }
                HomeTopBar(
                    onMoviesClicked = { navigateToInfiniteList(MediaType.Movie.name) },
                    onSeriesClicked = { navigateToInfiniteList(MediaType.Tv.name) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview("Home screen", showBackground = true)
@Composable
private fun HomeScreenPreview() {
    WatchNextTheme {
        Surface {
            HomeScreen(
                bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
                myListState = rememberLazyListState(),
                uiState = HomeUiState(
                    myListLatest = fakeWatchMediaList,
                    loading = false
                ),
                navigateToDetail = { _,_ -> },
                navigateToInfiniteList = {},
                onEvent = {}
            )
        }
    }
}