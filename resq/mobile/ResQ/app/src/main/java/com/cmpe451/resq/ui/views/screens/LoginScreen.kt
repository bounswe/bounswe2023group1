package com.cmpe451.resq.ui.views.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cmpe451.resq.R
import com.cmpe451.resq.ui.theme.DeepBlue
import com.cmpe451.resq.ui.theme.LightGreen
import com.cmpe451.resq.utils.NavigationItem
import com.cmpe451.resq.viewmodels.LoginViewModel


private val lexendDecaFont = FontFamily(Font(R.font.lexend_deca))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, appContext: Context) {
    val viewModel: LoginViewModel = viewModel()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "App Logo",
        modifier = Modifier.size(150.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

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
            label = { Text("E-mail", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = DeepBlue,
                focusedTextColor = Color.White,
                cursorColor = Color.White
            )
        )

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
                containerColor = DeepBlue,
                focusedTextColor = Color.White,
                cursorColor = Color.White
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
                    onCheckedChange = { rememberMe = it },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = Color.White,
                        checkedColor = DeepBlue
                    )
                )
                Text(
                    text = "Remember me",
                    style = MaterialTheme.typography.bodySmall,
                    color = DeepBlue,
                    fontSize = 14.sp
                )
            }

            TextButton(onClick = {}) {
                Text(
                    text = "Forgot password?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DeepBlue,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Login button
        Button(
            onClick = { viewModel.login(email, password, navController, appContext) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightGreen
            )
        ) {
            Text(
                "Sign in",
                color = DeepBlue,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Not Registered Yet? Create an account
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Don't have an account? ",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 14.sp
                )
            TextButton(onClick = {
                navController.navigate(NavigationItem.Register.route)
            }) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.bodySmall,
                    color = DeepBlue,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
        // Success and Error messages
        if (viewModel.loginResponse.value != null) {
            LaunchedEffect(key1 = viewModel.loginResponse.value) {
                snackbarHostState.showSnackbar(
                    message = "Sign in success",
                    duration = SnackbarDuration.Short
                )
            }
        }

        if (viewModel.errorMessage.value != null) {
            LaunchedEffect(key1 = viewModel.errorMessage.value) {
                snackbarHostState.showSnackbar(
                    message = "Sign in failed: ${viewModel.errorMessage.value}",
                    duration = SnackbarDuration.Short
                )
            }
        }
        SnackbarHost(hostState = snackbarHostState)
    }
}
