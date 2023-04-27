package com.comsuon.wp.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LabelledCheckbox(isChecked: Boolean, label: String, onCheckedChange: (Boolean) -> Unit) {
    Row(modifier = Modifier
        .padding(8.dp)
        .clickable { onCheckedChange(!isChecked) }) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            enabled = true,
            colors = CheckboxDefaults.colors(uncheckedColor = MaterialTheme.colorScheme.primary)
        )
        Text(
            text = label,
            color = contentColorFor(backgroundColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}