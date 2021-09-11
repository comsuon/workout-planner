package com.comsuon.workoutplanner.view.editor

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
import com.comsuon.workoutplanner.view.ExerciseModel
import com.comsuon.workoutplanner.view.LoopModel
import com.comsuon.workoutplanner.viewmodel.EditorViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun Editor(navController: NavController, viewModel: EditorViewModel) {
    val workoutModel by viewModel.workoutData.observeAsState()
    Scaffold(topBar = {
        val context = LocalContext.current
        EditorTopAppBar(
            workoutName = workoutModel?.workoutName ?: "",
            onSaveClicked = {
                viewModel.saveWorkout()
                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            },
            onWorkoutNameChanged = viewModel::setWorkoutName,
            onBackPressed = { navController.popBackStack() }
        )
    }) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colors.primaryVariant)
                .fillMaxSize()
        ) {
            LoopList(
                loopList = workoutModel!!.loopList,
                onLoopChange = viewModel::setLoop,
                addEmptyLoop = viewModel::addEmptyLoop,
                onAddEmptyExercise = viewModel::addEmptyExercise,
                onExerciseUpdated = viewModel::updateExercise,
                onDeleteExercise = viewModel::deleteExercise
            )
        }
    }
}

@Composable
fun LoopList(
    loopList: List<LoopModel>,
    onLoopChange: (Int, LoopModel) -> Unit,
    addEmptyLoop: () -> Unit,
    onExerciseUpdated: (Int, Int, ExerciseModel) -> Unit,
    onDeleteExercise: (Int, Int) -> Unit,
    onAddEmptyExercise: (Int) -> Unit
) {
    var listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        itemsIndexed(items = loopList) { index, loop ->
            if (index == 0) {
                Divider(color = Color.Transparent, thickness = 16.dp)
            }
            LoopView(
                loopModel = loop,
                onLoopUpdated = { newLoop -> onLoopChange(index, newLoop) },
                onExerciseUpdated = { exerciseIndex, exercise ->
                    onExerciseUpdated(
                        index,
                        exerciseIndex,
                        exercise
                    )
                },
                onDeleteItem = { exerciseIndex -> onDeleteExercise(index, exerciseIndex) },
                onAddNewExercise = { onAddEmptyExercise(index) }
            )
            Divider(color = Color.Transparent, thickness = 16.dp)
        }

        //Wrap the composable component in side item {} to build as item in LazyColumn
        item {
            AddNewLoopButton {
                addEmptyLoop.invoke()
                coroutineScope.launch {
                    delay(500)
                    listState.animateScrollToItem(loopList.size)
                }
            }
        }
    }
}

@Composable
fun AddNewLoopButton(addEmptyLoop: () -> Unit) {
    Button(
        onClick = addEmptyLoop,
        modifier = Modifier.padding(vertical = 8.dp),
        shape = RoundedCornerShape(corner = CornerSize(28.dp)),
        colors = buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.secondary)
        ),
        elevation = elevation(4.dp)
    ) {
        Icon(
            Icons.Default.Add, contentDescription = "Add loop"
        )
        Text(
            stringResource(R.string.btn_add_loop),
            style = MaterialTheme.typography.subtitle2,
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun EditorTopAppBar(
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