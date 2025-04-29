package com.example.clientmaintenancier.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientmaintenancier.R
import com.example.clientmaintenancier.entities.device
import com.example.clientmaintenancier.entities.task
import com.example.clientmaintenancier.ui.theme.AppColors
import com.example.clientmaintenancier.ui.theme.PlusJakartaSans

@Composable
fun StatusCard(
    modifier: Modifier = Modifier,
    title: String,
    count: String,
    iconContent: @Composable () -> Unit,
    backgroundColor: Color = Color.White,
    titleColor: Color = Color.Black
) {
    Card(
        modifier = modifier

            .height(130.dp)

            .border(BorderStroke(1.dp, Color.Gray.copy(0.2f)), shape = RoundedCornerShape(16.dp)) // Apply border with shape

            .clip(RoundedCornerShape(16.dp))
        ,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),


        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center) {
                iconContent()
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    color = titleColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = count,
                    fontFamily = PlusJakartaSans,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}



@Composable
fun InfoButton(deviceId: Int,onMoreInfoClick: (deviceId: Int) -> Unit, text :String) {
    OutlinedButton(
        onClick = { onMoreInfoClick(deviceId) },
        modifier = Modifier
            .fillMaxWidth()
            //.padding( vertical = 16.dp)
        ,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFFFF8C00)),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = AppColors.primary
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.primary

        )
    }
}

@Composable
fun HistoryButton(deviceId: Int,onHistoryClick: (deviceId: Int) -> Unit) {
    OutlinedButton(
        onClick = { onHistoryClick(deviceId) },
        modifier = Modifier
            .fillMaxWidth()
            //.padding( vertical = 16.dp)
        ,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFFFF8C00)),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = AppColors.primary
        )
    ) {
        Text(
            text = "History",
            fontSize = 16.sp,
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.primary

        )
    }
}


@Composable
fun DeviceCard(device: device) {
    val color = when (device.status) {
        "Actif" -> Color(0xFF4CAF50)        // Green
        "Banne" -> Color(0xFFFF9800)         // Orange
        "Hors_service" -> Color.Red
        "Defectueux" -> Color(0xFFF44336)    // Darker Red
        "En_maintenance" -> Color(0xFF2196F3) // Blue
        else -> Color.Gray
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Device Image/Icon
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFB0BEC5))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Device Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = device.nom,
                    fontSize = 16.sp,
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Location Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Wifi,
                        contentDescription = "Location",
                        modifier = Modifier.size(16.dp),
                        tint = color
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = device.status,
                        fontSize = 14.sp,
                        fontFamily = PlusJakartaSans,
                        color = color

                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = device.macAdresse,
                        fontSize = 12.sp,
                        fontFamily = PlusJakartaSans,
                        color = Color.Gray
                    )
                }
            }
        }


    }
}


@Composable
fun DeviceCard2(task: task, onCardClick: () -> Unit) {
    val textColor = when (task.type) {
        "preventive" -> AppColors.primary // Green color
        "curative" -> AppColors.red // Red color
        else -> Color.Black // Default color
    }

    val color = when (task.device.status) {
        "Actif" -> Color(0xFF4CAF50)        // Green
        "Banne" -> Color(0xFFFF9800)         // Orange
        "Hors_service" -> Color.Red
        "Defectueux" -> Color(0xFFF44336)    // Darker Red
        "En_maintenance" -> Color(0xFF2196F3) // Blue
        else -> Color.Gray
    }

    val color2 = when (task.status) {
        "en_panne" -> Color(0xFFF44336)
        "complete" -> Color(0xFF4CAF50)
        "en_progres" -> Color(0xFFFF9800)
        else -> Color.Gray
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable{ onCardClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Device Image/Icon
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFB0BEC5))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Task Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.device.nom ?: "Unknown Device",
                    fontSize = 16.sp,
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold
                )

                Row (Modifier.fillMaxWidth().padding(end = 16.dp),horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Wifi,
                            contentDescription = "Location",
                            modifier = Modifier.size(16.dp),
                            tint = color
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = task.device.status ?: "Unknown Status",
                            fontSize = 14.sp,
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Medium,
                            color = color
                        )
                    }
                    Text(
                        text = when (task.status) {
                            "en_panne" -> "Not taken"
                            "complete" -> "Completed"
                            "en_progres" -> "In progress"
                            else -> "Not available"
                        },
                        fontFamily = PlusJakartaSans,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = color2
                    )
                }


                Spacer(modifier = Modifier.height(4.dp))
            }
        }


    }
}

@Composable
fun DeviceSection(
    sectionTitle: String,
    titleColor: Color,
    devices: List<device>,
    onMoreInfoClick: (deviceId: Int) -> Unit,
    onHistoryClick: (deviceId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Column {
            Text(
                text = "List of devices",
                //color = AppColors.darkBlue,
                fontFamily = PlusJakartaSans,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Device Card
        devices.forEach { device ->
            DeviceCard(device)
            InfoButton(
                deviceId = device.id,
                onMoreInfoClick = onMoreInfoClick,
                "More Info"
            )
            HistoryButton(deviceId = device.id,onHistoryClick = onHistoryClick)
        }

        Spacer(modifier = Modifier.height(12.dp))
        // Ligne de séparation
        Divider(
            modifier = Modifier
                .fillMaxWidth()
            ,
            color = Color(0xFFE0E0E0),
            thickness = 1.dp
        )
    }
}


@Composable
fun DeviceSection2(
    tasks: List<task>,
    onTaskDetailsClick: (taskId: Int) -> Unit,
    onDeviceDetailsClick: (deviceId: Int) -> Unit,
    onStartMaintenanceClick: (taskId: Int) -> Unit,
    onMoreInfoClick: (deviceId: Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {

        Column {
            Text(
                text = "List of tasks",
                fontFamily = PlusJakartaSans,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // task Card
        tasks.forEach { task ->
            DeviceCard2(
                task,
                onCardClick = { onTaskDetailsClick(task.id)})
            //buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                InfoButton(
                    deviceId = task.deviceId,
                    onMoreInfoClick = onMoreInfoClick,
                    "device details"
                )
            }

        }


        Spacer(modifier = Modifier.height(12.dp))
        // Ligne de séparation
        Divider(
            modifier = Modifier
                .fillMaxWidth()
            ,
            color = Color(0xFFE0E0E0),
            thickness = 1.dp
        )
    }
}
