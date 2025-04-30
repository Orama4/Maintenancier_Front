package com.example.clientmaintenancier.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clientmaintenancier.R
import com.example.clientmaintenancier.navigation.Screen
import com.example.clientmaintenancier.ui.theme.AppColors
import com.example.clientmaintenancier.ui.theme.PlusJakartaSans
import com.example.clientmaintenancier.viewmodels.DeviceViewModel

@Composable
fun DeviceDetailsScreen(
    deviceId: Int,
    navController: NavController,
    viewModel: DeviceViewModel
) {
    val device by viewModel.device
    val loading by viewModel.loading2.collectAsState()
    val error by viewModel.error2.collectAsState()

    LaunchedEffect(deviceId) {
        viewModel.fetchDeviceById(deviceId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            error != null -> {
                Text(
                    text = "Error: $error",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            device == null -> {
                Text(
                    text = "No device data received",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 48.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 25.dp)
                            .verticalScroll(rememberScrollState()),
                    ) {
                        // Header with back button
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

                            Text(
                                text = "Device Details",
                                modifier = Modifier.padding(8.dp),
                                color = AppColors.darkBlue,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = PlusJakartaSans,
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Device name and status
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = device!!.nom,
                                fontFamily = PlusJakartaSans,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.writingBlue
                            )

                            Text(
                                text = when (device?.status) {
                                    "Actif" -> "Actif"
                                    "Banne" -> "Banné"
                                    "Hors_service" -> "Hors service"
                                    "Defectueux" -> "Defectueux"
                                    "En_maintenance" -> "En maintenance"
                                    else -> "Not available"
                                },
                                fontFamily = PlusJakartaSans,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = when (device!!.status) {
                                    "Actif" -> AppColors.green
                                    "Banne" -> AppColors.primary
                                    "Hors_service" -> AppColors.red
                                    "Defectueux" -> AppColors.red
                                    "En_maintenance" -> Color(0xFF2196F3)
                                    else -> AppColors.grey
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Last seen and peripherals
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Mac address: ")
                                    }
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                        append(device?.macAdresse)
                                    }
                                },
                                fontFamily = PlusJakartaSans,
                                fontSize = 14.sp,
                                color = AppColors.writingBlue
                            )

                            Spacer(modifier = Modifier.height(30.dp))

                            // Peripherals section
                            val peripheriques = device!!.peripheriques.entries.joinToString(", ") { "${it.key}: ${it.value}" }
                            Text(
                                text = "Peripheriques:",
                                fontFamily = PlusJakartaSans,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.darkBlue
                            )

                            Text(
                                text = peripheriques,
                                fontFamily = PlusJakartaSans,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = AppColors.darkBlue
                            )

                            Spacer(modifier = Modifier.height(30.dp))

                            // Location section
                            val localisation = device!!.localisation.entries.joinToString(", ") { "${it.key}: ${it.value}" }
                            Text(
                                text = "Localisation:",
                                fontFamily = PlusJakartaSans,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.darkBlue
                            )

                            Text(
                                text = localisation,
                                fontFamily = PlusJakartaSans,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = AppColors.darkBlue
                            )

                            Spacer(modifier = Modifier.height(30.dp))
                            Text(
                                text = "CPU usage:",
                                fontFamily = PlusJakartaSans,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.darkBlue
                            )

                            Text(
                                text = device?.cpuUsage.toString(),
                                fontFamily = PlusJakartaSans,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = AppColors.darkBlue
                            )

                            Spacer(modifier = Modifier.height(30.dp))
                            Text(
                                text = "RAM usage:",
                                fontFamily = PlusJakartaSans,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.darkBlue
                            )

                            Text(
                                text = device?.ramUsage.toString(),
                                fontFamily = PlusJakartaSans,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = AppColors.darkBlue
                            )
                        }



                    }
                }
            }
        }
    }
}