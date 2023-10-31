package com.cmpe451.resq.ui.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cmpe451.resq.ui.theme.LightGreen
import com.cmpe451.resq.viewmodels.RequestViewModel

@Composable
fun RequestScreen(
    navController: NavController,
) {
    val viewModel: RequestViewModel = viewModel()
    // Variables for dropdown menu
    var typeExpanded by remember { mutableStateOf(false) }
    val types = listOf("Food", "Water", "Medicine")
    var selectedType by remember { mutableStateOf(types[0]) }

    var priorityExpanded by remember { mutableStateOf(false) }
    val priorities = listOf("High", "Medium", "Low")
    var selectedPriority by remember { mutableStateOf(priorities[0]) }

    var quantity by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Top Bar with back button and title
        TopAppBar(
            title = { Text(text = "Request",
                color = Color(0xFFB356AF)) },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            backgroundColor = Color.White,
            elevation = 4.dp,
            contentColor = Color.Black
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                DropdownMenuComponent(
                    label = "Type",
                    items = listOf("Food", "Water", "Medicine"),
                    selectedItem = selectedType,
                    expanded = false
                ) { selectedType = it }

                Spacer(modifier = Modifier.height(16.dp))

                DropdownMenuComponent(
                    label = "Priority",
                    items = listOf("High", "Medium", "Low"),
                    selectedItem = selectedPriority,
                    expanded = false
                ) { selectedPriority = it }

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = quantity, // you can bind this to a state variable to store the quantity
                    onValueChange = { quantity= it },
                    label = { Text("Quantity") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(64.dp))

                // Enter Button
                Button(
                    onClick = { viewModel.onEnter() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = LightGreen),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(text = "Enter")
                }
            }
        }
    }
}

@Composable
fun DropdownMenuComponent(
    label: String,
    items: List<String>,
    selectedItem: String,
    expanded: Boolean,
    onItemSelected: (String) -> Unit
) {
    var expandState by remember { mutableStateOf(expanded) }
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.clickable { expandState = !expandState }
                )
            }
        )
        DropdownMenu(expanded = expandState, onDismissRequest = { expandState = false }) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    onItemSelected(item)
                    expandState = false
                }) {
                    Text(text = item)
                }
            }
        }
    }
}
