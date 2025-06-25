package com.example.turaestates.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.turaestates.auth.signin.presentation.SigninScreen
import com.example.turaestates.screen.HomeScreen
import com.example.turaestates.screen.WelcomeScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                navController = navController
            )
        }
        composable(route = Screen.SignIn.route) {
            SigninScreen(navController = navController)
        }

        composable(Screen.SignUp.route) {
            SignupNavGraph(
                parentNavController = navController
            ) // nested graph here
        }

        composable(route = Screen.Home.route) {
            HomeScreen()
        }
    }
}