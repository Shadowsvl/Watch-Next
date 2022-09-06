package com.heka.watchnext.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.heka.watchnext.data.fake.fakeWatchSection
import com.heka.watchnext.ui.components.*
import com.heka.watchnext.ui.theme.BaseDP
import com.heka.watchnext.ui.theme.BottomSheetShape
import com.heka.watchnext.ui.theme.WatchNextTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = bottomSheetState) {
        viewModel.action.collectLatest { action ->
            when(action) {
                HomeAction.ShowBottomSheet -> {
                    if (!bottomSheetState.isVisible) bottomSheetState.show()
                }
            }
        }
    }

    HomeScreen(
        bottomSheetState = bottomSheetState,
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeScreen(
    bottomSheetState: ModalBottomSheetState,
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = BottomSheetShape,
        scrimColor = Color.Transparent,
        sheetContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                uiState.watchMedia?.let {
                    WatchMediaSheet(watchMedia = it)
                } ?: CircularProgressIndicator()
            }
        }
    ) {
        LoadingContent(
            empty = uiState.loading,
            emptyContent = { FullScreenLoading() },
            isRefreshing = uiState.isRefreshing,
            onRefresh = { onEvent(HomeEvent.RefreshContent) }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(100.dp))
                    uiState.watchSections.forEach { section ->
                        HomeSection(
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
                HomeTopBar()
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
                uiState = HomeUiState(
                    watchSections = listOf(fakeWatchSection),
                    loading = false
                ),
                onEvent = {}
            )
        }
    }
}