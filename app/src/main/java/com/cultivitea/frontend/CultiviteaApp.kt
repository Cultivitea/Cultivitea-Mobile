package com.cultivitea.frontend

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cultivitea.frontend.ui.screens.DetectorScreen
import com.cultivitea.frontend.ui.screens.ForumScreen
import com.cultivitea.frontend.ui.screens.LoginScreen
import com.cultivitea.frontend.ui.screens.RegisterScreen
import com.cultivitea.frontend.ui.theme.CultiviteaTheme
import com.cultivitea.frontend.viewmodel.MainViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Detector : Screen("detector")
    object Forum : Screen("forum")
}

@Composable
fun CultiviteaApp(viewModel: MainViewModel, startDestination: String = Screen.Login.route) {
    CultiviteaTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = startDestination) {
            composable(Screen.Login.route) {
                LoginScreen { navController.navigate(Screen.Register.route) }
            }
            composable(Screen.Register.route) {
                RegisterScreen { navController.navigate(Screen.Login.route) }
            }
            composable(Screen.Detector.route) {
                DetectorScreen(viewModel)
            }
            composable(Screen.Forum.route) {
                ForumScreen()
            }
        }
    }
}
