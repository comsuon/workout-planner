package com.comsuon.workoutplanner.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.comsuon.workoutplanner.viewmodel.EditorViewModel

@Composable
fun Editor(navController: NavController, viewModel: EditorViewModel) {
    val workout: WorkoutModel by viewModel.workoutData.collectAsState()

    Scaffold(topBar = {
        renderTopAppBar(
            workoutName = workout.workoutName,
            onSaveClicked = { viewModel.saveWorkout() })
    }) {

    }
}

@Composable
fun renderTopAppBar(workoutName: String, onSaveClicked: () -> Unit) {

}