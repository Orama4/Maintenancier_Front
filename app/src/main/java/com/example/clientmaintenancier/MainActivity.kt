package com.example.clientmaintenancier

import AnimatedBottomNavigationBar
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clientmaintenancier.api.RetrofitClient
import com.example.clientmaintenancier.navigation.Screen
import com.example.clientmaintenancier.navigation.NavigationScreen
import com.example.clientmaintenancier.navigation.currentRoute
import com.example.clientmaintenancier.repositories.AuthRepository
import com.example.clientmaintenancier.repositories.DeviceRepository
import com.example.clientmaintenancier.repositories.TaskRepository
import com.example.clientmaintenancier.ui.screens.ForgetPasswordScreen
import com.example.clientmaintenancier.ui.screens.HomeScreen
import com.example.clientmaintenancier.ui.screens.LoginScreen
import com.example.clientmaintenancier.ui.screens.OnboardingScreen
import com.example.clientmaintenancier.ui.screens.RegistrationScreen
import com.example.clientmaintenancier.ui.screens.TaskDetailsScreen
import com.example.clientmaintenancier.ui.screens.TasksScreen
import com.example.clientmaintenancier.ui.screens.VerificationScreen
import com.example.clientmaintenancier.ui.theme.ClientMaintenancierTheme
import com.example.clientmaintenancier.viewmodels.DeviceViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val apiService = RetrofitClient.apiService
        val deviceRepository = DeviceRepository(apiService)
        val authRepository = AuthRepository(RetrofitClient.authApiService)
        val TaskRepository = TaskRepository(apiService)

        setContent {
            ClientMaintenancierTheme {
                val navController = rememberNavController()
                val currentRoute = currentRoute(navController)
                val shouldShowBottomBar = remember(currentRoute) {
                     derivedStateOf {
                        currentRoute?.let { route ->
                            !route.startsWith(Screen.OnBoarding.route) &&
                                    !route.startsWith(Screen.Login.route) &&
                                    !route.startsWith(Screen.Registration.route) &&
                                    !route.startsWith(Screen.ResetPassword.route) &&
                                    !route.startsWith(Screen.ForgotPassword.route) &&
                                    !route.startsWith(Screen.Verification.route) &&
                                    !route.startsWith(Screen.VerificationOtp.route)
                        } ?: false
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F5F5))
                        .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                ) {
                    // Pass the repository, not viewModel
                    NavigationScreen(navController, deviceRepository, authRepository,TaskRepository)
                    if (shouldShowBottomBar.value) {
                        AnimatedBottomNavigationBar(navController)
                    }                }
            }
        }
    }
}



