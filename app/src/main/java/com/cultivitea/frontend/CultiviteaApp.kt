package com.cultivitea.frontend

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cultivitea.frontend.ui.screens.LoginScreen
import com.cultivitea.frontend.ui.screens.RegisterScreen
import com.cultivitea.frontend.ui.theme.CultiviteaTheme

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
}

@Composable
fun CultiviteaApp() {
    CultiviteaTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.Login.route) {
            composable(Screen.Login.route) {
                LoginScreen { navController.navigate(Screen.Register.route) }
            }
            composable(Screen.Register.route) {
                RegisterScreen { navController.navigate(Screen.Login.route) }
            }
        }
    }
}