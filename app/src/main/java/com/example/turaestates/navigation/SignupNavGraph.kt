package com.example.turaestates.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.turaestates.auth.signup.presentation.SignupStep1Screen
import com.example.turaestates.auth.signup.presentation.SignupStep2Screen


@Composable
fun SignupNavGraph(parentNavController: NavHostController) {
    val localNavController = rememberNavController()

    NavHost(
        navController = localNavController,
        startDestination = "signup_step1"
    ) {
        composable("signup_step1") {
            SignupStep1Screen(
                navController = localNavController,
                parentNavController = parentNavController
            )
        }
        composable("signup_step2") {
            SignupStep2Screen(
                navController = localNavController,
                onSignupComplete = {
                    // Navigate to home in parent navController
                    parentNavController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                }
            )
        }
    }
}