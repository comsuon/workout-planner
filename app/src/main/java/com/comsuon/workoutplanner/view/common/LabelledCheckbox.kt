package com.comsuon.workoutplanner.view.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LabelledCheckbox(isChecked: Boolean, label: String, onCheckedChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.padding(8.dp).clickable { onCheckedChange(!isChecked) }) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            enabled = true,
            colors = CheckboxDefaults.colors(uncheckedColor = MaterialTheme.colors.primary)
        )
        Text(
            text = label,
            color = contentColorFor(backgroundColor = MaterialTheme.colors.primaryVariant),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}