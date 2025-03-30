package com.example.clientmaintenancier.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.clientmaintenancier.ui.screens.ForgetPasswordScreen
import com.example.clientmaintenancier.ui.screens.HomeScreen
import com.example.clientmaintenancier.ui.screens.LoginScreen
import com.example.clientmaintenancier.ui.screens.OnboardingScreen
import com.example.clientmaintenancier.ui.screens.RegistrationScreen
import com.example.clientmaintenancier.ui.screens.TasksScreen
import com.example.clientmaintenancier.ui.screens.VerificationScreen


sealed class Destination(val route: String) {
    object Login : Destination("login")
    object Registration : Destination("registration")
    object ForgotPassword : Destination("ForgotPassword")
    object Verification : Destination("verification")
    object OnBoarding : Destination("onboarding")
    object Home : Destination("home")
    object Tasks : Destination("tasks")



}

@Composable
fun NavigationScreen(navController: NavHostController) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = Destination.OnBoarding.route) {
        composable(Destination.OnBoarding.route) { OnboardingScreen(navController) }
        composable(Destination.Registration.route) { RegistrationScreen(navController) }
        composable(Destination.Verification.route) { VerificationScreen() }
        composable(Destination.Login.route) { LoginScreen(navController) }
        composable(Destination.ForgotPassword.route) { ForgetPasswordScreen(navController) }
        composable(Destination.Home.route) { HomeScreen() }
        composable(Destination.Tasks.route) { TasksScreen() }

    }
}
