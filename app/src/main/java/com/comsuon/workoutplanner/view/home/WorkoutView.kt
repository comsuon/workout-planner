package com.comsuon.workoutplanner.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comsuon.workoutplanner.ui.theme.WorkoutPlannerTheme
import com.comsuon.workoutplanner.view.ExerciseModel
import com.comsuon.workoutplanner.view.LoopModel
import com.comsuon.workoutplanner.view.WorkoutModel

@ExperimentalMaterialApi
@Composable
fun WorkoutView(
    workoutModel: WorkoutModel,
    onWorkoutSelected: () -> Unit,
    onWorkoutUpdated: (workout: WorkoutModel) -> Unit,
    onWorkoutDeleted: () -> Unit,
    onWorkoutStarted: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(1f),
        onClick = {},
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
    ) {
        Box(modifier = Modifier.padding(vertical = 12.dp)) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    workoutModel.workoutName,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = TextStyle(
                        color = contentColorFor(backgroundColor = MaterialTheme.colors.primaryVariant),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                LazyRow(
                    Modifier.padding(vertical = 8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(items = workoutModel.loopList) { loop ->
                        LoopItem(loop)
                    }
                }
            }
        }

    }
}

@Composable
fun LoopItem(loopModel: LoopModel) {
    Row(
        Modifier
            .background(color = MaterialTheme.colors.secondary)
            .padding(vertical = 2.dp)
            .padding(end = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "${loopModel.setCount}x",
                style = TextStyle(
                    color = contentColorFor(backgroundColor = MaterialTheme.colors.secondary),
                    fontSize = 16.sp
                )
            )
        }
        loopModel.exerciseList.map { exercise ->
            Box(
                modifier = Modifier
                    .size(width = 164.dp, height = 84.dp)
                    .background(color = exercise.colorCode),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(6.dp)
                ) {
                    Text(
                        text = exercise.exerciseName,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp,
                        ),
                        maxLines = 2,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                    val exerciseTime =
                        if (exercise.isTime)
                            "${exercise.timePerRep}s"
                        else
                            "${exercise.repCount} x ${exercise.timePerRep}s"
                    Text(
                        text = exerciseTime,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp,
                        )
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun WorkoutPreview() {
    val workout = WorkoutModel(
        workoutName = "Best Workout",
        loopList = listOf(
            LoopModel(
                setCount = 3,
                exerciseList = listOf(
                    ExerciseModel(
                        exerciseName = "Exercise 1 is too long for shi like this",
                        isTime = false,
                        repCount = 3,
                        timePerRep = 3
                    ),
                    ExerciseModel(
                        exerciseName = "Rest",
                        isTime = true,
                        timePerRep = 30
                    ),
                )
            ),
            LoopModel(
                setCount = 3,
                exerciseList = listOf(
                    ExerciseModel(
                        exerciseName = "Exercise 1",
                        isTime = false,
                        repCount = 3,
                        timePerRep = 3
                    ),
                    ExerciseModel(
                        exerciseName = "Rest",
                        isTime = true,
                        timePerRep = 30
                    ),
                )
            )
        )
    )

    WorkoutPlannerTheme {
        WorkoutView(
            workoutModel = workout,
            onWorkoutSelected = {},
            onWorkoutUpdated = {},
            onWorkoutDeleted = {},
            onWorkoutStarted = {}
        )
    }

}