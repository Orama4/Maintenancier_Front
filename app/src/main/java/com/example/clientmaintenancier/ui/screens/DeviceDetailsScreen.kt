package com.example.clientmaintenancier.ui.screens

import android.bluetooth.BluetoothClass.Device
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientmaintenancier.ui.theme.AppColors
import com.example.clientmaintenancier.ui.theme.PlusJakartaSans

@Composable
fun DeviceDetailsScreen(deviceId: Int = 0) {
    // In a real app, you would fetch task details based on taskId
    // For now, we'll use a hardcoded task for demonstration
    val task = if (deviceId > 0) {
        // This simulates fetching a task by ID
        TaskInfo(
            id = deviceId,
            problem = "Battery Issue",
            deviceId = deviceId,
            deviceName = "Monitor",
            status = "Down",
            batteryLevel = 25,
            location = "Oued Smar",
            maintenanceTime = "21 JAN, 12:30"
        )
    } else {
        // Fallback for when no ID is provided
        TaskInfo(
            id = 1,
            problem = "Default Task",
            deviceId = 1,
            deviceName = "Unknown Device",
            status = "Unknown",
            batteryLevel = 50,
            location = "Unknown Location",
            maintenanceTime = "Unknown Time"
        )
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Top Bar with Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Handle back navigation */ }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Task Details",
                fontFamily = PlusJakartaSans,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            // Task Status Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when(task.status) {
                        "Down" -> AppColors.red.copy(alpha = 0.1f)
                        "Connected" -> AppColors.green.copy(alpha = 0.1f)
                        "Disconnected" -> AppColors.orange.copy(alpha = 0.1f)
                        else -> Color.Gray.copy(alpha = 0.1f)
                    }
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = when(task.status) {
                                    "Down" -> AppColors.red
                                    "Connected" -> AppColors.green
                                    "Disconnected" -> AppColors.orange
                                    else -> Color.Gray
                                },
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Status",
                            tint = Color.White
                        )
                    }

                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            text = task.status,
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = when(task.status) {
                                "Down" -> AppColors.red
                                "Connected" -> AppColors.green
                                "Disconnected" -> AppColors.orange
                                else -> Color.Gray
                            }
                        )
                        Text(
                            text = "Status",
                            fontFamily = PlusJakartaSans,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Task Information Section
            SectionTitle(title = "Task Information")

            InfoItem(label = "Task ID", value = "#${task.id}")
            InfoItem(label = "Problem", value = task.problem)
            InfoItem(label = "Device", value = task.deviceName)
            InfoItem(label = "Device ID", value = "#${task.deviceId}")
            InfoItem(label = "Location", value = task.location)
            InfoItem(label = "Scheduled Time", value = task.maintenanceTime)
            InfoItem(label = "Battery Level", value = "${task.batteryLevel}%")

            // Spacer to ensure content isn't hidden behind bottom elements
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Bottom Button
        Button(
            onClick = { /* Handle start maintenance logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.primary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Start Maintenance",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontFamily = PlusJakartaSans,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
private fun InfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontFamily = PlusJakartaSans,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontFamily = PlusJakartaSans,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
    Divider(
        modifier = Modifier.padding(top = 8.dp),
        color = Color.LightGray.copy(alpha = 0.5f)
    )
}