package com.heka.watchnext.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heka.watchnext.R
import com.heka.watchnext.ui.theme.BaseDP
import com.heka.watchnext.ui.theme.SpaceDP
import com.heka.watchnext.ui.theme.WatchNextTheme

@Composable
fun WatchNextScreenTopBar(
    @StringRes screenLabelId: Int,
    onBack: () -> Unit,
    searchClicked: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(BaseDP),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.surface,
                        Color.Transparent
                    ),
                    startY = 70f
                )
            )
    ) {
        BackButton(
            modifier = Modifier
                .size(70.dp)
                .padding(BaseDP)
        ) {
            onBack()
        }
        Text(
            text = stringResource(id = screenLabelId),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        IconButton(
            onClick = searchClicked,
            modifier = Modifier.padding(end = BaseDP)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null
            )
        }
    }
}

@Preview("Watch next top bar")
@Composable
private fun WatchNextTopBarPreview() {
    WatchNextTheme {
        Surface {
            WatchNextScreenTopBar(
                screenLabelId = R.string.screen_label_movies,
                onBack = {},
                searchClicked = {}
            )
        }
    }
}

@Composable
fun SearchTopBar(
    search: String,
    onSearchChanged: (String) -> Unit,
    onCloseSearch: () -> Unit,
    loading: Boolean,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(SpaceDP),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.surface,
                            Color.Transparent
                        ),
                        startY = 70f
                    )
                )
        ) {
            BackButton(
                modifier = Modifier
                    .size(70.dp)
                    .padding(BaseDP)
            ) {
                onBack()
            }
            OutlinedTextField(
                value = search,
                onValueChange = onSearchChanged,
                textStyle = MaterialTheme.typography.h6,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    cursorColor = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (search.isNotBlank()) {
                        IconButton(
                            onClick = {
                                onSearchChanged("")
                            }
                        ) {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                        }
                    }
                },
                placeholder = {
                    Text(
                        stringResource(R.string.screen_label_search),
                        style = MaterialTheme.typography.h6
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { onCloseSearch() }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = BaseDP)
            )
        }
        if (loading) {
            LinearProgressIndicator(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Preview("Search top bar")
@Composable
private fun SearchTopBarPreview() {
    WatchNextTheme {
        Surface {
            SearchTopBar(
                search = "",
                onSearchChanged = {},
                onCloseSearch = {},
                loading = false,
                onBack = {}
            )
        }
    }
}