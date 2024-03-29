@file:OptIn(ExperimentalMaterial3Api::class)

package com.comsuon.wp.feature.editor.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.comsuon.wp.common.parseColor
import com.comsuon.wp.model.ExerciseModel
import com.comsuon.wp.ui.common.LabelledCheckbox
import com.comsuon.wp.ui.common.ReorderLayout
import com.comsuon.wp.ui.theme.Text_LightGrey
import com.comsuon.wp.ui.theme.WorkoutPlannerTheme
import com.comsuon.wp.ui.theme.tfColors
import com.comsuon.wp.ui.theme.tfTextStyle
import com.comsuon.wp.feature.editor.R as editorR

@Composable
fun ExerciseView(
    modifier: Modifier = Modifier,
    exercise: ExerciseModel,
    onExerciseUpdate: (ExerciseModel) -> Unit,
    onDeleteItem: () -> Unit,
    exerciseIndex: Int,
    listSize: Int,
    onMoveExercise: (Int, Int) -> Unit
) {
    ConstraintLayout(
        modifier = modifier.then(
            Modifier
                .padding(horizontal = 8.dp)
                .background(exercise.colorCode.parseColor() ?: Color.Transparent)
        )
    ) {
        val (exerciseName, reorderContainer, typeContainer, contentContainer, optionsContainer) =
            createRefs()
        TextField(
            modifier = Modifier
                .constrainAs(exerciseName) {
                    top.linkTo(parent.top, margin = 4.dp)
                    end.linkTo(parent.end, 24.dp)
                    start.linkTo(parent.start, 24.dp)
                }
                .padding(horizontal = 24.dp),
            placeholder = {
                Box(modifier = Modifier.fillMaxWidth(1f), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(editorR.string.editor_tf_hint_exercise_name),
                        color = Text_LightGrey,
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontWeight = FontWeight.Medium)
                    )
                }
            },
            singleLine = true,
            value = exercise.exerciseName,
            colors = tfColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            ),
            textStyle = tfTextStyle().merge(TextStyle(textAlign = TextAlign.Center)),
            onValueChange = { newValue -> onExerciseUpdate(exercise.copy(exerciseName = newValue)) }
        )
        // Reorder items
        ReorderLayout(modifier = Modifier.constrainAs(reorderContainer) {
            top.linkTo(exerciseName.bottom, margin = 4.dp)
            end.linkTo(parent.end, margin = 8.dp)
        }, listSize = listSize, index = exerciseIndex, onMoveItem = onMoveExercise)
        //Reps - Time selector
        TypeSelector(modifier = Modifier.constrainAs(typeContainer) {
            top.linkTo(exerciseName.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, exercise = exercise, onUpdate = onExerciseUpdate)
        //Main content of exercise
        Content(modifier = Modifier.constrainAs(contentContainer) {
            top.linkTo(typeContainer.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, exercise = exercise, onUpdate = onExerciseUpdate)
        //Render exercise options
        ContentOptions(modifier = Modifier.constrainAs(optionsContainer) {
            top.linkTo(contentContainer.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, exercise = exercise, onUpdate = onExerciseUpdate, onDeleteExercise = onDeleteItem)
    }
}

@Composable
fun TypeSelector(modifier: Modifier, exercise: ExerciseModel, onUpdate: (ExerciseModel) -> Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val enabledColor =
            TextStyle(
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        val disabledColor =
            TextStyle(
                color = Text_LightGrey,
                fontSize = 21.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Monospace
            )

        TextButton(
            colors = buttonColors(containerColor = Color.Transparent),
            onClick = {
                if (exercise.isTime.not()) return@TextButton
                else
                    onUpdate(
                        exercise.copy(
                            isTime = false,
                        )
                    )
            }) {
            Text(
                text = stringResource(id = editorR.string.editor_label_reps),
                style = if (exercise.isTime) disabledColor else enabledColor,
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        Box(
            modifier = Modifier
                .height(16.dp)
                .width(1.5.dp)
                .background(color = Color.White)
        )
        TextButton(
            colors = buttonColors(containerColor = Color.Transparent),
            onClick = {
                if (exercise.isTime) return@TextButton
                else onUpdate(exercise.copy(isTime = true, repCount = 1))
            }) {
            Text(
                text = stringResource(id = editorR.string.editor_label_time),
                style = if (exercise.isTime) enabledColor else disabledColor,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun Content(exercise: ExerciseModel, modifier: Modifier, onUpdate: (ExerciseModel) -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (exercise.isTime.not()) {
            NumberRoller(
                value = exercise.repCount.toString()
            ) { newRep ->
                onUpdate(exercise.copy(repCount = newRep.toInt()))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        NumberRoller(
            isTimer = true,
            label =
            if (exercise.isTime) stringResource(id = editorR.string.editor_label_total_time)
            else stringResource(editorR.string.editor_label_time_per_rep),
            value = exercise.timePerRep.toString()
        ) { newTime ->
            onUpdate(exercise.copy(timePerRep = newTime.toInt()))
        }
    }
}

@Composable
fun ContentOptions(
    exercise: ExerciseModel,
    modifier: Modifier,
    onUpdate: (ExerciseModel) -> Unit,
    onDeleteExercise: () -> Unit
) {
    Row(
        modifier = modifier.then(Modifier.fillMaxWidth(1f)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            LabelledCheckbox(
                isChecked = exercise.autoFinished,
                label = stringResource(editorR.string.editor_label_auto_finished)
            ) {
                val newModel = exercise.copy(autoFinished = it)
                onUpdate(newModel)
            }
            LabelledCheckbox(
                isChecked = exercise.skipLastSet,
                label = stringResource(editorR.string.editor_label_skip_last_set)
            ) {
                val newModel = exercise.copy(skipLastSet = it)
                onUpdate(newModel)
            }
        }
        IconButton(onClick = onDeleteExercise, modifier.background(Color.Transparent)) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete exercise",
                tint = contentColorFor(
                    backgroundColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}

@Preview(name = "Exercise Preview")
@Composable
fun PreviewExerciseView() {
    WorkoutPlannerTheme(darkTheme = true) {
        ExerciseView(exercise = ExerciseModel(isTime = true), onExerciseUpdate = { },
            onDeleteItem = {}, onMoveExercise = { _, _ -> }, exerciseIndex = 1, listSize = 4
        )
    }

}