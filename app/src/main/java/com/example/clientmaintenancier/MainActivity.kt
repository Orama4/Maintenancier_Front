package com.example.clientmaintenancier

import AnimatedBottomNavigationBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clientmaintenancier.navigation.Screen
import com.example.clientmaintenancier.navigation.NavigationScreen
import com.example.clientmaintenancier.ui.screens.ForgetPasswordScreen
import com.example.clientmaintenancier.ui.screens.HomeScreen
import com.example.clientmaintenancier.ui.screens.LoginScreen
import com.example.clientmaintenancier.ui.screens.OnboardingScreen
import com.example.clientmaintenancier.ui.screens.RegistrationScreen
import com.example.clientmaintenancier.ui.screens.TaskDetailsScreen
import com.example.clientmaintenancier.ui.screens.TasksScreen
import com.example.clientmaintenancier.ui.screens.VerificationScreen
import com.example.clientmaintenancier.ui.theme.ClientMaintenancierTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ClientMaintenancierTheme  {
                val navController = rememberNavController()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F5F5))
                        .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                ) {
                    NavigationScreen(navController)
                    AnimatedBottomNavigationBar(navController)

                }
            }
        }
    }
}



