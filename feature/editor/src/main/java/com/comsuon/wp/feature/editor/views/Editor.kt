package com.comsuon.wp.feature.editor.views

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ButtonDefaults.buttonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.comsuon.wp.common.Constants.WORKOUT_SAVE_KEY
import com.comsuon.wp.feature.editor.R
import com.comsuon.wp.feature.editor.viewmodel.EditorViewModel
import com.comsuon.wp.model.ExerciseModel
import com.comsuon.wp.model.LoopModel
import com.comsuon.wp.ui.common.CircularLoading
import com.comsuon.wp.ui.model.UiState
import com.comsuon.wp.ui.theme.WorkoutPlannerTheme
import com.comsuon.wp.ui.theme.tfColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Editor(navController: NavController, workoutId: String = "", viewModel: EditorViewModel) {
    val workoutModel by viewModel.workoutData.observeAsState()
    val uiState by viewModel.uiState.observeAsState()
    val scrollIndex by viewModel.scrollIndex.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (workoutId.isEmpty().not()) {
            viewModel.loadWorkout(workoutId)
        }
    }
    WorkoutPlannerTheme(darkTheme = isSystemInDarkTheme()) {
        Scaffold(
            topBar = {
                EditorTopAppBar(
                    workoutName = workoutModel?.workoutName ?: "",
                    onSaveClicked = {
                        viewModel.saveWorkout()
                    },
                    onWorkoutNameChanged = viewModel::setWorkoutName,
                    onBackPressed = { navController.popBackStack() }
                )
            }
        ) { contentPadding ->
            Box(
                modifier = Modifier
                    .padding(top = contentPadding.calculateTopPadding())
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxSize()
            ) {
                LoopList(
                    loopList = workoutModel!!.loopList,
                    onLoopChange = viewModel::setLoop,
                    addEmptyLoop = viewModel::addEmptyLoop,
                    onAddEmptyExercise = viewModel::addEmptyExercise,
                    onExerciseUpdated = viewModel::updateExercise,
                    onDeleteExercise = viewModel::deleteExercise,
                    onDeleteLoop = viewModel::deleteLoop,
                    scrollIndex = scrollIndex?.getContentIfNotHandled()
                )
                when (uiState?.getContentIfNotHandled()) {
                    is UiState.Loading -> {
                        CircularLoading()
                    }
                    is UiState.Error<*> -> {
                        val error = (uiState!!.peekContent() as UiState.Error<*>).error.errorCode
                        Toast.makeText(
                            context,
                            stringResource(id = error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is UiState.Success<*> -> {
                        Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(WORKOUT_SAVE_KEY, true)
                        navController.popBackStack()
                    }
                    else -> {
                    }
                }
            }
        }
    }
}

@Composable
fun LoopList(
    loopList: List<LoopModel>,
    onLoopChange: (Int, LoopModel) -> Unit,
    addEmptyLoop: () -> Unit,
    onDeleteLoop: (Int) -> Unit,
    onExerciseUpdated: (Int, Int, ExerciseModel) -> Unit,
    onDeleteExercise: (Int, Int) -> Unit,
    onAddEmptyExercise: (Int) -> Unit,
    scrollIndex: Int?
) {
    var listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        //implement lazy column for loop list
        items(
            count = loopList.size,
            key = { index -> loopList[index].toString() + index },
            itemContent = { index ->
                LoopView(
                    loopModel = loopList[index],
                    onLoopUpdated = { newLoop -> onLoopChange(index, newLoop) },
                    onExerciseUpdated = { exerciseIndex, exercise ->
                        onExerciseUpdated(
                            index,
                            exerciseIndex,
                            exercise
                        )
                    },
                    onDeleteItem = { exerciseIndex -> onDeleteExercise(index, exerciseIndex) },
                    onAddNewExercise = { onAddEmptyExercise(index) },
                    onDeleteLoop = { onDeleteLoop(index) }
                )
            })
        //Wrap the composable component in side item {} to build as item in LazyColumn
        item (
            key = "AddNewButton"
            ) {
            AddNewLoopButton {
                addEmptyLoop.invoke()
                coroutineScope.launch {
                    delay(500)
                    listState.animateScrollToItem(loopList.size)
                }
            }
        }
        scrollIndex?.let {
            coroutineScope.launch {
                listState.animateScrollToItem(it)
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
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.primary)
        ),
        elevation = buttonElevation(defaultElevation = 4.dp)
    ) {
        Icon(
            Icons.Default.Add, contentDescription = "Add loop"
        )
        Text(
            stringResource(R.string.editor_btn_add_loop),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
                        color = MaterialTheme.colorScheme.primaryContainer,
                    )
                    .onFocusChanged {
                        keyboardController?.hide()
                    },
                placeholder = {
                    Text(
                        text = stringResource(R.string.editor_tf_hint_workout_name),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                textStyle = MaterialTheme.typography.labelMedium,
                singleLine = true,
                colors = tfColors()
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        actions = {
            IconButton(onClick = onSaveClicked) {
                Icon(
                    Icons.Filled.SaveAlt,
                    contentDescription = "Save Workout",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
    )
}