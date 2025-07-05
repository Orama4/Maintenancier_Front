package com.example.clientmaintenancier.navigation

import AccountMainScreen
import ChangePasswordScreen
import ContactSupportScreen
import FaqScreen
import LogoutDeleteScreen
import ProfileInfoScreen
import PushNotificationsScreen
import ReportBugScreen
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clientmaintenancier.api.UserProfile
import com.example.clientmaintenancier.repositories.AuthRepository
import com.example.clientmaintenancier.repositories.DeviceRepository
import com.example.clientmaintenancier.repositories.TaskRepository
import com.example.clientmaintenancier.ui.screens.DeviceDetailsScreen
import com.example.clientmaintenancier.ui.screens.ForgetPasswordScreen
import com.example.clientmaintenancier.ui.screens.HomeScreen
import com.example.clientmaintenancier.ui.screens.InterventionHistoryScreen
import com.example.clientmaintenancier.ui.screens.LoginScreen
import com.example.clientmaintenancier.ui.screens.OnboardingScreen
import com.example.clientmaintenancier.ui.screens.RegistrationScreen
import com.example.clientmaintenancier.ui.screens.ResetPasswordScreen
import com.example.clientmaintenancier.ui.screens.TaskDetailsScreen
import com.example.clientmaintenancier.ui.screens.TasksScreen
import com.example.clientmaintenancier.ui.screens.VerificationOtp
import com.example.clientmaintenancier.ui.screens.VerificationScreen
import com.example.clientmaintenancier.viewmodels.AuthViewModel
import com.example.clientmaintenancier.viewmodels.AuthViewModelFactory
import com.example.clientmaintenancier.viewmodels.DeviceViewModel
import com.example.clientmaintenancier.viewmodels.DeviceViewModelFactory
import com.example.clientmaintenancier.viewmodels.TaskViewModel
import com.example.clientmaintenancier.viewmodels.TaskViewModelFactory
import getSampleFaqs


sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Registration : Screen("registration")

    object ForgotPassword : Screen("ForgotPassword")
    object Verification : Screen("verification")
    object VerificationOtp : Screen("VerificationOtp")


    object OnBoarding : Screen("onboarding")
    object Home : Screen("home")
    object Tasks : Screen("tasks")
    object TaskDetails : Screen("TaskDetails")
    object DeviceDetails : Screen("DeviceDetails")
    object InterventionHistory : Screen("InterventionHistory")

    object Main_account : Screen("main_account")

    object Profile_info : Screen("profile_info")
    object Change_password : Screen("change_password")
    object Push_notifications : Screen("push_notifications")
    object Faq : Screen("faq")
    object Contact_support : Screen("contact_support")
    object Report_bug : Screen("report_bug")
    object Logout_delete : Screen("logout_delete")
    object ResetPassword : Screen("ResetPassword")


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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationScreen(
    navController: NavHostController,
    deviceRepository: DeviceRepository,
    authRepository: AuthRepository,
    taskRepository: TaskRepository,

    ) {
    val taskViewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(taskRepository)
    )

    val deviceViewModel: DeviceViewModel = viewModel(
        factory = DeviceViewModelFactory(deviceRepository)
    )
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(authRepository,LocalContext.current)
    )
    NavHost(navController, startDestination = Screen.OnBoarding.route) {
        composable(Screen.Home.route) {
            val viewModel: DeviceViewModel = viewModel(
                factory = DeviceViewModelFactory(deviceRepository)
            )
            val userId = authViewModel.getUserInfo()?.maintainerId ?: -1
            Log.d("am in my way to go to devices page ","am in my way to go to devices page:$userId")
           val name = authViewModel.getUserProfile()?.name?: ""
            HomeScreen(
                maintainerId = userId,
                viewModel = viewModel,
                onUserSearch = {},
                onNotificationClick = {},
                onMenuClick = {},
                notificationCount = 2,
                username = name,
                navController = navController
            )
        }

        composable(Screen.Tasks.route) {
            val userId = authViewModel.getUserInfo()?.maintainerId ?: -1
            TasksScreen(
                maintainerId = userId,
                viewModel = taskViewModel,
                notificationCount = 3,
                onMenuClick = { },
                onNotificationClick = { },
                onTaskSearch = {},
                navController = navController
            )
        }

        composable(
            route = "${Screen.TaskDetails.route}/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            TaskDetailsScreen(taskId = taskId,navController = navController,viewModel = taskViewModel)
        }

        composable(
            route = "${Screen.DeviceDetails.route}/{deviceId}",
            arguments = listOf(navArgument("deviceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getInt("deviceId") ?: 0
            DeviceDetailsScreen(deviceId = deviceId,navController = navController,viewModel=deviceViewModel)
        }

        composable(
            route = "${Screen.InterventionHistory.route}/{deviceId}",
            arguments = listOf(navArgument("deviceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getInt("deviceId") ?: 0
            InterventionHistoryScreen(deviceId = deviceId,viewModel=taskViewModel,navController,onMenuClick = { })
        }
        composable(Screen.OnBoarding.route) { OnboardingScreen(navController,authViewModel) }
        composable(Screen.Registration.route) {

            RegistrationScreen(navController = navController, viewModel = authViewModel)
        }
        composable(Screen.Verification.route + "/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerificationScreen(navController,viewModel = authViewModel, email = email)
        }
        composable(Screen.VerificationOtp.route + "/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerificationOtp(navController,viewModel = authViewModel, email = email)
        }
        composable(Screen.ResetPassword.route + "/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ResetPasswordScreen(navController, authViewModel, email = email)
        }
        composable(Screen.Login.route) { LoginScreen(navController,authViewModel) }
        composable(Screen.ForgotPassword.route) { ForgetPasswordScreen(navController,authViewModel) }
        composable(Screen.Main_account.route) {
            AccountMainScreen(navController = navController)
        }
        composable(Screen.Profile_info.route) {
            ProfileInfoScreen(navController = navController, authViewModel = authViewModel, onSave = { updatedProfile ->
                println("Saving profile: $updatedProfile")
                authViewModel.saveUserProfile(updatedProfile)
            })}
        composable(Screen.Change_password.route) {
            ChangePasswordScreen(navController = navController,authViewModel = authViewModel)
        }


        composable(Screen.Push_notifications.route) {
            val pushNotificationsEnabled = rememberSaveable { mutableStateOf(true) }

            PushNotificationsScreen(navController = navController, initialState = pushNotificationsEnabled.value, onToggle = { enabled ->
                println("Notifications toggled: $enabled")
                pushNotificationsEnabled.value = enabled })

        }
        composable(Screen.Faq.route) {
            val faqs = remember { getSampleFaqs() }
            FaqScreen(navController = navController, faqs = faqs)
        }
        composable(Screen.Contact_support.route) {
            val contactEmail = "support@yourapp.com"
            val contactPhone = "+1-800-SUPPORT"
            ContactSupportScreen(navController = navController, email = contactEmail, phone = contactPhone)

        }
        composable(Screen.Report_bug.route) {
            ReportBugScreen(navController = navController, onSubmit = { report ->
                println("Submitting bug report: $report")
                // Simulate success
                val success = true
                if (success) navController.navigateUp()
                success
            })
        }
        composable(Screen.Logout_delete.route) {
            LogoutDeleteScreen(navController = navController,authViewModel = authViewModel )

        }

    }
}

//@Composable
//fun NavigationScreen(navController: NavHostController= rememberNavController(),deviceViewModel: DeviceViewModel) {
//    val context = LocalContext.current
//    NavHost(navController = navController, startDestination = Screen.Tasks.route) {
//        composable(Screen.OnBoarding.route) { OnboardingScreen(navController) }
//        composable(Screen.Registration.route) { RegistrationScreen(navController) }
//        composable(Screen.Verification.route) { VerificationScreen() }
//        composable(Screen.Login.route) { LoginScreen(navController) }
//        composable(Screen.ForgotPassword.route) { ForgetPasswordScreen(navController) }
//        composable(Screen.Home.route) { HomeScreen(viewModel = deviceViewModel,onUserSearch = {},
//            onMoreInfoClick = {},onNotificationClick = {},onMenuClick = {}, notificationCount = 2,
//            "Abla"
//        ) }
//        composable(Screen.Tasks.route) {
//            TasksScreen(
//                notificationCount = 2,
//                onMenuClick = {},
//                onNotificationClick = {},
//                onTaskSearch = {},
//                tasks = listOf(
//                    TaskInfo(1,"Monitor",1,"Battery","Down",100,"Oued Smar","21 JAN, 12:30"),
//                    TaskInfo(99,"Monitor",2,"Monitor","Down",100,"Oued Smar","21 JAN, 12:30"),
//                    TaskInfo(3,"Monitor",3,"Monitor","Down",100,"Oued Smar","21 JAN, 12:30"),
//                ),
//                navController = navController
//            )
//        }
//
//
//        // Keep the original routes for backward compatibility
////        composable(Screen.TaskDetails.route) { TaskDetailsScreen() }
////        composable(Screen.DeviceDetails.route) { DeviceDetailsScreen() }
//        composable(
//            route = "${Screen.TaskDetails.route}/{taskId}",
//            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
//        ) { backStackEntry ->
//            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
//            TaskDetailsScreen(taskId = taskId,navController = navController)
//        }
//
//        composable(
//            route = "${Screen.DeviceDetails.route}/{deviceId}",
//            arguments = listOf(navArgument("deviceId") { type = NavType.IntType })
//        ) { backStackEntry ->
//            val deviceId = backStackEntry.arguments?.getInt("deviceId") ?: 0
//            DeviceDetailsScreen(deviceId = deviceId,navController = navController)
//        }
//
//
//
//
//        composable(Screen.Main_account.route) {
//            AccountMainScreen(navController = navController)
//        }
//        composable(Screen.Profile_info.route) {
//            val userProfile = remember {
//                mutableStateOf(UserProfile("Aymen Bouslama", "aymen.b@example.com", "+1 123-456-7890"))
//            }
//            ProfileInfoScreen(navController = navController, profile = userProfile.value,
//                onSave = { updatedProfile ->
//                    println("Saving profile: $updatedProfile")
//                    userProfile.value = updatedProfile
//                    navController.navigateUp()
//                }
//            )}
//        composable(Screen.Change_password.route) {
//            ChangePasswordScreen(navController = navController, onChangePassword = { current, new ->
//                println("Changing password: Current=$current, New=$new")
//                val success = true
//                if (success) navController.navigateUp()
//                success
//            }) }
//        composable(Screen.Push_notifications.route) {
//            val pushNotificationsEnabled = rememberSaveable { mutableStateOf(true) }
//
//            PushNotificationsScreen(navController = navController, initialState = pushNotificationsEnabled.value, onToggle = { enabled ->
//                println("Notifications toggled: $enabled")
//                pushNotificationsEnabled.value = enabled })
//
//        }
//        composable(Screen.Faq.route) {
//            val faqs = remember { getSampleFaqs() }
//            FaqScreen(navController = navController, faqs = faqs)
//        }
//        composable(Screen.Contact_support.route) {
//            val contactEmail = "support@yourapp.com"
//            val contactPhone = "+1-800-SUPPORT"
//            ContactSupportScreen(navController = navController, email = contactEmail, phone = contactPhone)
//
//        }
//        composable(Screen.Report_bug.route) {
//            ReportBugScreen(navController = navController, onSubmit = { report ->
//                println("Submitting bug report: $report")
//                // Simulate success
//                val success = true
//                if (success) navController.navigateUp()
//                success
//            })
//        }
//        composable(Screen.Logout_delete.route) {
//            LogoutDeleteScreen(navController = navController, onLogout = {
//                println("Logging out...")
//            }, onDeleteAccount = {println("Deleting account...")})
//
//        }
//    }
//}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
