import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.clientmaintenancier.R
import com.example.clientmaintenancier.api.UpdateProfileRequest
import com.example.clientmaintenancier.api.UserProfile
import com.example.clientmaintenancier.navigation.Screen
import com.example.clientmaintenancier.ui.theme.AppColors
import com.example.clientmaintenancier.ui.theme.PlusJakartaSans
import com.example.clientmaintenancier.viewmodels.AuthViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileInfoScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    onSave: (UserProfile) -> Unit
) {
    var profile by remember { mutableStateOf<UserProfile?>(null) }

    LaunchedEffect(Unit) {
        profile = authViewModel.getUserProfile()
    }

    profile?.let { userProfile ->
        var name by remember { mutableStateOf(userProfile.name) }
        var email by remember { mutableStateOf(userProfile.email) }
        var phone by remember { mutableStateOf(userProfile.phone) }
        var adress by remember { mutableStateOf(userProfile.adress) }

        val hasChanges = name != userProfile.name || email != userProfile.email || phone != userProfile.phone || adress != userProfile.adress

        Scaffold(
            containerColor = Color.White
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 10.dp) // More vertical padding
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.Start // Align to the left
                ) {
                    Box(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Screen.Main_account.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.d_back),
                            contentDescription = null,
                        )
                    }
                    Text(
                        text = "Profile information",
                        modifier = Modifier.padding(8.dp),
                        color = AppColors.darkBlue,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = PlusJakartaSans,
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                ModernTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Full Name",
                    leadingIcon = Icons.Outlined.Person // Add icons
                )
                Spacer(modifier = Modifier.height(16.dp))
                ModernTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email Address",
                    leadingIcon = Icons.Outlined.Email // Add icons
                )
                Spacer(modifier = Modifier.height(16.dp))
                ModernTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = "Phone Number",
                    leadingIcon = Icons.Outlined.Phone // Add icons
                )
                Spacer(modifier = Modifier.height(32.dp))
                ModernTextField(
                    value = adress,
                    onValueChange = { adress = it },
                    label = "Adress",
                    leadingIcon = Icons.Outlined.Map // Add icons
                )
                Spacer(modifier = Modifier.height(32.dp))
                ModernButton(
                    text = "Save Changes",
                    onClick = {
                        val token = authViewModel.getToken()
                        if (token != null) {
                            val request = UpdateProfileRequest(
                                userId = authViewModel.getUserInfo()?.id ?: -1,
                                firstname = name.split(" ").firstOrNull(),
                                lastname = name.split(" ").getOrNull(1),
                                phonenumber = phone,
                                address = adress
                            )
                            authViewModel.updateProfile(token, request)
                            onSave(UserProfile(name, email, phone, adress))
                            navController.navigate(Screen.Main_account.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    enabled = hasChanges
                )
            }
        }
    } ?: run {
        // Handle the case where the profile is not available
        Text(text = "Loading profile...")
    }
}