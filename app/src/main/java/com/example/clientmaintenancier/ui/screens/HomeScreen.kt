package com.example.clientmaintenancier.ui.screens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.clientmaintenancier.R
import com.example.clientmaintenancier.ui.components.*
import com.example.clientmaintenancier.ui.theme.AppColors
import com.example.clientmaintenancier.ui.theme.PlusJakartaSans
import com.example.clientmaintenancier.viewmodels.DeviceViewModel

enum class DeviceStatus(val displayName: String) {
    CONNECTED("Connected"),
    DISCONNECTED("Disconnected"),
    DOWN("Down")
}
data class DeviceInfo(
    val id: Int,
    val name: String,
    val location: String,
    val timestamp: String,
    val status: DeviceStatus,
    val imageUrl: String? = null
)

val primaryOrange = AppColors.primary
val greenConnected = AppColors.green
val redDown = AppColors.red
val orangeDisconnected = AppColors.orange
val lightBeige = Color(0xFFFFF0D9)
val grayBackground = Color(0xFFF5F5F5)


@Composable
fun HomeScreen(
    viewModel: DeviceViewModel,
    onUserSearch: (String) -> Unit,
    onMoreInfoClick: (deviceId: Int) -> Unit,
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    notificationCount: Int,
    username: String = "Abla"
) {
    val stats by viewModel.stats.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchDeviceStats()
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when {
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

            stats != null -> {
                val total = stats?.totalDevices ?: 0
                val connected = stats?.connectedDevices ?: 0
                val disconnected = stats?.disconnectedDevices ?: 0
                val down = stats?.enPanneDevices ?: 0

                Column {
                    HeaderSection(
                        username = username,
                        notificationCount = notificationCount,
                        onNotificationClick = onNotificationClick,
                        onMenuClick = onMenuClick
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        StatusCard(
                            modifier = Modifier.weight(1f),
                            title = "Total",
                            count = "$total devices",
                            iconContent = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_grid),
                                    contentDescription = "Total",
                                    tint = Color.Black
                                )
                            },
                            backgroundColor = lightBeige
                        )

                        StatusCard(
                            modifier = Modifier.weight(1f),
                            title = "Down",
                            count = "$down devices",
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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        StatusCard(
                            modifier = Modifier.weight(1f),
                            title = "Connected",
                            count = "$connected devices",
                            iconContent = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_conn),
                                    contentDescription = "Connected",
                                    tint = greenConnected
                                )
                            },
                            titleColor = greenConnected
                        )

                        StatusCard(
                            modifier = Modifier.weight(1f),
                            title = "Disconnected",
                            count = "$disconnected devices",
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

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Most Reported Devices",
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        }
    }
}

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


/*@Composable
fun HomeScreen(
    username: String = "Abla",
    devices: List<DeviceInfo>,
    onUserSearch: (String) -> Unit,
    onMoreInfoClick: (deviceId: Int) -> Unit,
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
    val deviceByStatus = devices.groupBy { it.status }
    val connectedDevices = deviceByStatus[DeviceStatus.CONNECTED] ?: emptyList()
    val disconnectedDevices = deviceByStatus[DeviceStatus.DISCONNECTED] ?: emptyList()
    val downDevices = deviceByStatus[DeviceStatus.DOWN] ?: emptyList()

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
                        count = "${devices.size.toString()} devices",
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
                        count = "${downDevices.size} device",
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
                        count = "${connectedDevices.size} devices",
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
                        count = "${disconnectedDevices.size} device",
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





                    if (connectedDevices.isNotEmpty()) {
                        item {
                            DeviceSection(
                                sectionTitle = "connected",
                                titleColor = greenConnected,
                                batteryLevel = 50,
                                devices = connectedDevices,
                                onMoreInfoClick = onMoreInfoClick
                            )
                        }
                    }

                    if (disconnectedDevices.isNotEmpty()) {
                        item {
                            DeviceSection(
                                sectionTitle = "disconnected",
                                titleColor = orangeDisconnected,
                                batteryLevel = 50,
                                devices = disconnectedDevices,
                                onMoreInfoClick = onMoreInfoClick
                            )
                        }
                    }

                    if (downDevices.isNotEmpty()) {
                        item {
                            DeviceSection(
                                sectionTitle = "Down",
                                titleColor = redDown,
                                batteryLevel = 50,
                                devices = downDevices,
                                onMoreInfoClick = onMoreInfoClick
                            )
                        }
                    }

                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }
    }
}*/

