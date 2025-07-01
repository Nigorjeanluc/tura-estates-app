package com.example.turaestates

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.turaestates.navigation.SetupNavGraph
import com.example.turaestates.ui.theme.TuraEstatesTheme
import com.example.turaestates.viewmodel.SplashViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }
        enableEdgeToEdge()
        setContent {
            TuraEstatesTheme {
//                SetBarColor(color = MaterialTheme.colorScheme.inverseOnSurface)
                val screen by splashViewModel.startDestination
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, startDestination = screen)
            }
        }
    }


//    @Composable
//    private fun SetBarColor(color: Color) {
//        val systemUiController = rememberSystemUiController()
//        LaunchedEffect(key1 = color) {
//            systemUiController.setSystemBarsColor(color)
//        }
//    }
}
