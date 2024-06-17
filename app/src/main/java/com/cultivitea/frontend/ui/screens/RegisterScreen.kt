package com.cultivitea.frontend.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.cultivitea.frontend.ui.theme.PrimaryBrown
import com.cultivitea.frontend.ui.theme.PrimaryGreen
import com.cultivitea.frontend.viewmodel.MainViewModel

@Composable
fun RegisterScreen(onNavigateToLogin: () -> Unit, viewModel: MainViewModel) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var registerError by rememberSaveable { mutableStateOf<String?>(null) }
    var usernameError by rememberSaveable { mutableStateOf<String?>(null) }
    var emailError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
    var showSuccessDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold (containerColor = Color.White) { paddingValues ->
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
                        .requiredSize(200.dp)
                        .padding(top = 64.dp, bottom = 30.dp)
                )
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start) {
                    Column(modifier = Modifier.padding(vertical = 16.dp)) {
                        Text(
                            text = stringResource(id = R.string.register),
                            style = MaterialTheme.typography.titleMedium,
                        )

                        Spacer(modifier = Modifier.padding(4.dp))

                        Text(
                            text = stringResource(id = R.string.register_description),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.username),
                        style = MaterialTheme.typography.labelSmall,
                    )
                    TextField(
                        value = username,
                        onValueChange = {
                            username = it
                            usernameError = null
                        },
                        isError = usernameError != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                if (usernameError != null) Color.Red else PrimaryBrown,
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
                    if (usernameError != null) {
                        Text(
                            text = usernameError ?: "",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        onClick = {
                            isLoading = true
                            viewModel.registerUser(username, email, password) { signUpResponse, error ->
                                isLoading = false
                                if (signUpResponse != null && !signUpResponse.error!!) {
                                    showSuccessDialog = true
                                } else {
                                    registerError = error ?: "Unknown error"
                                    signUpResponse?.details?.let { details ->
                                        usernameError = details["username"]
                                        emailError = details["email"]
                                        passwordError = details["password"]
                                    }
                                    Log.d("RegisterScreen", "Response: $signUpResponse")
                                    Log.d("RegisterScreen", "Error registering user: $usernameError, $emailError, $passwordError")
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.sign_up),
                            modifier = Modifier.padding(horizontal = 40.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 18.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    ClickableAuthText(onNavigateToLogin, R.string.signin, R.string.already_have_account)
                }
            }
            if (registerError != null && registerError != "Unknown error") {
                AlertDialog(
                    onDismissRequest = { registerError = null },
                    confirmButton = {
                        TextButton(onClick = { registerError = null }) {
                            Text("OK")
                        }
                    },
                    text = {
                        Text(
                            registerError ?: "",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                )
            }
            if (showSuccessDialog) {
                AlertDialog(
                    onDismissRequest = { showSuccessDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showSuccessDialog = false
                            onNavigateToLogin()
                        }) {
                            Text("OK")
                        }
                    },
                    text = {
                        Text(
                            "Account Successfully Created",
                        )
                    }
                )
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
}

