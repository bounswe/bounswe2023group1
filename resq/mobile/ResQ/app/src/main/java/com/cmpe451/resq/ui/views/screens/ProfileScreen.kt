package com.cmpe451.resq.ui.views.screens
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cmpe451.resq.viewmodels.ProfileViewModel
import com.cmpe451.resq.data.models.ProfileData
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ProfileScreen(userId: Int, navController: NavController) {
    val viewModel: ProfileViewModel = viewModel()
    LaunchedEffect(userId) {

        viewModel.getUserData(userId)
    }

    val profileData by viewModel.profile
    when (profileData) {
        null -> {
            // Data is loading
            Text("Loading...")
        }
        else -> {
            if (profileData!!.role == "Victim" || profileData!!.role == "Responder") {
                Profile(profileData = profileData!!, navController = navController)
            } else {
                // Handle other roles or unknown roles
                Text("Unknown Role")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(profileData:ProfileData, navController: NavController) {
    val name = profileData.name
    val surname = profileData.surname
    val dateOfBirth = profileData.dateOfBirth
    val role = profileData.role
    val address = profileData.address
    Column {
        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = "User Profile",
                                style = TextStyle(
                                    fontSize = 25.sp,
                                    color = Color(0xFF224957),
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF224957))
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column {
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

                    // Profile photo area
                    Surface(
                        color = Color.White, // Background color for the profile photo area
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Profile photo
                            //       Image(
                            //           painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your profile photo resource
                            //           contentDescription = null,
                            //           modifier = Modifier
                            //               .size(80.dp)
                            //               .clip(CircleShape) // Clip to a circular shape
                            //       )

                            Spacer(modifier = Modifier.width(16.dp)) // Add space between photo and info

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(5.dp)
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("Name:")
                                        }
                                        append(" $name")
                                    },
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        color = Color(0xFF224957) // Set the font color to #224957
                                    )
                                )
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("Surname:")
                                        }
                                        append(" $surname")
                                    },
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        color = Color(0xFF224957) // Set the font color to #224957
                                    )
                                )
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("Date of Birth:")
                                        }
                                        append(" $dateOfBirth")
                                    },
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        color = Color(0xFF224957) // Set the font color to #224957
                                    )
                                )
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("Role:")
                                        }
                                        append(" $role")
                                    },
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        color = Color(0xFF224957) // Set the font color to #224957
                                    )
                                )
                                if (role == "Responder") {
                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("Address:")
                                            }
                                            append(" $address")
                                        },
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            color = Color(0xFF224957)
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        if (role == "Victim") {
                            // "My Requests" button
                            Button(
                                onClick = {
                                    // Add requests action here
                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFFB356AF)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .width(200.dp)
                                    .height(50.dp)
                            ) {
                                Text(text = "My Requests")
                            }
                        } else if (role == "Responder") {
                            // "My Resources" button
                            Button(
                                onClick = {
                                    // Add Resources action here
                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFF397FE7)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .width(200.dp)
                                    .height(50.dp)
                            ) {
                                Text(text = "My Resources")
                            }
                            Button(
                                onClick = {
                                    // Add Tasks action here
                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFFE7A139)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .width(200.dp)
                                    .height(50.dp)
                            ) {
                                Text(text = "My Tasks")
                            }
                        }
                        // "Edit Profile" button
                        Button(
                            onClick = {
                                // Add profile action here
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF224957)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .width(200.dp)
                                .height(50.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "✏️",
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp)) // Add space between the icon and the button text
                                Text(text = "Edit Profile")
                            }
                        }
                    }
                }
            }
        }
    }
}
