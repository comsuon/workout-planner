package com.comsuon.wp.feature.editor.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.comsuon.wp.model.ExerciseModel
import com.comsuon.wp.model.LoopModel
import com.comsuon.wp.ui.theme.Indigo100
import com.comsuon.wp.feature.editor.R as editorR

@Composable
fun LoopView(
    loopModel: LoopModel,
    onDeleteItem: (Int) -> Unit,
    onAddNewExercise: () -> Unit,
    onExerciseUpdated: (Int, ExerciseModel) -> Unit,
    onLoopUpdated: (LoopModel) -> Unit,
    onDeleteLoop: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 16.dp), contentAlignment = Alignment.TopCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 8.dp)
                .defaultMinSize(minHeight = 100.dp),
            shape = RoundedCornerShape(4.dp),
            elevation = cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            ExerciseList(
                exerciseList = loopModel.exerciseList,
                onDeleteItem = onDeleteItem,
                onExerciseUpdated = onExerciseUpdated,
                onNewExercise = onAddNewExercise
            )
        }
        SetCount(setCount = loopModel.setCount) { newSetCount ->
            val newLoop = loopModel.copy(setCount = newSetCount)
            onLoopUpdated(newLoop)
        }
        DeleteLoop(modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 8.dp), onDeleteLoop)
    }

}

@Composable
fun SetCount(setCount: Int = 3, onSetCountUpdated: (Int) -> Unit) {
    Column(
        modifier = Modifier.background(Indigo100, shape = RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(editorR.string.editor_label_set_count),
            modifier = Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.bodySmall
        )
        Row(modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp)) {
            IconButton(
                modifier = Modifier.size(32.dp),
                onClick = {
                    var result = setCount - 1
                    result = if (result < 0) 0 else result
                    onSetCountUpdated(result)
                }) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Decrease set",
                )
            }
            Text(
                text = setCount.toString(),
                style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace),
                modifier = Modifier.padding(horizontal = 6.dp)
            )
            IconButton(
                modifier = Modifier.size(32.dp),
                onClick = {
                    val result = setCount + 1
                    onSetCountUpdated(result)
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase set",
                )
            }
        }
    }
}

@Composable
fun DeleteLoop(modifier: Modifier, onDeleteLoop: () -> Unit) {
    IconButton(
        onClick = onDeleteLoop,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete loop",
            tint = contentColorFor(
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
fun ExerciseList(
    exerciseList: List<ExerciseModel>,
    onDeleteItem: (Int) -> Unit,
    onNewExercise: () -> Unit,
    onExerciseUpdated: (Int, ExerciseModel) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (exerciseList.isNotEmpty()) {
            exerciseList.forEachIndexed { index, item ->
                ExerciseView(
                    modifier = if (index == 0) Modifier.padding(top = 44.dp) else Modifier,
                    exercise = item,
                    onDeleteItem = { onDeleteItem(index) },
                    onExerciseUpdate = { newItem ->
                        onExerciseUpdated(index, newItem)
                    })
            }
        }
        Box(
            modifier = Modifier.padding(
                top = if (exerciseList.isEmpty()) 64.dp else 8.dp,
                bottom = 8.dp
            )
        ) {
            IconButton(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer, shape = CircleShape)
                    .size(36.dp),
                onClick = onNewExercise
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new exercise",
                    tint = Color.White
                )
            }
        }
    }
}