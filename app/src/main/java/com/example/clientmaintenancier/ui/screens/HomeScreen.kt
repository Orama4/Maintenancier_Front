package com.example.clientmaintenancier.ui.screens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Composable
fun HomeScreen() {
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
                        .background(grayBackground),
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
                            .background(Color.Black),
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
                            text = "2",
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
                        text = "Hey Abla, ",
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
                    value = "",
                    onValueChange = {},
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
                        count = "4 devices",
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
                        count = "1 device",
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
                        count = "2 devices",
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
                        count = "1 device",
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

                // Device List
                // Connected Devices
                DeviceSection(
                    sectionTitle = "connected",
                    titleColor = greenConnected,
                    batteryLevel = 50
                )

                // Disconnected Devices
                DeviceSection(
                    sectionTitle = "disconnected",
                    titleColor = orangeDisconnected,
                    batteryLevel = 50
                )

                // Down Devices
                DeviceSection(
                    sectionTitle = "Down",
                    titleColor = redDown,
                    batteryLevel = 50
                )

                // Add extra padding at the bottom to ensure content isn't hidden behind bottom nav
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

