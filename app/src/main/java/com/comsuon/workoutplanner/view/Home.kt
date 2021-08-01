package com.comsuon.workoutplanner.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.comsuon.workoutplanner.R
import com.comsuon.workoutplanner.navigation.Screens
import com.comsuon.workoutplanner.ui.theme.Text_Primary
import com.comsuon.workoutplanner.ui.theme.WorkoutPlannerTheme

@Composable
fun Home(navController: NavController) {
    WorkoutPlannerTheme {
        val scrollSate = rememberScrollState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primaryVariant)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(state = scrollSate)
                    .padding(horizontal = 16.dp, vertical = 24.dp),
            ) {
                WorkoutListTitle()
            }
            NewWorkoutFAB(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                onFABClicked = { navController.navigate(Screens.Editor.name) }
            )

        }
    }
}

@Composable
fun WorkoutListTitle() {
    Text(
        text = stringResource(R.string.title_workout_list),
        style = MaterialTheme.typography.h6,
        color = Text_Primary
    )
}

@Composable
fun NewWorkoutFAB(modifier: Modifier, onFABClicked: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onFABClicked,
        text = { Text(text = stringResource(R.string.btn_new_workout)) },
        icon = { Icon(Icons.Filled.Add, stringResource(R.string.btn_new_workout)) },
    )
}
