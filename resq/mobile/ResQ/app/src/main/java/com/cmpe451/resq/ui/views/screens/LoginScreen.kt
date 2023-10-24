package com.cmpe451.resq.ui.views.screens

import com.cmpe451.resq.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController
import com.cmpe451.resq.data.remote.AuthApi
import com.cmpe451.resq.domain.LoginUseCase
import com.cmpe451.resq.ui.theme.DeepBlue
import com.cmpe451.resq.ui.theme.LightGreen
import com.cmpe451.resq.viewmodels.LoginViewModel


private val lexendDecaFont = FontFamily(Font(R.font.lexend_deca))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {

    val authApi = AuthApi()
    val loginUseCase = LoginUseCase(authApi)
    val viewModel = LoginViewModel(loginUseCase)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }


    val user by viewModel.user
    if (user != null) {
        // Navigate to another screen
        navController.navigate("login")
    }
    println("DEBUGGG")
    println(user?.password)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Sign in text
        Text(
            text = "Sign in",
            style = MaterialTheme.typography.displayLarge,
            color = DeepBlue,
            fontWeight = FontWeight.Medium,
            fontFamily = lexendDecaFont
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Login input
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Login", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = DeepBlue
            )        )

        Spacer(modifier = Modifier.height(16.dp)) // Provide space

        // Password input
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = DeepBlue
            )
        )

        Spacer(modifier = Modifier.height(8.dp)) // Provide space

        // Remember me and Forgot password?
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Text(
                    text = "Remember me",
                    style = MaterialTheme.typography.bodySmall,
                    color = DeepBlue
                )
            }

            TextButton(onClick = {}) {
                Text(
                    text = "Forgot password?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Login button
        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightGreen
            )
        ) {
            Text("Login", color = DeepBlue)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Not Registered Yet? Create an account
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Not Registered Yet? ", style = MaterialTheme.typography.bodySmall)
            TextButton(onClick = {
                navController.navigate("registration")
            }) {
                Text(
                    text = "Create an account",
                    style = MaterialTheme.typography.bodySmall,
                    color = DeepBlue,
                    fontWeight = FontWeight.Medium,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}
