package com.example.clientmaintenancier.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientmaintenancier.R
import com.example.clientmaintenancier.ui.theme.AppColors
import com.example.clientmaintenancier.ui.theme.PlusJakartaSans

enum class TaskState {
    IN_PROGRESS,
    EN_PANNE,
    COMPLETED
}

data class TaskDetails(
    val id: Int,
    val status: TaskState,
    val estimatedCompletionTime: String,
    val date: String,
    val priority: String,
    val problem: String,
    val deviceName : String, // replace by deviceId apres
    val description : String,
    val userName : String , // replace by userId apres
    val location: String,
)

@Composable
fun TaskDetailsScreen(taskId: Int = 0) {
    // In a real app, you would fetch task details based on taskId
    // For now, we'll use a hardcoded task for demonstration
    val task = if (taskId > 0) {
        // This simulates fetching a task by ID
        TaskDetails(
            id = taskId,
            status = TaskState.EN_PANNE,
            estimatedCompletionTime = "3h",
            date = "10/03/2025",
            priority = "High",
            problem = "Battery Replacement",
            deviceName = "Monitor",
            description = "Replace the battery of Device 123, which is currently at 10% and causing frequent disconnections.",
            userName = "Imene L",
            location = "You - 49th St Los Angeles, California",
        )
    } else {
        // Fallback for when no ID is provided
        TaskDetails(
            id = 1,
            status = TaskState.COMPLETED,
            estimatedCompletionTime = "3h",
            date = "10/03/2025",
            priority = "Medium",
            problem = "Battery Issue",
            deviceName = "Monitor",
            description = "Replace the battery of Device 123, which is currently at 10% and causing frequent disconnections.",
            userName = "John doe",
            location = "You - 49th St Los Angeles, California",
        )
    }

    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier.fillMaxSize().padding(top = 48.dp),
    ){
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
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
            ){
                Column(){
                    Text(
                        text = "Task #"+task.id,
                        fontFamily = PlusJakartaSans,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.writingBlue
                    )
                    Text(
                        text = task.date,
                        fontFamily = PlusJakartaSans,
                        fontSize = 12.sp,
                        color = AppColors.writingBlue
                    )
                }

                Row(){
                    Box(
                        modifier = Modifier
                            .background(AppColors.grey, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp) // Adds spacing inside the box
                            .wrapContentSize(), // Ensures Box expands with text
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = task.estimatedCompletionTime,
                            fontFamily = PlusJakartaSans,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = AppColors.writingBlue
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))


                    Box(
                        modifier = Modifier
                            .background(
                                color = when (task.status) {
                                    TaskState.EN_PANNE -> AppColors.red
                                    TaskState.COMPLETED -> AppColors.green
                                    TaskState.IN_PROGRESS -> AppColors.primary
                                    else -> Color.Gray
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .wrapContentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when (task.status) {
                                TaskState.EN_PANNE -> "En panne"
                                TaskState.COMPLETED -> "Completed"
                                TaskState.IN_PROGRESS -> "In progress"
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
                        text = task.priority + " priority",
                        fontFamily = PlusJakartaSans,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.red
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = task.problem,
                        fontFamily = PlusJakartaSans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.writingBlue
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
                        text = task.deviceName,
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
                        text = task.description,
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
                            text = task.userName,
                            color = AppColors.darkBlue,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Medium,
                            fontFamily = PlusJakartaSans
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))


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
                            text = task.date,
                            color = AppColors.darkBlue,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.SemiBold,
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
                            text = task.location,
                            color = AppColors.darkBlue,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Medium,
                            fontFamily = PlusJakartaSans
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Pushes button to bottom

            // Conditional button display based on task status
            when (task.status) {
                TaskState.IN_PROGRESS -> {
                    Column(
                        modifier = Modifier.padding(bottom = 30.dp)
                    ) {
                        OutlinedButton(
                            onClick = { /* Action du bouton ici */ },
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
                TaskState.EN_PANNE -> {
                    Column(
                        modifier = Modifier.padding(bottom = 30.dp)
                    ) {
                        Button(
                            onClick = { /* Action du bouton ici */ },
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
                TaskState.COMPLETED -> {
                    // No button displayed for completed tasks
                }
            }
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
private fun InfoItem(value: String, color: Color, size: TextUnit) {
    Text(
        text = value,
        fontFamily = PlusJakartaSans,
        fontSize = size,
        color = color
    )
}