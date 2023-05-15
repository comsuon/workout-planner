package com.comsuon.wp.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.comsuon.wp.ui.theme.EXERCISE_COLORS

fun String.parseColor(): Color? {
    return if (this.isBlank().not()) Color(android.graphics.Color.parseColor(this)) else null
}

fun randomExerciseColor(): Color {
    return EXERCISE_COLORS.random()
}

fun Color.parseHexString(): String {
    return "#" + Integer.toHexString(this.toArgb())
}