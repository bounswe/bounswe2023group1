package com.cmpe451.resq.ui.views.screens
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.data.models.ProfileData
import com.cmpe451.resq.ui.theme.MyTasksColor
import com.cmpe451.resq.ui.theme.OngoingTasksColor
import com.cmpe451.resq.ui.theme.RequestColor
import com.cmpe451.resq.ui.theme.ResourceColor
import com.cmpe451.resq.viewmodels.ProfileViewModel
import java.time.Year
@Composable
fun ProfileButton(color: Color, text:String, route: String, navController: NavController) {
    Button(
        onClick = {
            if (route.isNotEmpty()){
                navController.navigate(route)
            }

        },
        colors = ButtonDefaults.buttonColors(color),
        modifier = Modifier
            .size(170.dp, 60.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun ProfilePhoto() {
    var imageUri = rememberSaveable { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
    }
    if (imageUri.value.isEmpty()) {
        Image(
            Icons.Default.AccountCircle,
            contentDescription = "User Profile",
            modifier = Modifier
                .size(150.dp)
                .clickable { launcher.launch("image/*") },
        )
    }
    else{
        val painter = rememberAsyncImagePainter(imageUri.value)
        Image(
            painter = painter,
            contentDescription = "User Profile",

            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .clickable { launcher.launch("image/*") },
            contentScale = ContentScale.Crop
        )
    }
    // TO DO: Save imageUri.value to database, and retrieve it when the user logs in again.
    // TO DO: Add deletion option
}

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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(navController: NavController, appContext: Context) {
    val viewModel: ProfileViewModel = viewModel()

    viewModel.getUserData(appContext)

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
                    Profile(profileData = profileData!!, navController = navController, viewModel, appContext)
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
    for (i in end downTo start){
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(profileData:ProfileData, navController: NavController, viewModel: ProfileViewModel, appContext: Context) {
    val genders = listOf("Male", "Female")
    val bloodTypes = listOf("AB Rh+", "AB Rh-", "A Rh+", "A Rh-", "B Rh+", "B Rh-", "O Rh+", "O Rh-")
    val years = generateYears(1900, Year.now().value)
    val months = generateMonths()
    val userSessionManager = UserSessionManager.getInstance(appContext)
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
    var isPhoneValid by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    var profileColor = Color(0xFFFFFFFF)
    when (selectedRole().value) {
        "VICTIM" -> {
            profileColor = RequestColor
        }

        "RESPONDER" -> {
            profileColor = ResourceColor
        }

        "FACILITATOR" -> {
            profileColor = MyTasksColor
        }
    }


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
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                ProfilePhoto()
            }
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
                        OutlinedTextField(
                            value = name,
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

                            OutlinedTextField(
                                value = surname,
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


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)

                    ) {


                            val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                            isEmailValid = isValidEmail

                            OutlinedTextField(
                                value = email,
                                onValueChange = {
                                    email = it
                                    isEmailValid =
                                        android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {

                        isPhoneValid = android.util.Patterns.PHONE.matcher(phoneNumber).matches()
                        OutlinedTextField(
                                value = phoneNumber,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Phone
                                ),
                                onValueChange = {
                                    phoneNumber = it.isDigit()
                                    isPhoneValid = android.util.Patterns.PHONE.matcher(it).matches()
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {


                            OutlinedTextField(
                                value = country,
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


                            OutlinedTextField(
                                value = city,
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


                            OutlinedTextField(
                                value = state,
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {


                            OutlinedTextField(
                                value = weight,
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



                            OutlinedTextField(
                                value = height,
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),

                        ) {
                        Column(modifier = Modifier.weight(1f)) {
                                TextListSelectionWithColorChange(
                                    items = genders,
                                    selectedItem = gender,
                                    onItemSelected = { gender = it },
                                    label = "Gender",
                                    color = profileColor
                                )
                            }


                        Column(modifier = Modifier.weight(1f)) {
                                TextListSelectionWithColorChange(
                                    items = bloodTypes,
                                    selectedItem = bloodType,
                                    onItemSelected = { bloodType = it },
                                    label = "Blood Type",
                                    color = profileColor
                                )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                                TextListSelectionWithColorChange(
                                    items = years,
                                    selectedItem = year,
                                    onItemSelected = { year = it },
                                    label = "Year",
                                    color = profileColor
                                )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            TextListSelectionWithColorChange(
                                items = months,
                                selectedItem = month,
                                onItemSelected = { month = it },
                                label = "Month",
                                color = profileColor
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            val days = generateDays(month)

                            TextListSelectionWithColorChange(
                                items = days,
                                selectedItem = day,
                                onItemSelected = { day = it },
                                label = "Day",
                                color = profileColor
                            )
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

            when (selectedRole().value) {
                "VICTIM" -> {
                    VictimProfileButtons(navController = navController)
                }

                "RESPONDER" -> {
                    ResponderProfileButtons(navController = navController)
                }

                "FACILITATOR" -> {
                    FacilitatorProfileButtons(navController = navController)
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
                                viewModel.updateProfile(appContext, ProfileData(
                                    name = name,
                                    surname = surname,
                                    bloodType = bloodType,
                                    country = country,
                                    city = city,
                                    state = state,
                                    year = year,
                                    month = month,
                                    day = day,
                                    email = email,
                                    gender = gender.uppercase(),
                                    height = height,
                                    weight = weight,
                                    phoneNumber = phoneNumber,
                                    selectedRole = selectedRole().value,
                                    roles = userSessionManager.getUserRoles(),
                                ))
                                if (viewModel.updateMessage.value != null) {
                                    message = "Details saved successfully."
                                }

                                else if (viewModel.errorMessage.value != null) {
                                    message = viewModel.errorMessage.value!!
                                }
                                else{
                                    message = "Details saved successfully."
                                }

                            }

                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF224957)),
                        modifier = Modifier.size(170.dp, 60.dp)
                    ) {
                        Text(text = "Save Details")
                    }

                    Spacer(modifier = Modifier.width(30.dp))
                    Button(
                        onClick = {
                            //@ TO DO Handle button click
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

@Composable
fun FacilitatorProfileButtons(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        ProfileButton(
            color = ResourceColor,
            text = "My Resources",
            route = "",
            navController = navController
        )
        Spacer(modifier = Modifier.width(30.dp))
        ProfileButton(
            color = MyTasksColor,
            text = "My Tasks",
            route = "",
            navController = navController
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        ProfileButton(
            color = RequestColor,
            text = "My Request",
            route = "",
            navController = navController
        )
        Spacer(modifier = Modifier.width(30.dp))
        ProfileButton(
            color = OngoingTasksColor,
            text = "Ongoing Tasks",
            route = "OngoingTasks",
            navController = navController
        )

    }
}

@Composable
fun ResponderProfileButtons(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        ProfileButton(
            color = ResourceColor,
            text = "My Resources",
            route = "",
            navController = navController
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        ProfileButton(
            color = MyTasksColor,
            text = "My Tasks",
            route = "",
            navController = navController
        )
    }
}

@Composable
fun VictimProfileButtons(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        ProfileButton(
            color = RequestColor,
            text = "My Request",
            route = "",
            navController = navController
        )
    }
}
