package com.cultivitea.frontend.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cultivitea.frontend.helper.createImagePart
import com.cultivitea.frontend.helper.getRealPathFromURI
import com.cultivitea.frontend.ui.composables.CustomAppBar
import com.cultivitea.frontend.ui.composables.ProfileImage
import com.cultivitea.frontend.ui.theme.PrimaryBrown
import com.cultivitea.frontend.ui.theme.PrimaryGreen
import com.cultivitea.frontend.viewmodel.MainViewModel

@Composable
fun EditProfileScreen(navController: NavController, viewModel: MainViewModel) {
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUrl = getRealPathFromURI(context, it)
        }
    }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        topBar = { CustomAppBar(screenTitle = "Edit Profile", onBackClick = {navController.popBackStack()}, showBack = true) },
        content = { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProfileImage(imageUrl = imageUrl)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { galleryLauncher.launch("image/*") },
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = PrimaryGreen
                        ),
                        border = BorderStroke(1.dp, PrimaryGreen)
                    ) {
                        Text(
                            text = "Change Picture",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 18.sp,
                                color = PrimaryGreen,
                                fontWeight = FontWeight.Normal
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth().border(1.dp, PrimaryBrown, RoundedCornerShape(20.dp)),
                        enabled = false,
                        colors = TextFieldDefaults.colors(
                            errorContainerColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedLabelColor = Color.Black,
                            disabledContainerColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = email,
                        onValueChange = { email = it},
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth().border(1.dp, PrimaryBrown, RoundedCornerShape(20.dp)),
                        enabled = false,
                        colors = TextFieldDefaults.colors(
                            errorContainerColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedLabelColor = Color.Black,
                            disabledContainerColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
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
                        label = { Text("Phone Number") },
                        modifier = Modifier.fillMaxWidth().border(1.dp, PrimaryBrown, RoundedCornerShape(20.dp)),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.Black,
                            errorContainerColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedLabelColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = dateOfBirth,
                        onValueChange = { dateOfBirth = it
                        },
                        label = { Text("Date of Birth (dd/mm/yyyy)") },
                        modifier = Modifier.fillMaxWidth().border(1.dp, PrimaryBrown, RoundedCornerShape(20.dp)),
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.Black,
                            errorContainerColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedLabelColor = Color.Black
                        )

                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            isLoading = true
                            viewModel.editProfile(
                                name = username,
                                phoneNumber = phoneNumber,
                                dateOfBirth = dateOfBirth,
                                image = createImagePart(imageUrl)
                            ) { editProfileResponse, errorMessage ->
                                isLoading = false
                                if (editProfileResponse != null) {
                                    navController.popBackStack()
                                } else {
                                    Log.e("EditProfileScreen", "Failed to edit profile: $errorMessage")
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Save",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 18.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Normal
                            ),
                        )
                    }
                }
                if (isLoading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }
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




