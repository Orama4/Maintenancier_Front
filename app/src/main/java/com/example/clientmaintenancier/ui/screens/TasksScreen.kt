package com.example.clientmaintenancier.ui.screens
import android.icu.text.StringSearch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.clientmaintenancier.R
import com.example.clientmaintenancier.navigation.Screen
import com.example.clientmaintenancier.ui.components.*
import com.example.clientmaintenancier.ui.theme.AppColors
import com.example.clientmaintenancier.ui.theme.PlusJakartaSans

data class TaskInfo(
    val id: Int,
    val problem: String,
    val deviceId : Int,
    val deviceName: String,
    val status: String,
    val batteryLevel: Int,
    val location: String,
    val maintenanceTime: String
)

// Modified to use NavController and handle navigation
@Composable
fun TasksScreen(
    notificationCount: Int,
    onMenuClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onTaskSearch: (String) -> Unit,
    tasks: List<TaskInfo>,
    navController: NavController, // Added navigation controller parameter
    onDeviceDetailsClick: (deviceId: Int) -> Unit = { deviceId ->
        // Navigate to DeviceDetails with deviceId
        navController.navigate("${Screen.DeviceDetails.route}/$deviceId")
        {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    },
    onTaskDetailsClick: (taskId: Int) -> Unit = { taskId ->
        // Navigate to TaskDetails with taskId
        navController.navigate("${Screen.TaskDetails.route}/$taskId")
        {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
) {
    var searchText by remember { mutableStateOf("") }
    val primaryOrange = AppColors.primary
    val greenConnected = AppColors.green
    val redDown = AppColors.red
    val orangeDisconnected = AppColors.orange
    val lightBeige = Color(0xFFFFF0D9)
    val grayBackground = Color(0xFFF5F5F5)
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp) ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Menu Button
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(grayBackground)
                        .clickable(onClick = onMenuClick),
                    contentAlignment = Alignment.Center,

                    ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = "Menu",
                        modifier = Modifier.size(20.dp),
                    )

                }

                // Notification Icon with Badge
                Box {
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                            .background(Color.Black)
                            .clickable(onClick = onNotificationClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White
                        )
                    }

                    // Badge
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(primaryOrange)
                            .align(Alignment.TopEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = notificationCount.toString(),
                            color = Color.White,
                            fontSize = 12.sp,
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                // Greeting
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {

                    Text(
                        text = "List of tasks",
                        fontFamily = PlusJakartaSans,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Search Bar
                TextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        onTaskSearch(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(grayBackground),
                    placeholder = { Text("Search task", fontFamily = PlusJakartaSans) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray.copy(0.6f)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = grayBackground,
                        unfocusedContainerColor = grayBackground,
                        disabledContainerColor = grayBackground,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item{
                        // Status Cards Grid
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Total Card
                            StatusCard(
                                modifier = Modifier.weight(1f),
                                title = "Total tasks",
                                count = "${tasks.size} tasks",
                                iconContent = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_grid),
                                        contentDescription = "Total",
                                        tint = Color.Black
                                    )
                                }
                            )

                            // Down Card
                            StatusCard(
                                modifier = Modifier.weight(1f),
                                title = "Active alers",
                                count = "1 alert",
                                iconContent = {
                                    Icon(
                                        imageVector = Icons.Default.TrendingDown,
                                        contentDescription = "Down",
                                        tint = redDown
                                    )
                                },
                                titleColor = redDown
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }


                    // Device List
                    if (tasks.isNotEmpty()) {
                        item {
                            DeviceSection2(
                                sectionTitle = "Down",
                                titleColor = redDown,
                                batteryLevel = 50,
                                tasks = tasks,
                                onTaskDetailsClick = onTaskDetailsClick,
                                onDeviceDetailsClick = onDeviceDetailsClick,
                                onStartMaintenanceClick = onDeviceDetailsClick,
                                onMoreInfoClick = onDeviceDetailsClick
                            )
                        }
                    }
                    // Add extra padding at the bottom to ensure content isn't hidden behind bottom nav
                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }
    }
}