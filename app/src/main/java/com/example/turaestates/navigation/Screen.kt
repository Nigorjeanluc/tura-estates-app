package com.example.turaestates.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen(route = "welcome_screen")
    object SignIn : Screen(route = "sign_in_screen")
    object SignUp : Screen(route = "sign_up_screen")
    object Home : Screen(route = "home_screen")
}