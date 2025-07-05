
package com.example.clientmaintenancier.ui.screens

import AmazingErrorPopup
import ElegantCircularProgressIndicator
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clientmaintenancier.api.RegisterRequest
import com.example.clientmaintenancier.api.SendOTPRequest
import com.example.clientmaintenancier.navigation.Screen
import com.example.clientmaintenancier.ui.theme.AppColors
import com.example.clientmaintenancier.ui.theme.PlusJakartaSans
import com.example.clientmaintenancier.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavController, viewModel: AuthViewModel) {
    val textStates = remember { mutableStateListOf("", "", "", "", "", "") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordVisible2 by remember { mutableStateOf(false) }
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val registerSuccess by viewModel.registerSuccess.collectAsState()
    val otpSent by viewModel.otpSent.collectAsState()
    LaunchedEffect(registerSuccess) {
        if (registerSuccess) {
            navController.navigate(Screen.Login.route)
        }
    }
    LaunchedEffect(otpSent) {
        if (otpSent) {
            navController.navigate(Screen.Verification.createRoute(textStates[1]))
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
            modifier = Modifier.padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Sign Up",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = PlusJakartaSans,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Please sign up to get started",
                fontFamily = PlusJakartaSans,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .fillMaxHeight(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        "NAME",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.SemiBold
                    )
                    OutlinedTextField(
                        value = textStates[0],
                        onValueChange = { textStates[0] = it },
                        placeholder = { Text("Abla Rabia", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            containerColor = AppColors.lightBlue
                        )
                    )
                    Spacer(modifier = Modifier.height(7.dp))

                    // Email Field
                    Text(
                        "EMAIL",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.SemiBold
                    )
                    OutlinedTextField(
                        value = textStates[1],
                        onValueChange = { textStates[1] = it },
                        placeholder = { Text("example@gmail.com", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            containerColor = AppColors.lightBlue
                        )
                    )
                    Spacer(modifier = Modifier.height(7.dp))


                    // Password Field
                    Text(
                        "PASSWORD",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.SemiBold
                    )
                    OutlinedTextField(
                        value = textStates[2],
                        onValueChange = { textStates[2] = it },
                        placeholder = { Text("********", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        shape = RoundedCornerShape(8.dp),
                        visualTransformation = if (!passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        trailingIcon = {
                            IconButton(
                                onClick = { passwordVisible = !passwordVisible },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                )
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            containerColor = AppColors.lightBlue
                        )
                    )
                    Spacer(modifier = Modifier.height(7.dp))

                    // Verify Password Field
                    Text(
                        "RE-TYPE PASSWORD",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.SemiBold
                    )
                    OutlinedTextField(
                        value = textStates[3],
                        onValueChange = { textStates[3] = it },
                        placeholder = { Text("********", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        shape = RoundedCornerShape(8.dp),
                        visualTransformation = if (!passwordVisible2) PasswordVisualTransformation() else VisualTransformation.None,
                        trailingIcon = {
                            IconButton(
                                onClick = { passwordVisible2 = !passwordVisible2 },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Icon(
                                    imageVector = if (passwordVisible2) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                )
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            containerColor = AppColors.lightBlue
                        )
                    )
                    Spacer(modifier = Modifier.height(7.dp))

                    // Address Field
                    Text(
                        "ADDRESS",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.SemiBold
                    )
                    OutlinedTextField(
                        value = textStates[4],
                        onValueChange = { textStates[4] = it },
                        placeholder = { Text("123 Main St", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            containerColor = AppColors.lightBlue
                        )
                    )
                    Spacer(modifier = Modifier.height(7.dp))

                    // Phone Number Field
                    Text(
                        "PHONE NUMBER",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.SemiBold
                    )
                    OutlinedTextField(
                        value = textStates[5],
                        onValueChange = { textStates[5] = it },
                        placeholder = { Text("123-456-7890", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            containerColor = AppColors.lightBlue
                        )
                    )
                }

                Spacer(modifier = Modifier.height(7.dp))

                // Register Button
                Button(
                    onClick = {
                        val request  = RegisterRequest(
                            email = textStates[1],
                            password = textStates[2],
                            firstname = textStates[0],
                            lastname = "",
                            phonenumber = textStates[5],
                            address = textStates[4]
                        )
                        viewModel.register(request)
//
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8000)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Sign Up",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = PlusJakartaSans
                    )
                }

                Spacer(modifier = Modifier.height(7.dp))

                // Sign Up
                Row {
                    Text(
                        "Already have an account?",
                        color = Color.Gray,
                        fontFamily = PlusJakartaSans
                    )
                    Text(
                        " LOGIN",
                        color = Color(0xFFFF8000),
                        fontWeight = FontWeight.Bold,
                        fontFamily = PlusJakartaSans,
                        modifier = Modifier.clickable(onClick = { navController.navigate(Screen.Login.route) })
                    )
                }

                if (loading) {
                    ElegantCircularProgressIndicator()
                }

                error?.let {
                    AmazingErrorPopup(it)

                }
            }
        }
    }
}