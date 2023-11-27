package com.cmpe451.resq.ui.views.screens
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cmpe451.resq.data.models.ProfileData
import com.cmpe451.resq.viewmodels.ProfileViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.cmpe451.resq.data.manager.UserSessionManager

import java.time.Year
import androidx.compose.material.MaterialTheme.typography
import kotlinx.coroutines.launch
import androidx.compose.material.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextListSelectionWithColorChange(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    label: String,
    color: Color
) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(selectedItem) }

    Box(
        modifier = Modifier
            .clickable { expanded = true }
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .padding(4.dp)
                .background(Color.White),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        selected = item
                        onItemSelected(item)
                        expanded = false
                    },
                    modifier = Modifier.background(if (item == selected) color else Color.Transparent)
                ) {
                    Text(text = item, color = if (item == selected) Color.White else Color.Black)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(navController: NavController, appContext: Context) {
    val viewModel: ProfileViewModel = viewModel()

    viewModel.getUserData(appContext)

    val allRoles = listOf("VICTIM", "RESPONDER", "FACILITATOR")
    val userRoles = UserSessionManager.getInstance(appContext).getUserRoles()
    val availableRoles = allRoles.filter { !userRoles.contains(it) }

    val profileData by viewModel.profile
    when (profileData) {
        null -> {
            // Data is loading
            Text("Loading...")
        }
        else -> {
            val userRoles = profileData!!.roles
            if (userRoles != null) {
                if (userRoles.contains("VICTIM") || userRoles.contains("RESPONDER") || userRoles.contains("FACILITATOR")) {

                    Profile(profileData = profileData!!, navController = navController, availableRoles, viewModel, appContext)
                } else {

                    Text("Unknown Role")
                }
            }
        }
    }
}
private fun String.letterOrSpace() = filter { it.isLetter() || it.isWhitespace() }
private fun String.isDigit() = filter { it.isDigit() }

fun generateYears(start: Int, end: Int): List<String>{
    val years = mutableListOf<String>()
    for (i in start..end){
        years.add(i.toString())
    }
    return years
}

fun generateMonths(): List<String>{
    val months = mutableListOf<String>()
    for (i in 1..12){
        months.add(i.toString())
    }
    return months
}

fun generateDays(month: String): List<String>{
    val days = mutableListOf<String>()
    val end = when (month){
        "1", "3", "5", "7", "8", "10", "12" -> 31
        "4", "6", "9", "11" -> 30
        "2" -> 28
        else -> 31
    }
    for (i in 1..end){
        days.add(i.toString())
    }
    return days
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Profile(profileData:ProfileData, navController: NavController, availableRoles: List<String>, viewModel: ProfileViewModel, appContext: Context) {
    val genders = listOf("Male", "Female")
    val bloodTypes = listOf("AB Rh+", "AB Rh-", "A Rh+", "A Rh-", "B Rh+", "B Rh-", "O Rh+", "O Rh-")
    val years = generateYears(1900, Year.now().value)
    val months = generateMonths()
    var selectedRole = { mutableStateOf(profileData.selectedRole ?: "") }
    var name by remember { mutableStateOf(profileData.name ?: "") }
    var surname by remember { mutableStateOf(profileData.surname ?: "") }
    var year by remember { mutableStateOf(profileData.year ?: "") }
    var month by remember { mutableStateOf(profileData.month ?: "") }
    var day by remember { mutableStateOf(profileData.day ?: "") }
    var email by remember { mutableStateOf(profileData.email ?: "") }
    var weight by remember { mutableStateOf(profileData.weight ?: "") }
    var gender by remember { mutableStateOf(profileData.gender ?: "") }
    var height by remember { mutableStateOf(profileData.height ?: "") }
    var country by remember { mutableStateOf(profileData.country ?: "") }
    var city by remember { mutableStateOf(profileData.city ?: "") }
    var state by remember { mutableStateOf(profileData.state ?: "") }
    var phoneNumber by remember { mutableStateOf(profileData.phoneNumber ?: "") }
    var bloodType by remember { mutableStateOf(profileData.bloodType ?: "") }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPhoneValid  by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent(
                availableRoles = availableRoles,
                onRoleSelected = { selectedRole ->
                    viewModel.selectRole(selectedRole, appContext)
                    // Handle the role selection
                    coroutineScope.launch { modalBottomSheetState.hide() }
                }
            )
        },
        sheetState = modalBottomSheetState,

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(
                        text = "Account",
                        style = TextStyle(
                            fontSize = 25.sp,
                            color = Color(0xFF224957),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    Icons.Default.AccountCircle,
                    contentDescription = "User Profile",
                    modifier = Modifier
                        .size(150.dp)
                        .weight(1f)
                )
                Text(
                    text = "$name $surname",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(Color.White)
                        ) {
                            name?.let {
                                OutlinedTextField(
                                    value = it,
                                    onValueChange = { name = it.letterOrSpace() },
                                    label = { Text("First Name") },
                                    shape = RoundedCornerShape(15),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                        .background(Color.White),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Text
                                    ),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        containerColor = Color.White,
                                        textColor = Color.Black,
                                        cursorColor = Color.Black

                                    )

                                )
                            }


                            surname?.let {
                                OutlinedTextField(
                                    value = it,
                                    onValueChange = { surname = it.letterOrSpace() },
                                    label = { Text("Last Name") },
                                    shape = RoundedCornerShape(15),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                        .background(Color.White),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Text
                                    ),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        containerColor = Color.White,
                                        textColor = Color.Black,
                                        cursorColor = Color.Black
                                    )
                                )
                            }

                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)

                        ) {
                            email?.let {
                                val isValidEmail =
                                    android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                                isEmailValid = isValidEmail

                                OutlinedTextField(
                                    value = it,
                                    onValueChange = {
                                        email = it
                                        isEmailValid =
                                            android.util.Patterns.EMAIL_ADDRESS.matcher(it)
                                                .matches()
                                    },
                                    label = { Text("Email") },
                                    shape = RoundedCornerShape(15),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                        .background(Color.White),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        containerColor = Color.White,
                                        textColor = Color.Black,
                                        cursorColor = Color.Black,
                                        focusedBorderColor = if (isEmailValid) Color.Black else Color.Red
                                    ),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Email
                                    )
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            phoneNumber?.let {
                                OutlinedTextField(
                                    value = it,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Phone
                                    ),
                                    onValueChange = {
                                        phoneNumber = it.isDigit()
                                        isPhoneValid =
                                            android.util.Patterns.PHONE.matcher(it).matches()
                                    },

                                    label = { Text("Phone Number") },
                                    shape = RoundedCornerShape(15),

                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                        .background(Color.White),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        containerColor = Color.White,
                                        textColor = Color.Black,
                                        cursorColor = Color.Black,
                                        focusedBorderColor = if (isPhoneValid) Color.Black else Color.Red
                                    )
                                )

                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            country?.let {
                                OutlinedTextField(
                                    value = it,
                                    onValueChange = { country = it.letterOrSpace() },
                                    label = { Text("Country") },
                                    shape = RoundedCornerShape(15),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                        .background(Color.White),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        containerColor = Color.White,
                                        textColor = Color.Black,
                                        cursorColor = Color.Black,

                                        )
                                )
                            }
                            city?.let {
                                OutlinedTextField(
                                    value = it,
                                    onValueChange = { city = it.letterOrSpace() },
                                    label = { Text("City") },
                                    shape = RoundedCornerShape(15),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                        .background(Color.White),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        containerColor = Color.White,
                                        textColor = Color.Black,
                                        cursorColor = Color.Black,
                                    )
                                )

                            }
                            state?.let {
                                OutlinedTextField(
                                    value = it,
                                    onValueChange = { state = it.letterOrSpace() },
                                    label = { Text("State") },
                                    shape = RoundedCornerShape(15),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                        .background(Color.White),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        containerColor = Color.White,
                                        textColor = Color.Black,
                                        cursorColor = Color.Black,
                                    )
                                )


                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {

                            weight?.let {
                                OutlinedTextField(
                                    value = it,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Number
                                    ),
                                    onValueChange = { weight = it.isDigit() },
                                    label = { Text("Weight (kg)") },
                                    shape = RoundedCornerShape(15),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                        .background(Color.White),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        containerColor = Color.White,
                                        textColor = Color.Black,
                                        cursorColor = Color.Black
                                    )
                                )

                            }
                            height?.let {
                                OutlinedTextField(
                                    value = it,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Number
                                    ),
                                    onValueChange = { height = it.isDigit() },
                                    label = { Text("Height (cm)") },
                                    shape = RoundedCornerShape(15),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                        .background(Color.White),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        containerColor = Color.White,
                                        textColor = Color.Black,
                                        cursorColor = Color.Black,
                                    )
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),

                            ) {
                            Column(modifier = Modifier.weight(1f)) {
                                gender?.let {
                                    TextListSelectionWithColorChange(
                                        items = genders,
                                        selectedItem = gender,
                                        onItemSelected = { gender = it },
                                        label = "Gender",
                                        color = Color(0xFFB356AF)
                                    )
                                }
                            }


                            Column(modifier = Modifier.weight(1f)) {
                                bloodType?.let {
                                    TextListSelectionWithColorChange(
                                        items = bloodTypes,
                                        selectedItem = bloodType,
                                        onItemSelected = { bloodType = it },
                                        label = "Blood Type",
                                        color = Color(0xFFB356AF)
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                year?.let {
                                    TextListSelectionWithColorChange(
                                        items = years,
                                        selectedItem = year,
                                        onItemSelected = { year = it },
                                        label = "Year",
                                        color = Color(0xFFB356AF)
                                    )
                                }
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                month?.let {
                                    TextListSelectionWithColorChange(
                                        items = months,
                                        selectedItem = month,
                                        onItemSelected = { month = it },
                                        label = "Month",
                                        color = Color(0xFFB356AF)
                                    )
                                }
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                val days = generateDays(month)
                                day?.let {
                                    TextListSelectionWithColorChange(
                                        items = days,
                                        selectedItem = day,
                                        onItemSelected = { day = it },
                                        label = "Day",
                                        color = Color(0xFFB356AF)
                                    )
                                }
                            }
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Button(
                        onClick = {
                            // @TO DO: Handle button click
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFFB356AF)),
                        modifier = Modifier
                            .size(170.dp, 60.dp)
                    ) {
                        Text(text = "My Requests")
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Button(
                            onClick = {

                                if (!isEmailValid and !isPhoneValid) {
                                    // @TO DO: Save details
                                    message = "Please check your email address and phone number."

                                } else if (!isPhoneValid) {
                                    message = "Please check your phone number."

                                } else if (!isEmailValid) {
                                    message = "Please check your email address."
                                } else {
                                    // @TO DO Handle Save Details button click
                                    message = "Details saved successfully."
                                }

                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF224957)),
                            modifier = Modifier.size(170.dp, 60.dp)
                        ) {
                            Text(text = "Save Details")
                        }

                        Spacer(modifier = Modifier.width(25.dp))
                        Button(
                            onClick = {
                                //@ TO DO Handle button click
                                coroutineScope.launch {
                                    modalBottomSheetState.show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF224957)),
                            modifier = Modifier
                                .size(170.dp, 60.dp)
                        ) {
                            Text(text = "Request Role")
                        }
                    }
                }

            }

        }
        LaunchedEffect(key1 = message) {
            if (message.isNotEmpty()) {
                snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

}

@Composable
fun BottomSheetContent(availableRoles: List<String>, onRoleSelected: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Select a Role", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))
        Divider()
        // List the available roles for the user to choose
        availableRoles.forEach { role ->
            TextButton(
                onClick = { onRoleSelected(role) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(role, style = MaterialTheme.typography.bodyMedium)
            }
            Divider()
        }
    }
}