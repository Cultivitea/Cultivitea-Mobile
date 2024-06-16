package com.cultivitea.frontend

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cultivitea.frontend.data.api.response.DiscussionItem
import com.cultivitea.frontend.ui.composables.BottomNavigationBar
import com.cultivitea.frontend.ui.screens.AddDiscussionScreen
import com.cultivitea.frontend.ui.screens.DetectorScreen
import com.cultivitea.frontend.ui.screens.DiscussionDetailScreen
import com.cultivitea.frontend.ui.screens.EditProfileScreen
import com.cultivitea.frontend.ui.screens.ForumScreen
import com.cultivitea.frontend.ui.screens.LoginScreen
import com.cultivitea.frontend.ui.screens.ProfileScreen
import com.cultivitea.frontend.ui.screens.RegisterScreen
import com.cultivitea.frontend.ui.theme.CultiviteaTheme
import com.cultivitea.frontend.viewmodel.MainViewModel
import com.google.gson.Gson

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Detector : Screen("detector")
    object Forum : Screen("forum")
    object Profile : Screen("profile")
    object EditProfile : Screen("editprofile")
    object AddDiscussion : Screen("addDiscussion")
}

@Composable
fun CultiviteaApp(viewModel: MainViewModel, startDestination: String = Screen.Login.route) {
    CultiviteaTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                if (navController.currentBackStackEntryAsState().value?.destination?.route in listOf(
                        Screen.Detector.route,
                        Screen.Forum.route,
                        Screen.Profile.route
                    )) {
                    BottomNavigationBar(navController)
                }
            }
        ) { innerPadding ->
            NavHost(navController = navController, startDestination = startDestination, Modifier.padding(innerPadding)) {
                composable(Screen.Login.route) {
                    LoginScreen(
                        onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                        viewModel = viewModel,
                        onLoginSuccess = {
                            navController.navigate(Screen.Detector.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    )
                }
                composable(Screen.Register.route) {
                    RegisterScreen(onNavigateToLogin = { navController.navigate(Screen.Login.route) }, viewModel)
                }
                composable(Screen.Detector.route) {
                    DetectorScreen(viewModel)
                }
                composable(Screen.Forum.route) {
                    ForumScreen(navController, viewModel)
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(navController, viewModel)
                }
                composable(Screen.EditProfile.route) {
                    EditProfileScreen(navController, viewModel)
                }
                composable(Screen.AddDiscussion.route) {
                    AddDiscussionScreen(navController, viewModel) // Added AddDiscussionScreen composable
                }
                composable(
                    "discussionDetail/{discussionItem}",
                    arguments = listOf(navArgument("discussionItem") { type = NavType.StringType })
                ) { backStackEntry ->
                    val discussionJson = backStackEntry.arguments?.getString("discussionItem")
                    val discussionItem = Gson().fromJson(discussionJson, DiscussionItem::class.java)
                    DiscussionDetailScreen(navController, discussionItem, viewModel)
                }
            }
        }
    }
}

