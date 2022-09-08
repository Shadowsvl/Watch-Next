package com.heka.watchnext.ui.screens.infinite_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.heka.watchnext.R
import com.heka.watchnext.data.extensions.isCloseToBottom
import com.heka.watchnext.data.fake.fakeWatchMediaList
import com.heka.watchnext.ui.components.FullScreenLoading
import com.heka.watchnext.ui.components.LoadingContent
import com.heka.watchnext.ui.components.PosterGrid
import com.heka.watchnext.ui.components.WatchNextScreenTopBar
import com.heka.watchnext.ui.templates.WatchMediaBottomSheetLayout
import com.heka.watchnext.ui.theme.WatchNextTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InfiniteListScreen(
    onBack: () -> Unit,
    navigateToDetail: (mediaId: Long, mediaTypeName: String) -> Unit,
    viewModel: InfiniteListViewModel = hiltViewModel()
) {
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val gridState = rememberLazyGridState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = bottomSheetState) {
        viewModel.action.collectLatest { action ->
            when(action) {
                InfiniteListAction.ShowBottomSheet -> {
                    if (!bottomSheetState.isVisible) bottomSheetState.show()
                }
            }
        }
    }

    val isGridCloseToBottom by remember {
        derivedStateOf {
            uiState.infiniteWatchMediaList.isNotEmpty() && gridState.isCloseToBottom()
        }
    }

    LaunchedEffect(key1 = isGridCloseToBottom) {
        if (isGridCloseToBottom) viewModel.onEvent(InfiniteListEvent.RequestMoreMedia)
    }

    InfiniteListScreen(
        bottomSheetState = bottomSheetState,
        gridState = gridState,
        uiState = uiState,
        onBack = onBack,
        navigateToDetail = navigateToDetail,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun InfiniteListScreen(
    bottomSheetState: ModalBottomSheetState,
    gridState: LazyGridState,
    uiState: InfiniteListUiState,
    onBack: () -> Unit,
    navigateToDetail: (mediaId: Long, mediaTypeName: String) -> Unit,
    onEvent: (InfiniteListEvent) -> Unit
) {
    WatchMediaBottomSheetLayout(
        bottomSheetState = bottomSheetState,
        watchMedia = uiState.watchMedia,
        isMediaAdded = uiState.isMediaAdded,
        onListButtonClicked = { onEvent(InfiniteListEvent.OnListButtonClicked(it)) },
        onInfoButtonClicked = { navigateToDetail(it.id, it.mediaType.name) }
    ) {
        LoadingContent(
            empty = uiState.infiniteWatchMediaList.isEmpty(),
            emptyContent = { FullScreenLoading() }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                PosterGrid(
                    watchMediaList = uiState.infiniteWatchMediaList,
                    onWatchMediaClicked = {
                        onEvent(InfiniteListEvent.OnWatchMediaChanged(it))
                    },
                    gridState = gridState,
                    modifier = Modifier.fillMaxSize()
                )
                WatchNextScreenTopBar(screenLabelId = uiState.screenLabelId) {
                    onBack()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview("Infinite list screen", showBackground = true)
@Composable
private fun InfiniteListScreenPreview() {
    WatchNextTheme {
        Surface {
            InfiniteListScreen(
                bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
                gridState = rememberLazyGridState(),
                uiState = InfiniteListUiState(
                    screenLabelId = R.string.screen_label_movies,
                    infiniteWatchMediaList = fakeWatchMediaList
                ),
                onBack = {},
                navigateToDetail = {_,_ -> },
                onEvent = {}
            )
        }
    }
}