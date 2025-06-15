package com.example.turaestates.auth.signin.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.turaestates.navigation.Screen
import com.example.turaestates.util.TokenManager
import com.example.turaestates.util.components.LoadingDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun SigninScreen(
    navController: NavController,
    viewModel: SigninViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    SigninContent(
        state = state,
        onSignIn = { username, password ->
            viewModel.signin(username, password)
        }
    )

    // Save token and navigate if sign-in is successful
    LaunchedEffect(state.signinResponse?.token) {
//        state.signinResponse?.token.let { token ->
//            scope.launch(Dispatchers.IO) {
//                TokenManager.saveToken(context, token.toString())
//            }
//            navController.navigate(Screen.Home.route) {
//                popUpTo(Screen.SignIn.route) { inclusive = true }
//            }
//        }
        if (state.navigateToHome) {
            scope.launch(Dispatchers.IO) {
                TokenManager.saveToken(context, state.signinResponse?.token.toString())
            }
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.SignIn.route) { inclusive = true }
            }
            viewModel.onNavigated() // Reset the flag
        }
    }
}

@Composable
fun SigninContent(
    state: SigninViewState,
    onSignIn: (String, String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LoadingDialog(isLoading = state.isLoading)

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSignIn(username, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            Text("Sign In")
        }

        state.error?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = it, color = Color.Red)
        }
    }
}
