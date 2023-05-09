package com.comsuon.wp.collections.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comsuon.wp.common.parseColor
import com.comsuon.wp.model.ExerciseModel
import com.comsuon.wp.model.LoopModel
import com.comsuon.wp.model.WorkoutModel
import com.comsuon.wp.ui.theme.LocalBackgroundTheme
import com.comsuon.wp.ui.theme.LocalTintTheme
import com.comsuon.wp.ui.theme.Shapes
import com.comsuon.wp.ui.theme.WorkoutPlannerTheme

@Composable
fun WorkoutView(
    workoutModel: WorkoutModel,
    onWorkoutSelected: () -> Unit,
    onWorkoutUpdated: (workout: WorkoutModel) -> Unit,
    onWorkoutDeleted: () -> Unit,
    onWorkoutStarted: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .clickable(false, onClick = {}),
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Box(modifier = Modifier.padding(vertical = 12.dp)) {
            Column(horizontalAlignment = Alignment.Start) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    val backgroundColor = LocalBackgroundTheme.current.color
                    Text(
                        text = workoutModel.workoutName,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Row(
                        modifier = Modifier.background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                        )
                    ) {
                        val tint =
                            LocalTintTheme.current.iconTint ?: MaterialTheme.colorScheme.primary
                        IconButton(onClick = onWorkoutDeleted) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete workout",
                                tint = tint
                            )
                        }
                        IconButton(onClick = onWorkoutSelected) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "delete workout",
                                tint = tint
                            )
                        }
                    }
                }
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
            .background(color = MaterialTheme.colorScheme.secondary)
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
                style = MaterialTheme.typography.labelSmall
                    .copy(color = MaterialTheme.colorScheme.secondary)
            )
        }
        loopModel.exerciseList.map { exercise ->
            val background = LocalBackgroundTheme.current.color
            Box(
                modifier = Modifier
                    .size(width = 164.dp, height = 84.dp)
                    .background(color = exercise.colorCode.parseColor() ?: background),
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