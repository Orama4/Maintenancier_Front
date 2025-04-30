package com.example.clientmaintenancier.ui.screens
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.clientmaintenancier.R
import com.example.clientmaintenancier.api.VerifyOTPRequest
import com.example.clientmaintenancier.navigation.Screen
import com.example.clientmaintenancier.ui.theme.AppColors
import com.example.clientmaintenancier.ui.theme.PlusJakartaSans
import com.example.clientmaintenancier.viewmodels.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationOtp(navController: NavController, viewModel: AuthViewModel, email: String) {
    val otpValues = remember { mutableStateListOf("", "", "", "", "", "") }
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val otpVerified by viewModel.otpVerified.collectAsState()

    LaunchedEffect(otpVerified) {
        if (otpVerified) {
            navController.navigate(Screen.ResetPassword.createRoute(email))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.darkBlue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.padding(top = 140.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Verification",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = PlusJakartaSans
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "We have sent a code to your email",
                fontSize = 18.sp,
                color = Color.Gray,
                fontFamily = PlusJakartaSans
            )
            Text(
                text = email,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = PlusJakartaSans
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .fillMaxHeight(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "CODE",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row {
                            Text(
                                text = "Resend",
                                color = AppColors.darkBlue,
                                fontWeight = FontWeight.Bold,
                                fontFamily = PlusJakartaSans,
                                fontSize = 14.sp
                            )
                            Text(
                                text = " in 50 sec",
                                color = AppColors.darkBlue,
                                fontWeight = FontWeight.Normal,
                                fontFamily = PlusJakartaSans,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        otpValues.forEachIndexed { index, _ ->
                            OutlinedTextField(
                                value = otpValues[index],
                                onValueChange = { text ->
                                    if (text.length <= 1) {
                                        otpValues[index] = text
                                    }
                                },
                                modifier = Modifier
                                    .size(50.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = Color.White,
                                    focusedBorderColor = Color.White,
                                    containerColor = AppColors.lightBlue
                                ),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                singleLine = true
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(26.dp))

                    Button(
                        onClick = {
                            val otp = otpValues.joinToString("")
                            viewModel.verifyForgotPasswordOTP(VerifyOTPRequest(email = email, otp = otp))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8000)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "VERIFY",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = PlusJakartaSans
                        )
                    }
                }
            }
        }

        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        error?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

