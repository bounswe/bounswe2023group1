package com.cmpe451.resq.ui.views.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.cmpe451.resq.data.models.CategoryNode
import com.cmpe451.resq.ui.theme.LightGreen
import com.cmpe451.resq.ui.theme.RequestColor
import com.cmpe451.resq.viewmodels.RequestViewModel

@Composable
fun RequestScreen(
    navController: NavController,
    appContext: Context
) {
    val viewModel: RequestViewModel = viewModel()

    LaunchedEffect(key1 = true) {
        viewModel.fetchMainCategories(appContext)
    }

    val selectedCategoryState = viewModel.selectedCategory
    val categories = viewModel.categories.value


    val priorities = listOf("HIGH", "MEDIUM", "LOW")
    var selectedPriority by remember { mutableStateOf(viewModel.selectedPriority.value) }

    var quantity by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Top Bar with back button and title
        TopAppBar(
            title = { Text(text = "Request", color = RequestColor) },
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                DropdownMenuComponent(
                    label = "Category",
                    items = categories,
                    selectedItem = selectedCategoryState.value ?: CategoryNode(-1, "Select a Category", emptyList()),
                    itemToString = { it.data },
                    onItemSelected = { category ->
                        viewModel.updateCategory(category)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                DropdownMenuComponent(
                    label = "Type",
                    items = viewModel.types.value,
                    selectedItem = viewModel.selectedType.value ?: CategoryNode(-1, "Select a Type", emptyList()),
                    itemToString = { it.data },
                    onItemSelected = { type ->
                        viewModel.updateType(type)
                        viewModel.fetchItemsForType(type.id)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                DropdownMenuComponent(
                    label = "Item",
                    items = viewModel.items.value,
                    selectedItem = viewModel.selectedItem.value ?: CategoryNode(-1, "Select an Item", emptyList()),
                    itemToString = { it.data },
                    onItemSelected = { item ->
                        viewModel.updateItem(item)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                DropdownMenuComponent(
                    label = "Priority",
                    items = priorities,
                    selectedItem = selectedPriority,
                    itemToString = { it },
                    onItemSelected = { priority ->
                        selectedPriority = priority
                        viewModel.updatePriority(priority)
                    }
                )


                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = quantity, // you can bind this to a state variable to store the quantity
                    onValueChange = { quantity = it },
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
                    imageVector = if (expandState) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
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