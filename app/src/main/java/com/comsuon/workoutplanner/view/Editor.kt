package com.comsuon.workoutplanner.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.comsuon.workoutplanner.R
import com.comsuon.workoutplanner.ui.theme.tfColors
import com.comsuon.workoutplanner.ui.theme.tfTextStyle
import com.comsuon.workoutplanner.viewmodel.EditorViewModel

@ExperimentalComposeUiApi
@Composable
fun Editor(navController: NavController, viewModel: EditorViewModel) {
    val workoutModel by viewModel.workoutData.observeAsState()
    Scaffold(topBar = {
        val context = LocalContext.current
        renderTopAppBar(
            workoutName = workoutModel?.workoutName ?: "",
            onSaveClicked = {
                viewModel.saveWorkout()
                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            },
            onWorkoutNameChanged = { viewModel.setWorkoutName(it) },
            onBackPressed = { navController.popBackStack() }
        )
    }) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colors.surface)
                .fillMaxSize()
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun renderTopAppBar(
    workoutName: String,
    onWorkoutNameChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
    onBackPressed: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TopAppBar(
        title = {
            TextField(
                value = workoutName,
                onValueChange = onWorkoutNameChanged,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.primaryVariant,
                    )
                    .onFocusChanged {
                        keyboardController?.hide()
                    },
                placeholder = {
                    Text(
                        text = stringResource(R.string.tf_hint_workout_name),
                        color = MaterialTheme.colors.secondary
                    )
                },
                textStyle = tfTextStyle,
                singleLine = true,
                colors = tfColors()
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colors.secondary
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = Color.White,
        actions = {
            IconButton(onClick = onSaveClicked) {
                Icon(
                    Icons.Filled.SaveAlt,
                    contentDescription = "Save Workout",
                    tint = MaterialTheme.colors.secondary
                )
            }
        },
        elevation = 4.dp
    )
}