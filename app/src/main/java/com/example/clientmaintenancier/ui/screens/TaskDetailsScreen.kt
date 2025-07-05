package com.example.clientmaintenancier.ui.screens

import android.widget.Toast
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clientmaintenancier.R
import com.example.clientmaintenancier.navigation.Screen
import com.example.clientmaintenancier.ui.components.markAsCompleted
import com.example.clientmaintenancier.ui.components.takeTask
import com.example.clientmaintenancier.ui.theme.AppColors
import com.example.clientmaintenancier.ui.theme.PlusJakartaSans
import com.example.clientmaintenancier.viewmodels.TaskViewModel


@Composable
fun TaskDetailsScreen(
    taskId: Int,
    navController: NavController,
    viewModel: TaskViewModel
) {
    val task by viewModel.intervention
    val loading by viewModel.loading2.collectAsState()
    val error by viewModel.error2.collectAsState()

    LaunchedEffect(taskId) {
        viewModel.clearInterventionsData3()
        viewModel.fetchInterventionById(taskId)
    }


    val scrollState = rememberScrollState()
    var showDetailsDialog by remember { mutableStateOf(false) }
    var showCompletedDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current


    // TaskDetailsDialog component
    takeTask(
        isVisible = showDetailsDialog,
        onDismiss = { showDetailsDialog = false },
        onConfirm = { date ->
            viewModel.updateTask(
                interventionId = taskId, planDate = date, deviceId = task!!.deviceId)
            showDetailsDialog = false
            Toast.makeText(context, "Task started successfully", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Tasks.route) {
                popUpTo(Screen.Tasks.route) { inclusive = true }
            }
        }
    )

    markAsCompleted(
        isVisible = showCompletedDialog,
        onDismiss = { showCompletedDialog = false },
        onConfirm = { description ->
            viewModel.markInterventionAsCompleted(interventionId = taskId, deviceId = task!!.deviceId,description = description)
            showCompletedDialog = false
            Toast.makeText(context, "Task marked as completed successfully", Toast.LENGTH_SHORT).show()
            //You could navigate back or to another screen
            navController.navigate(Screen.Tasks.route) {
                popUpTo(Screen.Tasks.route) { inclusive = true }
            }
        }
    )


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
            task == null -> {
                Text(
                    text = "No task data received",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 48.dp).verticalScroll(rememberScrollState()),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 25.dp),
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
                                contentDescription = "Back",
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable { navController.navigateUp() }
                            )

                            Text(
                                text = "Task Details",
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
                        ) {
                            Column() {
                                Text(
                                    text = "Task #" + task?.id,
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AppColors.writingBlue
                                )
                                Text(
                                    text = task!!.type,
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 12.sp,
                                    color = AppColors.writingBlue
                                )
                            }

                            Row() {
                                Box(
                                    modifier = Modifier
                                        .background(AppColors.grey, shape = RoundedCornerShape(8.dp))
                                        .padding(
                                            horizontal = 16.dp,
                                            vertical = 8.dp
                                        ) // Adds spacing inside the box
                                        .wrapContentSize(), // Ensures Box expands with text
                                    contentAlignment = Alignment.Center
                                ) {

                                }

                                Spacer(modifier = Modifier.width(10.dp))


                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = when (task?.status) {
                                                "en_panne" -> AppColors.red
                                                "complete" -> AppColors.green
                                                "en_progres" -> AppColors.primary
                                                else -> Color.Gray
                                            },
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .wrapContentSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = when (task?.status) {
                                            "en_panne" -> "En panne"
                                            "complete" -> "Completed"
                                            "en_progres" -> "In progress"
                                            else -> "Not available"
                                        },
                                        fontFamily = PlusJakartaSans,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Column {
                            Column {
                                Text(
                                    text = task?.priority?.plus(" priority") ?: "Low priority",
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppColors.red
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = when (task?.isRemote) {
                                        true -> "Remote"
                                        false -> "On site"
                                        null -> ""
                                    },
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppColors.red
                                )

                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Column {
                                Text(
                                    text = "Device name",
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = AppColors.writinggrey
                                )

                                Text(
                                    text = task!!.device.nom,
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppColors.writingBlue
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Text(
                                    text = "Device status",
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = AppColors.writinggrey
                                )

                                Text(
                                    text = task!!.device.status,
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppColors.writingBlue
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))

                            Column {
                                Text(
                                    text = "Description",
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = AppColors.writinggrey
                                )

                                Text(
                                    text = task!!.description ?: "No description available yet",
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppColors.writingBlue
                                )

                            }

                            Spacer(modifier = Modifier.height(20.dp))


                            Column {
                                Text(
                                    text = "User informations",
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = AppColors.writinggrey
                                )


                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Email:" + task!!.device.email ?: "No email available",
                                        color = AppColors.darkBlue,
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = PlusJakartaSans
                                    )
                                }


                                Spacer(modifier = Modifier.height(20.dp))

                                Text(
                                    text = "Plan date",
                                    fontFamily = PlusJakartaSans,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = AppColors.writinggrey
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.calendar),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(20.dp)
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Text(
                                        text = task!!.planDate ?: "No plan date available yet",
                                        color = AppColors.darkBlue,
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = PlusJakartaSans
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(40.dp)) // Pushes button to bottom

                        // Conditional button display based on task status
                        when (task?.status) {
                            "en_progres" -> {
                                Column(
                                    modifier = Modifier.padding(bottom = 30.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = { showCompletedDialog = true},
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = AppColors.green
                                        ),
                                        border = BorderStroke(1.dp, AppColors.green),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = "Mark completed",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            fontFamily = PlusJakartaSans,
                                            color = AppColors.green
                                        )
                                    }
                                }
                            }

                            "en_panne" -> {
                                Column(
                                    modifier = Modifier.padding(bottom = 30.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            showDetailsDialog = true
                                        }, // Show dialog when button is clicked
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.primary),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = "Take task",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            fontFamily = PlusJakartaSans,
                                            color = Color.White
                                        )
                                    }
                                }
                            }

                            "complete" -> {
                                // No button displayed for completed tasks
                            }
                        }

                    }
                }
            }
        }
    }

}