package com.cultivitea.frontend.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cultivitea.frontend.data.api.response.CommentItem
import com.cultivitea.frontend.data.api.response.DiscussionItem
import com.cultivitea.frontend.helper.getTimeAgo
import com.cultivitea.frontend.ui.composables.CommentCard
import com.cultivitea.frontend.ui.composables.CustomAppBar
import com.cultivitea.frontend.ui.theme.GrayInput
import com.cultivitea.frontend.ui.theme.NavBrown
import com.cultivitea.frontend.ui.theme.PrimaryGreen
import com.cultivitea.frontend.viewmodel.MainViewModel

@Composable
fun DiscussionDetailScreen(navController: NavController, discussionItem: DiscussionItem, viewModel: MainViewModel) {
    var comments by remember { mutableStateOf<List<CommentItem>?>(null) }
    var addComment by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(discussionItem.discussionId) {
        viewModel.getComments(discussionItem.discussionId!!)
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomAppBar(screenTitle = "Diskusi", onBackClick = {navController.popBackStack()}, showBack = true)
        },
        content = { paddingValues ->
            Column(modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 12.dp, vertical = 12.dp).verticalScroll(rememberScrollState()).height(IntrinsicSize.Max)) {
                val timeAgo = getTimeAgo(discussionItem.createdAt!!)

                Column {
                    Text(
                        text = discussionItem.title!!,
                        style = MaterialTheme.typography.titleMedium.copy(color = NavBrown, fontSize = 24.sp)
                    )
                    Row(
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = discussionItem.creator!! + " - ",
                            style = MaterialTheme.typography.labelSmall.copy(color = GrayInput, fontSize = 10.sp, fontWeight = FontWeight.Normal)
                        )
                        Text(
                            text = timeAgo,
                            style = MaterialTheme.typography.labelSmall.copy(color = GrayInput, fontSize = 10.sp, fontWeight = FontWeight.Normal)
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = discussionItem.content!!,
                        style = MaterialTheme.typography.labelMedium.copy(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Light)
                    )
                }
                HorizontalDivider(color = GrayInput.copy(alpha = 0.3f), thickness = 1.dp, modifier = Modifier.padding(vertical = 20.dp))
                Column  {
                    Text(
                        text = "Komentar",
                        style = MaterialTheme.typography.titleMedium.copy(color = NavBrown, fontSize = 24.sp)
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    comments?.forEach { comment ->
                        CommentCard(comment)
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
                Card(shape = RoundedCornerShape(4.dp), border = BorderStroke(1.dp, PrimaryGreen), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.fillMaxWidth()){
                    TextField(
                        value = addComment,
                        onValueChange = {
                            addComment = it
                        },
                        placeholder = { Text(text = "Tulis komentar...", style = MaterialTheme.typography.labelMedium.copy(color = GrayInput, fontSize = 12.sp, fontWeight = FontWeight.Normal))},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        colors = TextFieldDefaults.colors(
                            errorContainerColor = Color.White,
                            errorIndicatorColor = Color.White,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            cursorColor = PrimaryGreen
                        ),
                    )
                    HorizontalDivider(color = PrimaryGreen, thickness = 1.dp, modifier = Modifier.padding(top = 20.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Button(
                            onClick = {
                                viewModel.addComment(discussionItem.discussionId!!, addComment)
                                addComment = ""
                            },
                            modifier = Modifier.wrapContentSize(),
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = PrimaryGreen
                            )
                        ) {
                            Icon(Icons.Filled.Send, contentDescription = "Kirim", Modifier.size(20.dp))
                        }
                    }
                }
            }
        }
    )
    viewModel.comments.observe(LocalLifecycleOwner.current) { item ->
        Log.d("ProfileGet", "Response: $item")
        comments = item
    }
}
