package com.cmpe451.resq.ui.views.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T> DropdownMenuComponent(
    label: String,
    items: List<T>,
    selectedItem: T,
    itemToString: (T) -> String,
    onItemSelected: (T) -> Unit
) {
    var expandState by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentWidth(Alignment.Start)
    ) {
        OutlinedTextField(
            value = itemToString(selectedItem),
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandState = true },
            trailingIcon = {
                Icon(
                    imageVector = if (expandState) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.clickable { expandState = !expandState }
                )
            }
        )
        DropdownMenu(
            expanded = expandState,
            onDismissRequest = { expandState = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    onItemSelected(item)
                    expandState = false
                }) {
                    Text(text = itemToString(item))
                }
            }
        }
    }
}