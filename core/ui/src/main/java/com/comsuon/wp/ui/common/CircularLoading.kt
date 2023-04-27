package com.comsuon.wp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.comsuon.wp.ui.theme.WorkoutPlannerTheme

@Composable
fun CircularLoading() {
    WorkoutPlannerTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .background(color = Color.Black.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun previewCircularLoading() {
    CircularLoading()
}