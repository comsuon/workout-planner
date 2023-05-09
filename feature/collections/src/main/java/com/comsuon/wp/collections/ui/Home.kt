package com.comsuon.wp.collections.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.comsuon.wp.collections.navigation.collectionExtrasKey
import com.comsuon.wp.collections.navigation.collectionRoute
import com.comsuon.wp.collections.viewmodel.HomeViewModel
import com.comsuon.wp.ui.theme.LocalTintTheme
import com.comsuon.wp.ui.theme.WorkoutPlannerTheme
import com.comsuon.wp.collections.R as collectionR

const val WORKOUT_SAVE_KEY = "WORKOUT_SAVE_KEY"
const val WORKOUT_ID = "WORKOUT_ID_KEY"

@Composable
fun Home(navController: NavController, viewModel: HomeViewModel) {
    val workoutList by viewModel.workoutList.observeAsState()
    val workoutSaveResult =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData(WORKOUT_SAVE_KEY, false)
            ?.observeAsState()

    WorkoutPlannerTheme {
        val scrollSate = rememberScrollState()
        workoutSaveResult?.value?.let { saveSuccess -> if (saveSuccess) viewModel.loadWorkoutList() }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
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
                            onWorkoutSelected = { navController.navigate("${collectionRoute}?${collectionExtrasKey}=${workoutModel.index}") },
                            onWorkoutUpdated = { viewModel.addFavourite(index) },
                            onWorkoutDeleted = { viewModel.deleteWorkout(workoutModel.index) },
                            onWorkoutStarted = { })
                    }
                }
            }
            NewWorkoutFAB(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                onFABClicked = { navController.navigate("editor_route") }
            )

        }
    }
}

@Composable
fun WorkoutListTitle() {
    Text(
        text = stringResource(collectionR.string.collection_title_workout_list),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primaryContainer)
    )
}

@Composable
fun NewWorkoutFAB(modifier: Modifier, onFABClicked: () -> Unit) {
    val tintColor = LocalTintTheme.current.iconTint ?: MaterialTheme.colorScheme.primary
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onFABClicked,
        text = { Text(text = stringResource(collectionR.string.collection_btn_new_workout)) },
        icon = {
            Icon(Icons.Filled.Add, stringResource(collectionR.string.collection_btn_new_workout), tint = tintColor)
        },
    )
}
