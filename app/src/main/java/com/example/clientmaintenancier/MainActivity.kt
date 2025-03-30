package com.example.clientmaintenancier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clientmaintenancier.navigation.Destination
import com.example.clientmaintenancier.ui.screens.ForgetPasswordScreen
import com.example.clientmaintenancier.ui.screens.LoginScreen
import com.example.clientmaintenancier.ui.screens.OnboardingScreen
import com.example.clientmaintenancier.ui.screens.RegistrationScreen
import com.example.clientmaintenancier.ui.screens.VerificationScreen
import com.example.clientmaintenancier.ui.theme.ClientMaintenancierTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClientMaintenancierTheme  {
                val navController = rememberNavController()
                NavigationScreen(navController)
            }
        }
    }
}


@Composable
fun NavigationScreen(navController: NavHostController) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = Destination.OnBoarding.route) {
        composable(Destination.OnBoarding.route) { OnboardingScreen(navController) }
        composable(Destination.Registration.route) { RegistrationScreen(navController) }
        composable(Destination.Verification.route) { VerificationScreen() }
        composable(Destination.Login.route) { LoginScreen(navController) }
        composable(Destination.ForgotPassword.route) { ForgetPasswordScreen() }

    }
}

