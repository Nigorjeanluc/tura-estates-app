package com.example.turaestates.auth.signup.presentation

import android.util.Log
import java.util.Calendar
import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.turaestates.auth.signin.presentation.components.LogoSection
import com.example.turaestates.auth.signin.presentation.components.OrDivider
import com.example.turaestates.auth.signin.presentation.components.SocialLoginButtons
import com.example.turaestates.navigation.Screen

@Composable
fun SignupStep1Screen(
    navController: NavController,
    parentNavController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Email regex pattern (basic validation)
    val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoSection(
            title = "Get Started",
            subtitle = "Create your account"
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = ""
            },
            label = { Text("Email") },
            placeholder = { Text("Enter your email") },
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = ""
            },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Password Icon")
                }
            },
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                errorMessage = ""
            },
            label = { Text("Confirm Password") },
            singleLine = true,
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Confirm Password Icon")
                }
            },
            trailingIcon = {
                val icon = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                when {
                    !email.matches(emailPattern) -> {
                        errorMessage = "Please enter a valid email address."
                    }
                    password != confirmPassword -> {
                        errorMessage = "Passwords do not match."
                    }
                    password.isBlank() -> {
                        errorMessage = "Password cannot be empty."
                    }
                    else -> {
                        viewModel.updateForm {
                            copy(email = email, password = password)
                        }
                        navController.navigate("signup_step2")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }

        OrDivider()

        SocialLoginButtons(
            onGoogleClick = { /* Google login */ },
            onFacebookClick = {}
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            buildAnnotatedString {
                append("Already have an account?")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                ) {
                    append(" Sign In")
                }
            },
            modifier = Modifier.clickable {
                parentNavController.navigate(Screen.SignIn.route)
            }
        )
    }
}

@Composable
fun SignupStep2Screen(
    navController: NavController,
    parentNavController: NavController,
    viewModel: SignupViewModel = hiltViewModel(),
    onSignupComplete: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var fullname by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoSection(
            title = "Get Started",
            subtitle = "Create your account"
        )

        state.error?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = it, color = Color.Red)
            Log.d("SignupScreenError", "Error: $it")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fullname,
            onValueChange = { fullname = it },
            label = { Text("Full Name") },
            placeholder = { Text("Enter your full name") },
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Person, contentDescription = "Full Name Icon")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            placeholder = { Text("Enter your phone number") },
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Phone, contentDescription = "Phone Icon")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        DateOfBirthPicker(dob = dob, onDobSelected = { dob = it })

        Spacer(modifier = Modifier.height(12.dp))

        DropdownSelector(
            label = "Gender",
            options = listOf("Male", "Female", "Other"),
            selectedOption = gender,
            onOptionSelected = { gender = it }
        )

        Spacer(modifier = Modifier.height(12.dp))

        DropdownSelector(
            label = "Interest",
            options = listOf("Buying", "Renting", "Investing"),
            selectedOption = role,
            onOptionSelected = { role = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            viewModel.updateForm {
                copy(
                    fullname = fullname,
                    dob = dob,
                    phoneNumber = phoneNumber,
                    gender = gender,
                    role = role
                )
            }
            viewModel.signup()
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Submit")
        }



        Text(
            buildAnnotatedString {
                append("Already have an account?")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                ) {
                    append(" Sign In")
                }
            },
            modifier = Modifier.clickable {
                parentNavController.navigate(Screen.SignIn.route)
            }
        )

        if (state.navigateToHome) {
            LaunchedEffect(Unit) {
                onSignupComplete()
                viewModel.onNavigated()
            }
        }
    }
}

@Composable
fun DateOfBirthPicker(
    dob: String,
    onDobSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = "%02d/%02d/%04d".format(dayOfMonth, month + 1, year)
                onDobSelected(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    // Custom text field look-alike
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shape = MaterialTheme.shapes.small
            )
            .clickable { datePickerDialog.show() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = "Date Icon",
            modifier = Modifier.padding(end = 12.dp)
        )
        Text(
            text = if (dob.isNotEmpty()) dob else "Select your birth date",
            style = MaterialTheme.typography.bodyLarge,
            color = if (dob.isNotEmpty()) MaterialTheme.colorScheme.onSurface else Color.Gray
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selection ->
                DropdownMenuItem(
                    text = { Text(selection) },
                    onClick = {
                        onOptionSelected(selection)
                        expanded = false
                    }
                )
            }
        }
    }
}
