package com.example.turaestates.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object Explore : BottomNavScreen("explore", "Explore", Icons.Default.Home)
    object Map : BottomNavScreen("map", "Map", Icons.Default.Map)
    object Favorites : BottomNavScreen("favorites", "Favorites", Icons.Default.Favorite)
    object Profile : BottomNavScreen("profile", "Profile", Icons.Default.Person)
}