package com.heka.watchnext.ui.screens.search

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.heka.watchnext.R
import com.heka.watchnext.data.fake.fakeWatchSection
import com.heka.watchnext.ui.components.PosterCarousel
import com.heka.watchnext.ui.components.SearchTopBar
import com.heka.watchnext.ui.components.WatchNextSection
import com.heka.watchnext.ui.templates.WatchMediaBottomSheetLayout
import com.heka.watchnext.ui.theme.BaseDP
import com.heka.watchnext.ui.theme.WatchNextTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    onBack: () -> Unit,
    navigateToDetail: (mediaId: Long, mediaTypeName: String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = bottomSheetState) {
        viewModel.action.collectLatest { action ->
            when(action) {
                SearchAction.ShowBottomSheet -> {
                    if (!bottomSheetState.isVisible) bottomSheetState.show()
                }
            }
        }
    }

    SearchScreen(
        bottomSheetState = bottomSheetState,
        uiState = uiState,
        onBack = onBack,
        navigateToDetail = navigateToDetail,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SearchScreen(
    bottomSheetState: ModalBottomSheetState,
    uiState: SearchUiState,
    onBack: () -> Unit,
    navigateToDetail: (mediaId: Long, mediaTypeName: String) -> Unit,
    onEvent: (SearchEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current

    WatchMediaBottomSheetLayout(
        bottomSheetState = bottomSheetState,
        watchMedia = uiState.watchMedia,
        isMediaAdded = uiState.isMediaAdded,
        onListButtonClicked = { onEvent(SearchEvent.OnListButtonClicked(it)) },
        onInfoButtonClicked = { navigateToDetail(it.id, it.mediaType.name) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.resultSections.isEmpty()) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_watch_next_icon),
                    alpha = 0.6f,
                    contentDescription = null,
                    modifier = Modifier
                        .requiredSize(100.dp)
                        .align(Alignment.Center)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(86.dp))

                if (uiState.emptyResultMessageVisible && uiState.resultSections.isEmpty() && uiState.search.isNotBlank()) {
                    Text(
                        text = stringResource(id = R.string.notify_search_empty),
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = BaseDP)
                    )
                } else {
                    uiState.resultSections.forEach { section ->
                        WatchNextSection(
                            labelId = section.labelId,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            PosterCarousel(
                                watchMediaList = section.watchMediaList,
                                onWatchMediaClicked = {
                                    focusManager.clearFocus()
                                    onEvent(SearchEvent.OnWatchMediaChanged(it))
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(BaseDP))
            }
            SearchTopBar(
                search = uiState.search,
                onSearchChanged = { onEvent(SearchEvent.OnSearchQueryChanged(it)) },
                onCloseSearch = { focusManager.clearFocus() },
                loading = uiState.loading
            ) {
                onBack()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview("Search screen", showBackground = true)
@Composable
private fun SearchScreenPreview() {
    WatchNextTheme {
        Surface {
            SearchScreen(
                bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
                uiState = SearchUiState(
                    resultSections = listOf(fakeWatchSection),
                    loading = false
                ),
                onBack = {},
                navigateToDetail = { _,_ -> },
                onEvent = {}
            )
        }
    }
}

