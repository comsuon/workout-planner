package com.comsuon.workoutplanner.view.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
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
import com.comsuon.workoutplanner.R
import com.comsuon.workoutplanner.ui.theme.Text_LightGrey
import com.comsuon.workoutplanner.ui.theme.WorkoutPlannerTheme
import com.comsuon.workoutplanner.ui.theme.tfColors
import com.comsuon.workoutplanner.ui.theme.tfTextStyle
import com.comsuon.workoutplanner.view.ExerciseModel

@Composable
fun ExerciseView(
    exercise: ExerciseModel,
    onExerciseUpdate: (ExerciseModel) -> Unit,
    onDeleteItem: (Int) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 8.dp)
            .background(exercise.colorCode)
    ) {
        val (exerciseName, typeContainer, contentContainer, optionsContainer) = createRefs()
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
                        text = stringResource(R.string.tf_hint_exercise_name),
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
                focusedIndicatorColor = MaterialTheme.colors.primary
            ),
            textStyle = tfTextStyle.merge(TextStyle(textAlign = TextAlign.Center)),
            onValueChange = { newValue -> onExerciseUpdate(exercise.copy(exerciseName = newValue)) }
        )
        TypeSelector(modifier = Modifier.constrainAs(typeContainer) {
            top.linkTo(exerciseName.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, exercise = exercise, onUpdate = onExerciseUpdate)
        Content(modifier = Modifier.constrainAs(contentContainer) {
            top.linkTo(typeContainer.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, exercise = exercise, onUpdate = onExerciseUpdate)
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
            colors = buttonColors(backgroundColor = Color.Transparent),
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
                text = stringResource(id = R.string.label_reps),
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
            colors = buttonColors(backgroundColor = Color.Transparent),
            onClick = {
                if (exercise.isTime) return@TextButton
                else onUpdate(exercise.copy(isTime = true, repCount = 1))
            }) {
            Text(
                text = stringResource(id = R.string.label_time),
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
            if (exercise.isTime) stringResource(id = R.string.label_total_time)
            else stringResource(R.string.label_time_per_rep),
            value = exercise.timePerRep.toString()
        ) { newTime ->
            onUpdate(exercise.copy(timePerRep = newTime.toInt()))
        }
    }
}

@Preview(name = "Exercise Preview")
@Composable
fun PreviewExerciseView() {
    WorkoutPlannerTheme() {
        ExerciseView(exercise = ExerciseModel(isTime = true), onExerciseUpdate = { }) {

        }
    }

}