package com.example.turaestates.screen

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.turaestates.ui.theme.TuraEstatesTheme

@Composable
fun MainScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = bottomNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavScreen.Explore.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavScreen.Explore.route) {
                // Your explore screen here
                Text("Explore Screen")
            }
            composable(BottomNavScreen.Map.route) {
                // Your map screen here
                Text("Map Screen")
            }
            composable(BottomNavScreen.Favorites.route) {
                // Your favorites screen here
                Text("Favorites Screen")
            }
            composable(BottomNavScreen.Profile.route) {
                // Your profile screen here
                Text("Profile Screen")
            }
        }
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavScreen.Explore,
        BottomNavScreen.Map,
        BottomNavScreen.Favorites,
        BottomNavScreen.Profile
    )
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    BottomNavigation {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentDestination?.route == screen.route,
                onClick = {
                    if (currentDestination?.route != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(BottomNavScreen.Explore.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    MainScreen(
        navController = rememberNavController()
    )
}