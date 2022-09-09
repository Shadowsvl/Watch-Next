package com.heka.watchnext.ui.screens.my_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.heka.watchnext.R
import com.heka.watchnext.data.fake.fakeWatchMediaList
import com.heka.watchnext.ui.components.*
import com.heka.watchnext.ui.templates.WatchMediaBottomSheetLayout
import com.heka.watchnext.ui.theme.BaseDP
import com.heka.watchnext.ui.theme.WatchNextTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyListScreen(
    onBack: () -> Unit,
    navigateToDetail: (mediaId: Long, mediaTypeName: String) -> Unit,
    viewModel: MyListViewModel = hiltViewModel()
) {
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val gridState = rememberLazyGridState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = bottomSheetState) {
        viewModel.action.collectLatest { action ->
            when(action) {
                MyListAction.ShowBottomSheet -> {
                    if (!bottomSheetState.isVisible) bottomSheetState.show()
                }
            }
        }
    }

    MyListScreen(
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
private fun MyListScreen(
    bottomSheetState: ModalBottomSheetState,
    gridState: LazyGridState,
    uiState: MyListUiState,
    onBack: () -> Unit,
    navigateToDetail: (mediaId: Long, mediaTypeName: String) -> Unit,
    onEvent: (MyListEvent) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val showUpButton by remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex > 8
        }
    }

    WatchMediaBottomSheetLayout(
        bottomSheetState = bottomSheetState,
        watchMedia = uiState.watchMedia,
        isMediaAdded = uiState.isMediaAdded,
        onListButtonClicked = { onEvent(MyListEvent.OnListButtonClicked(it)) },
        onInfoButtonClicked = { navigateToDetail(it.id, it.mediaType.name) }
    ) {
        LoadingContent(
            empty = uiState.loading,
            emptyContent = { FullScreenLoading() }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                PosterGrid(
                    watchMediaList = uiState.myWatchMediaList,
                    onWatchMediaClicked = {
                        onEvent(MyListEvent.OnWatchMediaChanged(it))
                    },
                    gridState = gridState,
                    modifier = Modifier.fillMaxSize()
                )

                WatchNextScreenTopBar(screenLabelId = R.string.screen_label_my_list) {
                    onBack()
                }

                if (showUpButton) {
                    UpButton(
                        modifier = Modifier
                            .padding(BaseDP)
                            .align(Alignment.BottomEnd)
                    ) {
                        coroutineScope.launch { gridState.scrollToItem(0) }
                    }
                }
            }

            SideEffect {
                if (uiState.myWatchMediaList.isEmpty()) onBack()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview("My list screen", showBackground = true)
@Composable
private fun MyListScreenPreview() {
    WatchNextTheme {
        Surface {
            MyListScreen(
                bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
                gridState = rememberLazyGridState(),
                uiState = MyListUiState(
                    myWatchMediaList = fakeWatchMediaList,
                    loading = false
                ),
                onBack = {},
                navigateToDetail = {_,_ -> },
                onEvent = {}
            )
        }
    }
}