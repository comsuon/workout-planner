@file:OptIn(ExperimentalMaterial3Api::class)

package com.comsuon.wp.feature.editor.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.comsuon.wp.feature.editor.R as editorR
import com.comsuon.wp.ui.theme.LocalTintTheme
import com.comsuon.wp.ui.theme.Text_Subtitle
import com.comsuon.wp.ui.theme.WorkoutPlannerTheme
import com.comsuon.wp.ui.theme.tfColors
import com.comsuon.wp.ui.theme.tfTextStyle

@Composable
fun NumberRoller(
    isTimer: Boolean = false,
    label: String = if (isTimer.not()) stringResource(editorR.string.editor_label_reps) else
        stringResource(editorR.string.editor_label_time),
    value: String = "0",
    min: Int = 1,
    step: Int = 1,
    valueChanged: (String) -> Unit
) {
    val tint = LocalTintTheme.current.iconTint ?: MaterialTheme.colorScheme.primary
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            modifier = Modifier.padding(bottom = 4.dp),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { valueChanged.invoke(onButtonDecrease(value, min, step)) },
                Modifier
                    .width(44.dp)
                    .align(Alignment.CenterVertically)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(topStart = 22.dp, bottomStart = 22.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Decrease",
                    tint = tint
                )
            }
            ConstraintLayout(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .width(72.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(4.dp)
                    ),
            ) {
                val (textField, subtitle) = createRefs()
                if (isTimer) Text(
                    text = "Sec",
                    style = MaterialTheme.typography.labelSmall.merge(TextStyle(color = Text_Subtitle)),
                    modifier = Modifier
                        .constrainAs(subtitle) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            baseline.linkTo(textField.baseline)
                        }
                        .padding(end = 4.dp)
                )
                TextField(
                    value = value,
                    onValueChange = { value -> valueChanged(onRepsValueChanged(value, "$min")) },
                    colors = tfColors(backgroundColor = Color.Transparent),
                    textStyle = tfTextStyle().merge(
                        TextStyle(textAlign = if (isTimer.not()) TextAlign.Center else TextAlign.End)
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.constrainAs(textField) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(subtitle.start)
                    }
                )
            }
            IconButton(
                onClick = { valueChanged.invoke(onButtonIncrease(value, step)) },
                Modifier
                    .width(44.dp)
                    .align(Alignment.CenterVertically)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(topEnd = 22.dp, bottomEnd = 22.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase",
                    tint = Color.White
                )
            }
        }
    }
}

private fun onRepsValueChanged(value: String, min: String = "0"): String {
    return value.filter { it.isDigit() }.toLongOrNull()?.toString() ?: min
}

private fun onButtonIncrease(value: String, step: Int): String {
    var valueInt = value.toIntOrNull() ?: 0
    valueInt += step
    return valueInt.toString()
}

private fun onButtonDecrease(value: String, min: Int, step: Int): String {
    var valueInt = value.toIntOrNull() ?: 0
    valueInt -= step
    return if (valueInt >= min) valueInt.toString() else min.toString()
}

@Preview(
    name = "Number Roller",
    device = Devices.PIXEL_4,
    showBackground = true
)

@Composable
fun PreviewNumberRoller() {
    val value by remember { mutableStateOf("05") }
    val currentContext = LocalContext.current
    WorkoutPlannerTheme(darkTheme = true) {
        NumberRoller(
            isTimer = true,
            value = value,
            valueChanged = { value ->
                Toast.makeText(currentContext, value, Toast.LENGTH_SHORT).show()
            })
    }

}