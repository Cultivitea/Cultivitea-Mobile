package com.cultivitea.frontend.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cultivitea.frontend.ui.composables.AppBarAction
import com.cultivitea.frontend.ui.composables.CustomAppBar
import com.cultivitea.frontend.ui.composables.ProfileImage
import com.cultivitea.frontend.ui.theme.PrimaryGreen
import com.cultivitea.frontend.viewmodel.MainViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: MainViewModel) {
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomAppBar(
                screenTitle = "Profile",
                actions = listOf(
                    AppBarAction(
                        icon = Icons.Filled.Logout,
                        contentDescription = "Logout",
                        onClick = { showLogoutDialog = true }
                    )
                )
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(16.dp)
                    ) {
                        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            ProfileImage(imageUrl = imageUrl)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    navController.navigate("editProfile")
                                },
                                shape = RoundedCornerShape(4.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                                border = BorderStroke(1.dp, PrimaryGreen)
                            ) {
                                Text(
                                    text = "Edit Profile",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.White
                                    ),
                                )
                            }
                        }

                    }
                    Column(Modifier.fillMaxWidth().padding(16.dp)) {
                        TextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Username", color = PrimaryGreen) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false,
                            colors = TextFieldDefaults.colors(
                                disabledContainerColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                disabledTextColor = Color.Black,
                                disabledLabelColor = Color.Black,
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = email,
                            onValueChange = { email = it},
                            label = { Text("Email", color = PrimaryGreen) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false,
                            colors = TextFieldDefaults.colors(
                                disabledContainerColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                disabledTextColor = Color.Black,
                                disabledLabelColor = Color.Black,
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = phoneNumber,
                            onValueChange = {
                                val numericRegex = "[0-9]*".toRegex()
                                if (it.matches(numericRegex)) {
                                    phoneNumber = it
                                }
                            },
                            label = { Text("Phone Number", color = PrimaryGreen) },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            enabled = false,
                            colors = TextFieldDefaults.colors(
                                disabledContainerColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                disabledTextColor = Color.Black,
                                disabledLabelColor = Color.Black,
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = dateOfBirth,
                            onValueChange = { dateOfBirth = it },
                            label = { Text("Date of Birth (dd/mm/yyyy)", color = PrimaryGreen) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false,
                            colors = TextFieldDefaults.colors(
                                disabledContainerColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                disabledTextColor = Color.Black,
                                disabledLabelColor = Color.Black,
                            )
                        )
                    }
                }
            }
            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showLogoutDialog = false
                            viewModel.logout()
                        }) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLogoutDialog = false }) {
                            Text("No")
                        }
                    },
                    text = {
                        Text(
                            "Are you sure you want to logout?",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                )
            }
        }
    )

    viewModel.getSession().observe(LocalLifecycleOwner.current) { user ->
        Log.d("ProfileGet", "Response: $user")
        phoneNumber = user.phoneNumber
        email = user.email
        username = user.username
        imageUrl = user.imageUrl
        dateOfBirth = user.dateOfBirth
    }
}
