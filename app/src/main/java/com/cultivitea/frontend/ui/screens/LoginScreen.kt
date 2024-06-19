package com.cultivitea.frontend.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cultivitea.frontend.R
import com.cultivitea.frontend.ui.composables.ClickableAuthText
import com.cultivitea.frontend.ui.theme.NavBrown
import com.cultivitea.frontend.ui.theme.PrimaryBrown
import com.cultivitea.frontend.ui.theme.PrimaryGreen
import com.cultivitea.frontend.viewmodel.MainViewModel

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    viewModel: MainViewModel,
    onLoginSuccess: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var loginError by rememberSaveable { mutableStateOf<String?>(null) }
    var emailError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
    var isLoading by rememberSaveable { mutableStateOf(false) }

    Scaffold(containerColor = Color.White) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 22.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(id = R.string.logo),
                    modifier = Modifier
                        .size(200.dp)
                        .padding(top = 64.dp, bottom = 30.dp)
                )

                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start) {
                    Column(modifier = Modifier.padding(vertical = 16.dp)) {
                        Text(
                            text = stringResource(id = R.string.login),
                            style = MaterialTheme.typography.titleMedium,
                        )

                        Spacer(modifier = Modifier.padding(4.dp))

                        Text(
                            text = stringResource(id = R.string.login_description),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        text = stringResource(id = R.string.email),
                        style = MaterialTheme.typography.labelSmall,
                    )

                    TextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = null
                        },
                        isError = emailError != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                if (emailError != null) Color.Red else PrimaryBrown,
                                RoundedCornerShape(20.dp)
                            ),
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.colors(
                            errorContainerColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                    )
                    if (emailError != null) {
                        Text(
                            text = emailError ?: "",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        text = stringResource(id = R.string.password),
                        style = MaterialTheme.typography.labelSmall,
                    )

                    TextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = null
                        },
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff

                            val description = if (passwordVisible) "Hide password" else "Show password"

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = description)
                            }
                        },
                        isError = passwordError != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                if (passwordError != null) Color.Red else PrimaryBrown,
                                RoundedCornerShape(20.dp)
                            ),
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.colors(
                            errorContainerColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                    )
                    if (passwordError != null) {
                        Text(
                            text = passwordError ?: "",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 25.dp)) {
                    Button(
                        onClick = {
                            isLoading = true
                            viewModel.loginUser(email, password) { loginResponse, error ->
                                isLoading = false
                                if (loginResponse != null && !loginResponse.error!!) {
                                    val userCredential = loginResponse.userCredential
                                    if (userCredential != null) {
                                        viewModel.getProfile(userCredential.token!!, userCredential.uid!!)
                                        onLoginSuccess()
                                    }
                                } else {
                                    Log.d("LoginScreen", "Error: $error")
                                    loginError = error ?: "Unknown error"
                                    loginResponse?.details?.let { details ->
                                        emailError = details["email"]
                                        passwordError = details["password"]
                                    }
                                    Log.d("LoginScreen", "Response: $loginResponse")
                                    Log.d("LoginScreen", "Error logging in: $emailError, $passwordError")
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.signin),
                            modifier = Modifier.padding(horizontal = 40.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 18.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    ClickableAuthText(onNavigateToRegister, R.string.sign_up, R.string.dont_have_account)
                }
            }

            if (loginError != null) {
                AlertDialog(
                    onDismissRequest = { loginError = null },
                    confirmButton = {
                        TextButton(onClick = { loginError = null }) {
                            Text("OK",
                                color = PrimaryGreen)
                        }
                    },
                    text = {
                        Text(
                            loginError ?: "",
                            color = NavBrown,
                            fontSize = 18.sp
                        )
                    }
                )
            }

            if (isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(color = NavBrown)
                }
            }
        }
    }
}