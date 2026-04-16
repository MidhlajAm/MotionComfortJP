package com.example.myjp.ui.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myjp.ui.screens.home.HomePage

@Composable
fun AppNavRoot(
    darkModeEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomePage(
                darkModeEnabled = darkModeEnabled,
                onDarkModeChange = onDarkModeChange
            )
        }
    }
}
