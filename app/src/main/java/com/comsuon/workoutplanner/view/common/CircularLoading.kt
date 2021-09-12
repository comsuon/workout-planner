package com.comsuon.workoutplanner.view.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.comsuon.workoutplanner.ui.theme.WorkoutPlannerTheme

@Composable
fun CircularLoading() {
    WorkoutPlannerTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .background(color = MaterialTheme.colors.primaryVariant.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun previewCircularLoading() {
    CircularLoading()
}