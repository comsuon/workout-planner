package com.comsuon.workoutplanner.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.comsuon.workoutplanner.R
import com.comsuon.workoutplanner.navigation.Screens
import com.comsuon.workoutplanner.ui.theme.Text_Primary
import com.comsuon.workoutplanner.ui.theme.WorkoutPlannerTheme
import com.comsuon.workoutplanner.viewmodel.HomeViewModel

@ExperimentalMaterialApi
@Composable
fun Home(navController: NavController, viewModel: HomeViewModel) {
    val workoutList by viewModel.workoutList.observeAsState()
    WorkoutPlannerTheme {
        val scrollSate = rememberScrollState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primaryVariant)
        ) {
            if (workoutList.isNullOrEmpty().not()) {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    contentPadding = PaddingValues(vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        WorkoutListTitle()
                    }
                    itemsIndexed(workoutList!!) { index, workoutModel ->
                        WorkoutView(
                            workoutModel = workoutModel,
                            onWorkoutSelected = { },
                            onWorkoutUpdated = { },
                            onWorkoutDeleted = { },
                            onWorkoutStarted = {})
                    }
                }
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
        icon = {
            Icon(
                Icons.Filled.Add, stringResource(R.string.btn_new_workout), tint = contentColorFor(
                    backgroundColor = MaterialTheme.colors.secondary
                )
            )
        },
    )
}
