package com.comsuon.wp.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun String.parseColor(): Color? {
    return if (this.isBlank().not()) Color(android.graphics.Color.parseColor(this)) else null
}