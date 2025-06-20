package com.example.turaestates.auth.signin.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.turaestates.R
import com.example.turaestates.navigation.Screen
import com.example.turaestates.util.TokenManager
import com.example.turaestates.util.components.LoadingDialog
import com.example.turaestates.util.components.SocialLoginButton
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
    var passwordVisibility by remember { mutableStateOf(false) }

    val darkTheme = isSystemInDarkTheme()
    val logo = if (darkTheme)
        painterResource(id = R.drawable.logolight)
    else
        painterResource(id = R.drawable.logodark)

    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.visibility)
    else
        painterResource(id = R.drawable.visibility_off)

    LoadingDialog(isLoading = state.isLoading)

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.width(125.dp).height(125.dp),
            contentScale = ContentScale.FillWidth,
            painter = logo,
            contentDescription = "Pager Image"
        )
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF005C71)
        )
        Text(
            text = "Sign in to your account",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )

        state.error?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = it, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            placeholder = { Text("Enter your username") },
            leadingIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Email Icon"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = {
                Text(text = "Password")
            },
            label = { Text(text = "Password") },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Email Icon"
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    Log.d("ImeAction3", "Password Done Clicked")
                }
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Optional padding
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Forgot Password?",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable {
                    // Handle click here (e.g., navigate to reset screen)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSignIn(username, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            Text("Sign In")
        }

        OrDivider()

        SocialLoginButtons(
            onGoogleClick = {
                // Handle Google login
            },
            onFacebookClick = {},
        )
    }
}


@Composable
fun OrDivider() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            color = Color.Gray.copy(alpha = 0.5f)
        )

        Text(
            text = "OR",
            modifier = Modifier.padding(horizontal = 12.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Divider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            color = Color.Gray.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun SocialLoginButtons(
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SocialLoginButton(
            text = "Google",
            icon = painterResource(id = R.drawable.google), // Your Google icon
            backgroundColor = Color.White,
            textColor = Color.Black,
            borderColor = Color.LightGray,
            onClick = onGoogleClick
        )

        SocialLoginButton(
            text = "Facebook",
            icon = painterResource(id = R.drawable.facebook), // Your Facebook icon
            backgroundColor = Color(0xFF1877F2),
            textColor = Color.White,
            borderColor = Color.Transparent,
            onClick = onFacebookClick
        )
    }
}
