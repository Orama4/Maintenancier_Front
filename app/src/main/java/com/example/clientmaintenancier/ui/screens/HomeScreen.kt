package com.example.clientmaintenancier.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.clientmaintenancier.viewmodels.DeviceViewModel


@Composable
fun HeaderSection(
    username: String,
    notificationCount: Int,
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "Welcome,")
            Text(
                text = username,
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onNotificationClick) {
                BadgedBox(
                    badge = {
                        if (notificationCount > 0) {
                            Badge { Text(text = notificationCount.toString()) }
                        }
                    }
                ) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                }
            }

            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    }
}


@Composable
fun HomeScreen(
    maintainerId: Int,
    viewModel: DeviceViewModel,
    username: String = "Abla",
    onUserSearch: (String) -> Unit,
    navController: NavController,
    onMoreInfoClick: (deviceId: Int) -> Unit = { deviceId ->
        navController.navigate("${Screen.DeviceDetails.route}/$deviceId")
        {
            // Ne pas réutiliser l'état précédent - forcer la création d'un nouveau composable
            restoreState = false
            // Éviter d'empiler les écrans multiples de détails
            launchSingleTop = true
            // Option pour nettoyer la pile de navigation
            popUpTo(Screen.Home.route) {
                saveState = false
            }
        }
    },
    onHistoryClick: (deviceId: Int) -> Unit = { deviceId ->
        navController.navigate("${Screen.InterventionHistory.route}/$deviceId") {
            // Ne pas réutiliser l'état précédent - forcer la création d'un nouveau composable
            restoreState = false
            // Éviter d'empiler les écrans multiples de détails
            launchSingleTop = true
            // Option pour nettoyer la pile de navigation
            popUpTo(Screen.Home.route) {
                saveState = false
            }
        }
    },
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    notificationCount: Int
) {

    val primaryOrange = AppColors.primary
    val greenConnected = AppColors.green
    val redDown = AppColors.red
    val orangeDisconnected = AppColors.orange
    val lightBeige = Color(0xFFFFF0D9)
    val grayBackground = Color(0xFFF5F5F5)
    val scrollState = rememberScrollState()
    var searchText by remember { mutableStateOf("") }


    val deviceStats by viewModel.deviceStats.collectAsState()
    val devices by viewModel.devices.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchDeviceStats(maintainerId)
        viewModel.fetchDevicesByMaintainer(maintainerId)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp) ) {
        when{
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            error != null -> {
                Text(
                    text = error ?: "",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            deviceStats != null -> {
                val total = deviceStats?.totalDevices ?: 0
                val connected = deviceStats?.connectedDevices ?: 0
                val disconnected = deviceStats?.disconnectedDevices ?: 0
                val down = deviceStats?.downDevices ?: 0
                val maintenance = deviceStats?.inMaintenanceDevices ?: 0

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
                            contentAlignment = Alignment.Center
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
                                    .background(Color.Black).clickable(onClick = onNotificationClick),
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
                                .padding( bottom = 16.dp)
                        ) {
                            Text(
                                text = "Hey $username,",
                                fontFamily = PlusJakartaSans,
                                fontSize = 20.sp
                            )
                            Text(
                                text = "Good Afternoon!",
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
                                onUserSearch(it)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(grayBackground),
                            placeholder = { Text("Search users", fontFamily = PlusJakartaSans) },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray.copy(0.6f)) },
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
                                        .fillMaxWidth()
                                    ,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    // Total Card
                                    StatusCard(
                                        modifier = Modifier.weight(1f),
                                        title = "Total",
                                        count = "${total} devices",
                                        iconContent = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_grid),
                                                contentDescription = "Total",
                                                tint = Color.Black
                                            )
                                        },
                                        backgroundColor = lightBeige
                                    )

                                    // Down Card
                                    StatusCard(
                                        modifier = Modifier.weight(1f),
                                        title = "Down",
                                        count = "${down} device",
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

                                // Second Row of Cards
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                    ,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    // Connected Card
                                    StatusCard(
                                        modifier = Modifier.weight(1f),
                                        title = "Connected",
                                        count = "${connected} devices",
                                        iconContent = {
                                            Icon(

                                                painter = painterResource(id = R.drawable.ic_conn),
                                                contentDescription = "Connected",
                                                tint = greenConnected
                                            )
                                        },
                                        titleColor = greenConnected
                                    )

                                    // Disconnected Card
                                    StatusCard(
                                        modifier = Modifier.weight(1f),
                                        title = "Disconnected",
                                        count = "${disconnected} device",
                                        iconContent = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_dis),
                                                contentDescription = "Disconnected",
                                                tint = orangeDisconnected
                                            )
                                        },
                                        titleColor = orangeDisconnected
                                    )
                                }
                            }
                            // Device List

                            if (devices.isNotEmpty()) {
                                item {
                                    DeviceSection(
                                        sectionTitle = "",
                                        titleColor = greenConnected,
                                        devices = devices,
                                        onMoreInfoClick = onMoreInfoClick,
                                        onHistoryClick = onHistoryClick,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

