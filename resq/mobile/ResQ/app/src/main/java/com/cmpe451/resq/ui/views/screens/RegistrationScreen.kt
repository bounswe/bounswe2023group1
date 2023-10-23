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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController
import com.cmpe451.resq.ui.theme.DeepBlue
import com.cmpe451.resq.ui.theme.LightGreen

private val lexendDecaFont = FontFamily(Font(R.font.lexend_deca))


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        // Create account text
        Text(
            text = "Create account",
            style = MaterialTheme.typography.displayLarge,
            color = DeepBlue,
            fontFamily = lexendDecaFont,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // E-mail input
        TextField(
            value = "", // TODO: Bind to a state
            onValueChange = {},
            label = { Text("E-mail", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = DeepBlue,
                textColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password input
        TextField(
            value = "", // TODO: Bind to a state
            onValueChange = {},
            label = { Text("Password", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = DeepBlue,
                textColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Check password input
        TextField(
            value = "", // TODO: Bind to a state
            onValueChange = {},
            label = { Text("Check password", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = DeepBlue,
                textColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Terms and privacy policy checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = false, // TODO: Bind to a state
                onCheckedChange = {}
            )
            Text(
                text = "I accept the terms and privacy policy",
                style = MaterialTheme.typography.bodySmall.copy(color = DeepBlue)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Create account button
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightGreen
            )
        ) {
            Text("Create account", color = DeepBlue)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Already have an account? Log in
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Already have an account? ", style = MaterialTheme.typography.bodySmall.copy(color = DeepBlue))
            TextButton(onClick = {
                navController.navigate("login")
            }) {
                Text(
                    text = "Log in",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = DeepBlue,
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }
    }
}
