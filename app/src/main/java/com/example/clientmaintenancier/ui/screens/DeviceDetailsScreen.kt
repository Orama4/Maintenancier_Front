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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clientmaintenancier.R
import com.example.clientmaintenancier.navigation.Screen
import com.example.clientmaintenancier.ui.theme.AppColors
import com.example.clientmaintenancier.ui.theme.PlusJakartaSans


enum class DeviceState {
    CONNECTED,
    DISCONNECTED,
}

enum class Connectivity {
    active,
    inactive,
}

data class DeviceDetails(
    val id: Int,
    val deviceName : String, // replace by deviceId apres
    val status: DeviceState,
    val lastSeen: String,
    val battery: String,
    val version: String,
    val purchaseDate: String,
    val expiryDate: String,
    val cameraState: Connectivity,
    val microphoneState: Connectivity,
    val gyroscopeState: Connectivity,
    val bluetooth: Connectivity,
    val wifi: Connectivity,
    val userName : String , // replace by userId apres
    val location: String
)

@Composable
fun DeviceDetailsScreen(deviceId: Int = 0,navController: NavController) {
    // In a real app, you would fetch task details based on taskId
    // For now, we'll use a hardcoded task for demonstration
    val device = if (deviceId > 0) {
        // This simulates fetching a task by ID
        DeviceDetails(
            id = deviceId,
            deviceName = "Smart Glasses",
            status = DeviceState.CONNECTED,
            lastSeen = "12/02/2025",
            battery = "50",
            version = "v1.2.3",
            purchaseDate = "12/02/2025",
            expiryDate = "12/02/2025",
            cameraState = Connectivity.inactive,
            microphoneState = Connectivity.active,
            gyroscopeState = Connectivity.active,
            bluetooth = Connectivity.active,
            wifi =Connectivity.active ,
            userName = "Imene L",
            location = "You - 49th St Los Angeles, California"
        )
    } else {
        // Fallback for when no ID is provided
        DeviceDetails(
            id = 1,
            deviceName = "Smart Glasses",
            status = DeviceState.CONNECTED,
            lastSeen = "12/02/2025",
            battery = "50",
            version = "v1.2.3",
            purchaseDate = "12/02/2025",
            expiryDate = "12/02/2025",
            cameraState = Connectivity.active,
            microphoneState = Connectivity.active,
            gyroscopeState = Connectivity.active,
            bluetooth = Connectivity.active,
            wifi =Connectivity.active ,
            userName = "Imene L",
            location = "You - 49th St Los Angeles, California"
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 48.dp)
    ){
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 25.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp).clickable {
                        // Navigate back when the back image is clicked
                        navController.navigateUp()
                    }
                )

                Text(
                    text = "Device Details",
                    modifier = Modifier.padding(8.dp),
                    color = AppColors.darkBlue,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PlusJakartaSans,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Text(
                    text = device.deviceName,
                    fontFamily = PlusJakartaSans,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.writingBlue
                )

                Text(
                    text = when (device.status) {
                        DeviceState.CONNECTED -> "Connected"
                        DeviceState.DISCONNECTED -> "Disconnected"
                    },
                    fontFamily = PlusJakartaSans,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = when (device.status) {
                        DeviceState.CONNECTED -> AppColors.green
                        DeviceState.DISCONNECTED -> AppColors.red
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Last seen: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(device.lastSeen)
                        }
                    },
                    fontFamily = PlusJakartaSans,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.writingBlue
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = device.battery+"%",
                        fontFamily = PlusJakartaSans,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.darkBlue
                    )

                    Spacer(modifier = Modifier.width(10.dp))


                    Image(
                        painter = painterResource(id = R.drawable.ic_batt),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))


            Column {
                Text(
                    text = "User informations",
                    fontFamily = PlusJakartaSans,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = AppColors.writinggrey
                )


                Spacer(modifier = Modifier.height(12.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = device.userName,
                        color = AppColors.darkBlue,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        fontFamily = PlusJakartaSans
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))



                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.location2),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = device.location,
                        color = AppColors.darkBlue,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        fontFamily = PlusJakartaSans
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))


            Column {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Firmware Version: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(device.version)
                        }
                    },
                    fontFamily = PlusJakartaSans,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.writingBlue
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Purchase Date: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(device.purchaseDate)
                        }
                    },
                    fontFamily = PlusJakartaSans,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.writingBlue
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Warranty Expiry: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(device.expiryDate)
                        }
                    },
                    fontFamily = PlusJakartaSans,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.writingBlue
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.glasses),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }

            Column {
                Text(
                    text = "Sensors",
                    color = AppColors.darkBlue,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PlusJakartaSans
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = AppColors.writingBlue)) {
                            append("Camera:   ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = when (device.cameraState) {
                                    Connectivity.active -> AppColors.green
                                    Connectivity.inactive -> AppColors.red
                                }
                            )
                        ) {
                            append(
                                when (device.cameraState) {
                                    Connectivity.active -> "active"
                                    Connectivity.inactive -> "inactive"
                                }
                            )
                        }
                    },
                    fontFamily = PlusJakartaSans,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = AppColors.writingBlue)) {
                            append("Microphone:   ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = when (device.microphoneState) {
                                    Connectivity.active -> AppColors.green
                                    Connectivity.inactive -> AppColors.red
                                }
                            )
                        ) {
                            append(
                                when (device.microphoneState) {
                                    Connectivity.active -> "active"
                                    Connectivity.inactive -> "inactive"
                                }
                            )
                        }
                    },
                    fontFamily = PlusJakartaSans,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )
            }

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = AppColors.writingBlue)) {
                        append("Gyroscope:   ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = when (device.gyroscopeState) {
                                Connectivity.active -> AppColors.green
                                Connectivity.inactive -> AppColors.red
                            }
                        )
                    ) {
                        append(
                            when (device.gyroscopeState) {
                                Connectivity.active -> "active"
                                Connectivity.inactive -> "inactive"
                            }
                        )
                    }
                },
                fontFamily = PlusJakartaSans,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )

            Spacer(modifier = Modifier.height(40.dp))


            Column(
                modifier = Modifier.padding(bottom = 50.dp)
            ) {
                Text(
                    text = "Connectivity",
                    color = AppColors.darkBlue,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PlusJakartaSans
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = AppColors.writingBlue)) {
                            append("Bluetooth:   ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = when (device.bluetooth) {
                                    Connectivity.active -> AppColors.green
                                    Connectivity.inactive -> AppColors.red
                                }
                            )
                        ) {
                            append(
                                when (device.bluetooth) {
                                    Connectivity.active -> "active"
                                    Connectivity.inactive -> "inactive"
                                }
                            )
                        }
                    },
                    fontFamily = PlusJakartaSans,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = AppColors.writingBlue)) {
                            append("Wifi:   ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = when (device.wifi) {
                                    Connectivity.active -> AppColors.green
                                    Connectivity.inactive -> AppColors.red
                                }
                            )
                        ) {
                            append(
                                when (device.wifi) {
                                    Connectivity.active -> "active"
                                    Connectivity.inactive -> "inactive"
                                }
                            )
                        }
                    },
                    fontFamily = PlusJakartaSans,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )
            }

        }

    }
}

