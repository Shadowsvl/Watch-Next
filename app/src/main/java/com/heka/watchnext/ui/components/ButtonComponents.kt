package com.heka.watchnext.ui.components

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.heka.watchnext.ui.theme.WatchNextTheme

@Composable
fun MyListButton(
    isMediaAdded: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = if (isMediaAdded) Color.Gray else MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.onSurface,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isMediaAdded) Icons.Filled.Check else Icons.Filled.Add,
            contentDescription = null
        )
    }
}

@Preview("My list button", showBackground = true)
@Composable
private fun MyListButtonPreview() {
    WatchNextTheme {
        Surface {
            MyListButton(isMediaAdded = false) {}
        }
    }
}

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = Color.DarkGray,
        contentColor = MaterialTheme.colors.onSurface,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null
        )
    }
}

@Preview("Back button", showBackground = true)
@Composable
private fun BackButtonPreview() {
    WatchNextTheme {
        Surface {
            BackButton { }
        }
    }
}