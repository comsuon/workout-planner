package com.comsuon.wp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.VerticalAlignBottom
import androidx.compose.material.icons.filled.VerticalAlignTop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comsuon.wp.ui.common.ReorderOrientation.HORIZONTAL
import com.comsuon.wp.ui.common.ReorderOrientation.VERTICAL
import com.comsuon.wp.ui.theme.LocalTintTheme

@Preview
@Composable
fun ReorderLayoutPreview() {
    ReorderLayout(
        modifier = Modifier,
        orientation = VERTICAL,
        listSize = 1,
        index = 0,
        onMoveItem = { _, _ -> }
    )
}

@Composable
fun ReorderLayout(
    modifier: Modifier = Modifier, orientation: ReorderOrientation = VERTICAL, listSize: Int,
    index: Int,
    onMoveItem: (Int, Int) -> Unit
) {
    val iconSize = modifier.size(32.dp)
    when (orientation) {
        VERTICAL -> {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.End
            ) {
                renderReorderButtons(
                    modifier = iconSize,
                    listSize = listSize,
                    index = index,
                    onMoveItem = onMoveItem
                )
            }
        }
        HORIZONTAL -> {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.End
            ) {
                renderReorderButtons(
                    modifier = iconSize,
                    listSize = listSize,
                    index = index,
                    onMoveItem = onMoveItem
                )
            }
        }
    }
}

@Composable
private fun renderReorderButtons(
    modifier: Modifier,
    listSize: Int,
    index: Int,
    onMoveItem: (Int, Int) -> Unit
) {
    val hideTop = index == 0
    val hideBottom = index == listSize - 1
    val tint = LocalTintTheme.current.iconTint ?: MaterialTheme.colorScheme.primary
    if (hideTop.not()) {
        //Move top
        IconButton(
            modifier = modifier,
            onClick = {
                onMoveItem(index, 0)
            }
        ) {
            Icon(
                imageVector = Icons.Default.VerticalAlignTop,
                tint = tint,
                contentDescription = "Move top"
            )
        }

        //Move up
        IconButton(
            modifier = modifier,
            onClick = {
                onMoveItem(index, index - 1)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowUpward,
                tint = tint,
                contentDescription = "Move up"
            )
        }
    }

    if (hideBottom.not()) {
        //Move down
        IconButton(
            modifier = modifier,
            onClick = {
                onMoveItem(index, index + 1)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDownward,
                tint = tint,
                contentDescription = "Move down"
            )
        }

        //Move bottom
        IconButton(
            modifier = modifier,
            onClick = {
                onMoveItem(index, listSize - 1)
            }
        ) {
            Icon(
                imageVector = Icons.Default.VerticalAlignBottom,
                tint = tint,
                contentDescription = "Move bottom"
            )
        }
    }
}

enum class ReorderOrientation {
    HORIZONTAL,
    VERTICAL
}