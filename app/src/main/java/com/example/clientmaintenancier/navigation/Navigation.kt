package com.example.clientmaintenancier.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.clientmaintenancier.ui.screens.DeviceDetailsScreen
import com.example.clientmaintenancier.ui.screens.DeviceInfo
import com.example.clientmaintenancier.ui.screens.DeviceStatus
import com.example.clientmaintenancier.ui.screens.ForgetPasswordScreen
import com.example.clientmaintenancier.ui.screens.HomeScreen
import com.example.clientmaintenancier.ui.screens.LoginScreen
import com.example.clientmaintenancier.ui.screens.OnboardingScreen
import com.example.clientmaintenancier.ui.screens.RegistrationScreen
import com.example.clientmaintenancier.ui.screens.TaskDetailsScreen
import com.example.clientmaintenancier.ui.screens.TaskInfo
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
    object TaskDetails : Destination("TaskDetails")
    object DeviceDetails : Destination("DeviceDetails")

    // For routes with parameters
    fun createRoute(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

@Composable
fun NavigationScreen(navController: NavHostController) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = Destination.Tasks.route) {
        composable(Destination.OnBoarding.route) { OnboardingScreen(navController) }
        composable(Destination.Registration.route) { RegistrationScreen(navController) }
        composable(Destination.Verification.route) { VerificationScreen() }
        composable(Destination.Login.route) { LoginScreen(navController) }
        composable(Destination.ForgotPassword.route) { ForgetPasswordScreen(navController) }
        composable(Destination.Home.route) { HomeScreen(
            "Abla", notificationCount = 2, devices = listOf(
                DeviceInfo(1,"Monitor","Oued Smar","21 JAN, 12:30",DeviceStatus.DISCONNECTED),
                DeviceInfo(2,"Monitor","Oued Smar","21 JAN, 12:30",DeviceStatus.CONNECTED),
                DeviceInfo(3,"Monitor","Oued Smar","21 JAN, 12:30",DeviceStatus.CONNECTED),
                DeviceInfo(4,"Monitor","Oued Smar","21 JAN, 12:30",DeviceStatus.DOWN),
            ),onUserSearch = {},onMoreInfoClick = {},onNotificationClick = {},onMenuClick = {}
        ) }
        composable(Destination.Tasks.route) {
            TasksScreen(
                notificationCount = 2,
                onMenuClick = {},
                onNotificationClick = {},
                onTaskSearch = {},
                tasks = listOf(
                    TaskInfo(1,"Monitor",1,"Battery","Down",100,"Oued Smar","21 JAN, 12:30"),
                    TaskInfo(99,"Monitor",2,"Monitor","Down",100,"Oued Smar","21 JAN, 12:30"),
                    TaskInfo(3,"Monitor",3,"Monitor","Down",100,"Oued Smar","21 JAN, 12:30"),
                ),
                navController = navController
            )
        }

        // Updated routes with parameters
        composable(
            route = "${Destination.TaskDetails.route}/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            TaskDetailsScreen(taskId = taskId)
        }

        composable(
            route = "${Destination.DeviceDetails.route}/{deviceId}",
            arguments = listOf(navArgument("deviceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getInt("deviceId") ?: 0
            DeviceDetailsScreen(deviceId = deviceId)
        }

        // Keep the original routes for backward compatibility
        composable(Destination.TaskDetails.route) { TaskDetailsScreen() }
        composable(Destination.DeviceDetails.route) { DeviceDetailsScreen() }
    }
}