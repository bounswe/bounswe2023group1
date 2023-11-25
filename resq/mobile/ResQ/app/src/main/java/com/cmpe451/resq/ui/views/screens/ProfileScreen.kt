package com.cmpe451.resq.ui.views.screens
import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cmpe451.resq.data.models.ProfileData
import com.cmpe451.resq.viewmodels.ProfileViewModel

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
                if (userRoles.contains("VICTIM") || userRoles.contains("RESPONDER")) {
                    Profile(profileData = profileData!!, navController = navController)
                } else {
                    // Handle other roles or unknown roles
                    Text("Unknown Role")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(profileData:ProfileData, navController: NavController) {
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
    var  city by remember { mutableStateOf(profileData.city ?: "") }
    var state by remember { mutableStateOf(profileData.state ?: "") }
    var phoneNumber by remember { mutableStateOf(profileData.phoneNumber ?: "") }
    var bloodType by remember { mutableStateOf(profileData.bloodType ?: "") }

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
                                onValueChange = { name = it },
                                label = { Text("First Name") },
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


                        // Surname
                        surname?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = { surname = it },
                                label = { Text("Last Name") },
                                shape = RoundedCornerShape(15),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                                    .background(Color.White),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color.White,
                                    textColor = Color.Black,
                                    cursorColor = Color.Black
                                ))
                        }

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)

                    ) {
                        // Email
                        email?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = { email = it },
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
                                ))
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        // Email
                        phoneNumber?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = { phoneNumber = it },
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
                                ))
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        // Email
                        country?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = { country = it },
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
                                onValueChange = { city = it },
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
                                ))

                        }
                        state?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = { state = it },
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
                                ))


                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        // Weight
                        weight?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = { weight = it },
                                label = { Text("Weight") },
                                shape = RoundedCornerShape(15),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                                    .background(Color.White),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color.White,
                                    textColor = Color.Black,
                                    cursorColor = Color.Black
                                ))

                        }
                        height?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = { height = it },
                                label = { Text("Height") },
                                shape = RoundedCornerShape(15),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                                    .background(Color.White),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color.White,
                                    textColor = Color.Black,
                                    cursorColor = Color.Black,
                                ))
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        gender?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = { gender = it },
                                label = { Text("Gender") },
                                shape = RoundedCornerShape(15),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                                    .background(Color.White),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color.White,
                                    textColor = Color.Black,
                                    cursorColor = Color.Black
                                ))
                        }


                        bloodType?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = { bloodType = it },
                                label = { Text("Blood Type") },
                                shape = RoundedCornerShape(15),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                                    .background(Color.White),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color.White,
                                    textColor = Color.Black,
                                    cursorColor = Color.Black
                                ))
                        }}
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        year?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = { year = it },
                                label = { Text("Year") },
                                shape = RoundedCornerShape(15),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                                    .background(Color.White),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color.White,
                                    textColor = Color.Black,
                                    cursorColor = Color.Black
                                ))

                        }
                        month?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = { month = it },
                                label = { Text("Month") },
                                shape = RoundedCornerShape(15),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                                    .background(Color.White),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color.White,
                                    textColor = Color.Black,
                                    cursorColor = Color.Black
                                ))
                        }
                        day?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = { year = it },
                                label = { Text("Day") },
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
                        // Handle button click
                    },
                    colors =  ButtonDefaults.buttonColors(Color(0xFFB356AF)),
                    modifier = Modifier
                        .size(170.dp, 60.dp) // Set the width and height
                        .padding(4.dp)
                ) {
                    Text(text = "My Requests")
                } }
            Spacer(modifier =Modifier.height(20.dp))
            Row(
                modifier = Modifier.align(Alignment.Start)
            ) {
                Button(
                    onClick = {
                        // Handle button click
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF224957)),
                    modifier = Modifier
                        .size(170.dp, 60.dp)
                        .padding(2.dp)
                ) {
                    Text(text = "Save Details")

                }
                Spacer(modifier = Modifier.width(25.dp))
                Button(
                    onClick = {
                        // Handle button click
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF224957)),
                    modifier = Modifier
                        .size(170.dp, 60.dp)
                        .padding(2.dp)
                ) {
                    Text(text = "Request Role")
                }
            }
        }
    }
}
