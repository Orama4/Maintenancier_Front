package com.example.clientmaintenancier.ui.screens
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.clientmaintenancier.entities.task
import com.example.clientmaintenancier.navigation.Screen
import com.example.clientmaintenancier.ui.components.*
import com.example.clientmaintenancier.ui.theme.AppColors
import com.example.clientmaintenancier.ui.theme.PlusJakartaSans
import com.example.clientmaintenancier.viewmodels.TaskViewModel
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InterventionHistoryScreen(
    deviceId: Int,
    viewModel: TaskViewModel,
    navController: NavController,
    onMenuClick: () -> Unit,
) {
    val grayBackground = Color(0xFFF5F5F5)
    val lightBeige = Color(0xFFFFF0D9)
    val scrollState = rememberScrollState()

    val interventions by viewModel.interventionsByDevice.collectAsState()
    val isLoading by viewModel.loading3.collectAsState()
    val error by viewModel.error3.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.clearInterventionsData()
        viewModel.fetchInterventionsByDevice(deviceId)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp) ) {
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

            interventions != null -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Top Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = "Back",
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable { navController.navigateUp() }
                            )
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
                                text = "List of interventions",
                                fontFamily = PlusJakartaSans,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Status Cards
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Total Card
                            StatusCard(
                                modifier = Modifier.weight(1f),
                                title = "Total interventions",
                                count = "${interventions.size} interventions",
                                iconContent = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_grid),
                                        contentDescription = "Total",
                                        tint = Color.Black
                                    )
                                },
                                backgroundColor = lightBeige

                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Interventions List
                        if (interventions.isNotEmpty()) {

                            interventions.forEach { intervention ->
                                InterventionCard(intervention = intervention)
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No interventions found for this device",
                                    color = Color.Gray,
                                    fontFamily = PlusJakartaSans,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InterventionCard(intervention: task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header with type and status
            Text(
                text = "Interevention #${intervention.id}",
                fontFamily = PlusJakartaSans,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Type: ${intervention.type}",
                    fontFamily = PlusJakartaSans,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                StatusChip(status = intervention.status)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Device info
            Text(
                text = "Device: ${intervention.device.nom}",
                fontFamily = PlusJakartaSans,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            intervention.device.email?.let {
                Text(
                    text = "User: $it",
                    fontFamily = PlusJakartaSans,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Intervention details
            intervention.planDate?.let { dateTimeString ->
                val parsedDate = OffsetDateTime.parse(dateTimeString)
                val formattedDate = parsedDate.toLocalDate().toString()
                Text(
                    text = "Planned Date: $formattedDate",
                    fontFamily = PlusJakartaSans,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            intervention.priority?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Priority: " + intervention.priority,
                        fontFamily = PlusJakartaSans,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            Text(
                text = "Remote: ${if (intervention.isRemote == true) "Yes" else "No"}",
                fontFamily = PlusJakartaSans,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )

            intervention.description?.let {
                if (it.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Description:",
                        fontFamily = PlusJakartaSans,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = it,
                        fontFamily = PlusJakartaSans,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: String) {
    Box(
        modifier = Modifier
            .background(
                color = AppColors.green.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.capitalize(),
            color = AppColors.green,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = PlusJakartaSans
        )
    }
}

@Composable
fun PriorityChip(priority: String) {
    val backgroundColor = when (priority.lowercase()) {
        "high" -> AppColors.red.copy(alpha = 0.2f)
        "medium" -> AppColors.orange.copy(alpha = 0.2f)
        "low" -> AppColors.green.copy(alpha = 0.2f)
        else -> Color.Gray.copy(alpha = 0.2f)
    }

    val textColor = when (priority.lowercase()) {
        "high" -> AppColors.red
        "medium" -> AppColors.orange
        "low" -> AppColors.green
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = priority.capitalize(),
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = PlusJakartaSans
        )
    }
}