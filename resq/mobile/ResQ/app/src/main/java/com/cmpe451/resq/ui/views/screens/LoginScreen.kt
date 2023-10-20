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
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration


val lexendDecaFont = FontFamily(Font(R.font.lexend_deca))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
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
            color = Color(0xff224957),
            fontWeight = FontWeight.Medium,
            fontFamily = lexendDecaFont
        )

        Spacer(modifier = Modifier.height(32.dp)) // Provide space

        // Login input
        TextField(
            value = "", // Bind to a state in a real-world scenario
            onValueChange = {},
            label = { Text("Login", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(	0xff224957)
            )        )

        Spacer(modifier = Modifier.height(16.dp)) // Provide space

        // Password input
        TextField(
            value = "", // Bind to a state in a real-world scenario
            onValueChange = {},
            label = { Text("Password", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(	0xff224957)
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
                    checked = false, // Bind to a state in a real-world scenario
                    onCheckedChange = {}
                )
                Text(
                    text = "Remember me",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xff224957))
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

        Spacer(modifier = Modifier.height(16.dp)) // Provide space

        // Login button
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff20df7f)
            )
        ) {
            Text("Login", color = Color(0xff224957))
        }

        Spacer(modifier = Modifier.height(16.dp)) // Provide space

        // Not Registered Yet? Create an account
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Not Registered Yet? ", style = MaterialTheme.typography.bodySmall)
            TextButton(onClick = {}) {
                Text(
                    text = "Create an account",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xff224957),
                    fontWeight = FontWeight.Medium,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}
