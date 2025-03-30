package com.example.clientmaintenancier.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import com.example.clientmaintenancier.ui.screens.DeviceInfo
import com.example.clientmaintenancier.ui.screens.TaskInfo
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
fun DeviceSection(
    sectionTitle: String,
    titleColor: Color,
    batteryLevel: Int,
    devices: List<DeviceInfo>,
    onMoreInfoClick: (deviceId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // Section Title with Battery
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sectionTitle,
                color = titleColor,
                fontFamily = PlusJakartaSans,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Battery Indicator
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$batteryLevel%",
                    fontSize = 14.sp,
                    fontFamily = PlusJakartaSans,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_batt),
                    contentDescription = "Battery",
                    modifier = Modifier.size(24.dp),
                    tint = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Device Card
        devices.forEach { device ->
            DeviceCard(device)
            InfoButton(onMoreInfoClick)
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
fun InfoButton(onMoreInfoClick: (deviceId: Int) -> Unit) {
    // Bouton "More infos"
    OutlinedButton(
        onClick = { onMoreInfoClick},
        modifier = Modifier
            .fillMaxWidth()
            .padding( vertical = 16.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFFFF8C00)),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = AppColors.primary
        )
    ) {
        Text(
            text = "More infos",
            fontSize = 16.sp,
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun DeviceCard(device: DeviceInfo) {
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
                    text = device.name,
                    fontSize = 16.sp,
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Location Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        modifier = Modifier.size(16.dp),
                        tint = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = device.location,
                        fontSize = 14.sp,
                        fontFamily = PlusJakartaSans,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = device.timestamp,
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
fun DeviceCard2(task: TaskInfo ) {
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
                Row (Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
                    Text(
                        text = task.deviceName,
                        fontSize = 16.sp,
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = task.problem,
                        fontSize = 12.sp,
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Medium
                    )

                }


                Spacer(modifier = Modifier.height(4.dp))

                // Location Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        modifier = Modifier.size(16.dp),
                        tint = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = task.location,
                        fontSize = 14.sp,
                        fontFamily = PlusJakartaSans,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text =task.maintenanceTime,
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
fun DeviceSection2(
    sectionTitle: String,
    titleColor: Color,
    batteryLevel: Int,
    onMoreInfoClick: (deviceId: Int) -> Unit,
    onStartMaintenanceClick: (taskId: Int) -> Unit,
    tasks: List<TaskInfo>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // Section Title with Battery
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sectionTitle,
                color = titleColor,
                fontFamily = PlusJakartaSans,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Battery Indicator
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$batteryLevel%",
                    fontSize = 14.sp,
                    fontFamily = PlusJakartaSans,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_batt),
                    contentDescription = "Battery",
                    modifier = Modifier.size(24.dp),
                    tint = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Device Card
        tasks.forEach { task ->
            DeviceCard2(task)
            //buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Button 1
                OutlinedButton(
                    onClick = {onMoreInfoClick },
                    modifier = Modifier
                        .weight(1f)
                        .padding( vertical = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFFF8C00)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = AppColors.primary
                    )
                ) {
                    Text(
                        text = "Device details",
                        fontSize = 12.sp,
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }

                // Button 2
                OutlinedButton(
                    onClick = { onStartMaintenanceClick},
                    modifier = Modifier
                        .weight(1f)
                        .padding( vertical = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFFF8C00)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White,
                        containerColor = AppColors.primary
                    )
                ) {
                    Text(
                        text = "Start maintenance",
                        fontSize = 12.sp,
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
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
