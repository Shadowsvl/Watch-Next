package com.heka.watchnext.ui.templates

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.heka.watchnext.model.WatchMedia
import com.heka.watchnext.ui.components.WatchMediaSheet
import com.heka.watchnext.ui.theme.BottomSheetShape
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WatchMediaBottomSheetLayout(
    bottomSheetState: ModalBottomSheetState,
    watchMedia: WatchMedia?,
    isMediaAdded: Boolean,
    onListButtonClicked: (WatchMedia) -> Unit,
    onInfoButtonClicked: (WatchMedia) -> Unit,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = bottomSheetState.isVisible) {
        coroutineScope.launch {
            bottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = BottomSheetShape,
        scrimColor = Color.Transparent,
        sheetContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                watchMedia?.let {
                    WatchMediaSheet(
                        watchMedia = it,
                        isMediaAdded = isMediaAdded,
                        onListButtonClicked = { media -> onListButtonClicked(media) },
                        onInfoButtonClicked = { media ->
                            coroutineScope.launch {
                                if (bottomSheetState.isVisible) bottomSheetState.snapTo(ModalBottomSheetValue.Hidden)
                            }
                            onInfoButtonClicked(media)
                        }
                    )
                } ?: CircularProgressIndicator()
            }
        }
    ) {
        content()
    }
}