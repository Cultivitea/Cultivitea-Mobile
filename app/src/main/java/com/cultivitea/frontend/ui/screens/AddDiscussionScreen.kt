package com.cultivitea.frontend.ui.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cultivitea.frontend.ui.composables.CustomAppBar
import com.cultivitea.frontend.ui.theme.PrimaryBrown
import com.cultivitea.frontend.ui.theme.PrimaryGreen
import com.cultivitea.frontend.viewmodel.MainViewModel

@Composable
fun AddDiscussionScreen(navController : NavController, viewModel: MainViewModel) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        topBar = { CustomAppBar(screenTitle = "Add Discussion", onBackClick = {navController.popBackStack()}, showBack = true) },
        content = { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            ) {
                Column (modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    TextField(
                        value = title,
                        onValueChange = { title = it
                        },
                        label = { Text("Discussion Title") },
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
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = content,
                        onValueChange = { content = it
                        },
                        label = { Text("Content") },
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
                            viewModel.addDiscussion(
                                title = title,
                                content = content
                            ) { response ->
                                isLoading = false
                                if (response != null) {
                                    navController.popBackStack()
                                } else {
                                    Log.e("AddDiscussionScreen", "Failed to edit profile")
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
}