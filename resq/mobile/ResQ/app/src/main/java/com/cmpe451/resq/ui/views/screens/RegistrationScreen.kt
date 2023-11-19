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
    import androidx.compose.material3.CheckboxDefaults
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
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.text.style.TextDecoration
    import androidx.compose.ui.unit.sp
    import androidx.lifecycle.viewmodel.compose.viewModel
    import androidx.navigation.NavController
    import com.cmpe451.resq.ui.theme.DeepBlue
    import com.cmpe451.resq.ui.theme.LightGreen
    import com.cmpe451.resq.viewmodels.RegistrationViewModel
    
    private val lexendDecaFont = FontFamily(Font(R.font.lexend_deca))
    
    
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RegistrationScreen(navController: NavController) {
    
        val viewModel: RegistrationViewModel = viewModel()

        var name by remember { mutableStateOf("") }
        var surname by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var termsAccepted by remember { mutableStateOf(false) }
    
    
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

// Name and Surname inputs in the same horizontal position
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name", color = Color.White) },
                    modifier = Modifier.weight(1f).padding(end = 8.dp), // half width and add some padding
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = DeepBlue,
                        textColor = Color.White,
                        cursorColor = Color.White
                    )
                )

                TextField(
                    value = surname,
                    onValueChange = { surname = it },
                    label = { Text("Surname", color = Color.White) },
                    modifier = Modifier.weight(1f).padding(start = 8.dp), // half width and add some padding
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = DeepBlue,
                        textColor = Color.White,
                        cursorColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // E-mail input
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = DeepBlue,
                    textColor = Color.White,
                    cursorColor = Color.White
                )
            )
    
            Spacer(modifier = Modifier.height(16.dp))
    
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
                    textColor = Color.White,
                    cursorColor = Color.White
                )
            )
    
            Spacer(modifier = Modifier.height(16.dp))
    
            // Check password input
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Check password", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = DeepBlue,
                    textColor = Color.White,
                    cursorColor = Color.White
                )
            )
    
            Spacer(modifier = Modifier.height(16.dp))
    
            // Terms and privacy policy checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = termsAccepted,
                    onCheckedChange = { termsAccepted = it },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = Color.White,
                        checkedColor = DeepBlue
                    )
                )
                Text(
                    text = "I accept the terms and privacy policy",
                    style = MaterialTheme.typography.bodySmall,
                    color = DeepBlue,
                    fontSize = 16.sp
                )
            }
    
            Spacer(modifier = Modifier.height(16.dp))
    
            // Create account button
            Button(
                onClick = {
                    viewModel.register(
                        name,
                        surname,
                        email,
                        password,
                        confirmPassword,
                        termsAccepted,
                        navController
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightGreen
                )
            ) {
                Text(
                    text = "Sign Up",
                    color = DeepBlue,
                    fontSize = 16.sp
                )
            }
    
            Spacer(modifier = Modifier.height(16.dp))
    
            // Already have an account? Log in
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Already have an account? ",
                    style = MaterialTheme.typography.bodySmall,
                    color = DeepBlue,
                    fontSize = 14.sp
                )
                TextButton(onClick = {
                    navController.navigate("login")
                }) {
                    Text(
                        text = "Sign in",
                        style = MaterialTheme.typography.bodySmall,
                        color = DeepBlue,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
            // Success and Error messages
            if (viewModel.registerMessage.value != null) {
                Text(
                    text = "Registration success",
                    color = Color.Green,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
    
            if (viewModel.errorMessage.value != null) {
                Text(
                    text = "Registration failed: ${viewModel.errorMessage.value}",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
