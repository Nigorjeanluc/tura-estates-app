package com.example.turaestates.auth.signup.presentation

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import com.example.turaestates.auth.signin.presentation.components.LogoSection
import com.example.turaestates.auth.signin.presentation.components.OrDivider
import com.example.turaestates.auth.signin.presentation.components.SocialLoginButtons
import com.example.turaestates.auth.signup.data.remote.SignupRequest
import com.example.turaestates.auth.signup.domain.model.SignupNetworkError
import com.example.turaestates.navigation.Screen
import com.example.turaestates.util.TokenManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun SignupStep1Screen(
    navController: NavController,
    parentNavController: NavController,
    viewModel: SignupViewModel
) {
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
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
                errorMessage = ""
                viewModel.updateEmail(it)
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
                errorMessage = ""
                viewModel.updatePassword(it)
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignupStep2Screen(
    navController: NavController,
    parentNavController: NavController,
    viewModel: SignupViewModel,
    onSignupComplete: () -> Unit,
) {
    val context = LocalContext.current
    val form by viewModel.form.observeAsState(SignupRequest())
    val state by viewModel.state.observeAsState(SignupViewState())

    val signupError by viewModel.signupError.observeAsState()  // SignupNetworkError?

    // Local validation error message (for quick UI validation before API call)
    var localErrorMessage by remember { mutableStateOf("") }

    // FIXED: Get email and password from ViewModel's LiveData, not from form
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")

    var fullname by remember { mutableStateOf(form.fullname) }
    var dob by remember { mutableStateOf(form.dob) }
    var phoneNumber by remember { mutableStateOf(form.phoneNumber) }
    var gender by remember { mutableStateOf(form.gender) }
    var role by remember { mutableStateOf(form.role) }

    LaunchedEffect(form) {
        fullname = form.fullname
        dob = form.dob
        phoneNumber = form.phoneNumber
        gender = form.gender
        role = form.role
    }

    // Display backend error messages (list or single)
    val backendErrorMessages = when (val err = signupError) {
        is SignupNetworkError.ValidationError -> err.messages
        is SignupNetworkError.GeneralError -> listOf(err.type.message)
        else -> emptyList()
    }

    fun generateUsernameFromFullName(fullName: String): String {
        val cleanName = fullName
            .lowercase()
            .replace(Regex("[^a-z\\s]"), "") // remove non-letter characters
            .trim()

        val parts = cleanName.split(Regex("\\s+"))
        val base = when (parts.size) {
            0 -> "user"
            1 -> parts[0]
            else -> parts[0] + parts.last()
        }

        val randomNumber = (100..999).random()
        return "$base$randomNumber"
    }

    fun isValidPhone(phone: String): Boolean {
        val rwandaPattern = Regex("^07[2-8][0-9]{7}\$")
        val internationalPattern = Regex("^\\+[1-9][0-9]{7,14}\$")
        return rwandaPattern.matches(phone) || internationalPattern.matches(phone)
    }

    fun isAtLeast18YearsOld(dobString: String): Boolean {
        return try {
            val parts = dobString.split("/")
            val day = parts[0].toInt()
            val month = parts[1].toInt() - 1
            val year = parts[2].toInt()

            val dobCalendar = Calendar.getInstance().apply {
                set(year, month, day, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val today = Calendar.getInstance()
            var age = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR)

            if (
                today.get(Calendar.MONTH) < dobCalendar.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == dobCalendar.get(Calendar.MONTH) &&
                        today.get(Calendar.DAY_OF_MONTH) < dobCalendar.get(Calendar.DAY_OF_MONTH))
            ) {
                age -= 1
            }

            age >= 18
        } catch (e: Exception) {
            false
        }
    }

    if (state.navigateToHome) {
        LaunchedEffect(Unit) {
            TokenManager.saveToken(context, state.signupResponse?.token.toString())
            onSignupComplete()
            viewModel.onNavigated()
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoSection(title = "Get Started", subtitle = "Create your account")

        Spacer(modifier = Modifier.height(12.dp))

        // Show backend error messages (list) first if any
        if (backendErrorMessages.isNotEmpty()) {
            backendErrorMessages.forEach { msg ->
                Text(text = msg, color = Color.Red, modifier = Modifier.padding(vertical = 2.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Show local validation error (single string)
        if (localErrorMessage.isNotBlank()) {
            Text(text = localErrorMessage, color = Color.Red)
            Spacer(modifier = Modifier.height(12.dp))
        }

        // -- Your input fields here, unchanged --
        OutlinedTextField(
            value = fullname,
            onValueChange = {
                fullname = it
                localErrorMessage = ""
                viewModel.updateForm { copy(fullname = it) }
            },
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
            onValueChange = {
                phoneNumber = it
                localErrorMessage = ""
                viewModel.updateForm { copy(phoneNumber = it) }
            },
            label = { Text("Phone Number") },
            placeholder = { Text("+2507XXXXXXXX") },
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Phone, contentDescription = "Phone Icon")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        DateOfBirthPicker(dob = dob, onDobSelected = {
            dob = it
            localErrorMessage = ""
            viewModel.updateForm { copy(dob = it) }
        })

        Spacer(modifier = Modifier.height(12.dp))

        DropdownSelector(
            label = "Gender",
            options = listOf("Male", "Female", "Other"),
            selectedOption = gender,
            onOptionSelected = {
                gender = it
                localErrorMessage = ""
                viewModel.updateForm { copy(gender = it) }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        DropdownSelector(
            label = "Interest",
            options = listOf("Buying", "Renting", "Investing"),
            selectedOption = role,
            onOptionSelected = {
                role = it
                localErrorMessage = ""
                viewModel.updateForm { copy(role = it) }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            viewModel.clearError()

            // Clear local errors on new submit
            localErrorMessage = ""

            val error = when {
                fullname.isBlank() -> "Full name is required"
                !isValidPhone(phoneNumber) -> "Enter a valid Rwandan phone number (e.g., +2507xxxxxxxx)"
                dob.isBlank() -> "Date of birth is required"
                !isAtLeast18YearsOld(dob) -> "You must be at least 18 years old"
                gender.isBlank() -> "Please select your gender"
                role.isBlank() -> "Please select your interest"
                else -> null
            }

            if (error != null) {
                localErrorMessage = error
                return@Button
            }

            // Format dob to ISO string before sending
            val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00.000'Z'")

            val dobIso = try {
                val parsedDate = LocalDate.parse(dob, inputFormatter)
                parsedDate.format(outputFormatter)
            } catch (e: Exception) {
                dob
            }

            viewModel.updateForm {
                copy(
                    username = generateUsernameFromFullName(fullname),
                    email = email,
                    password = password,
                    fullname = fullname,
                    dob = dobIso,
                    phoneNumber = phoneNumber,
                    gender = gender,
                    role = role
                )
            }

            Log.d("SignupViewModel", "Form: $form")

            viewModel.signup()
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                    },
                )
            }
        }
    }
}
