package com.cultivitea.frontend.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddToPhotos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cultivitea.frontend.R
import com.cultivitea.frontend.data.api.response.DiscussionItem
import com.cultivitea.frontend.ui.composables.DiscussionCard
import com.cultivitea.frontend.ui.composables.VideoCard
import com.cultivitea.frontend.ui.theme.NavBrown
import com.cultivitea.frontend.ui.theme.PrimaryGreen
import com.cultivitea.frontend.viewmodel.MainViewModel
import com.google.gson.Gson

@Composable
fun ForumScreen(navController: NavController, viewModel: MainViewModel) {
    var discussions by remember { mutableStateOf<List<DiscussionItem>?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getAllDiscussions()
    }

    Scaffold(
        containerColor = Color.White,
        content = { paddingValues ->
            Box {
                Column(modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())) {
                    Text(text = "Rekomendasi", style = MaterialTheme.typography.titleMedium.copy(color = NavBrown, fontSize = 24.sp))
                    Spacer(modifier = Modifier.padding(8.dp))
                    Column {
                        VideoCard(
                            title = "Plucking in Tea : Best Practices",
                            description = "Description",
                            videoUrl = "https://www.youtube.com/watch?v=0n4vd5j0DX8",
                            thumbnailRes = R.drawable.video1
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        VideoCard(
                            title = "Making cuttings from Tea Plants - How to propagate!",
                            description = "Description",
                            videoUrl = "https://www.youtube.com/watch?v=S3RBAu5oDEU",
                            thumbnailRes = R.drawable.video2
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        VideoCard(
                            title = "Pruning and Skiffing",
                            description = "Description",
                            videoUrl = "https://www.youtube.com/watch?v=wjwxCe2i8Zk",
                            thumbnailRes = R.drawable.video3
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Row( modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 12.dp),
                            text = "Forum Diskusi",
                            style = MaterialTheme.typography.titleMedium.copy(color = NavBrown, fontSize = 24.sp)
                        )
                        Button(
                            onClick = { navController.navigate("addDiscussion") },
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.White,
                                contentColor = PrimaryGreen
                            ),
                            border = BorderStroke(1.dp, PrimaryGreen)
                        ) {
                            Icon(Icons.Filled.AddToPhotos, contentDescription = "Kembali")

                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Column {
                        discussions?.forEach { discussion ->
                            DiscussionCard(discussion, onClick = {
                                val discussionJson = Gson().toJson(discussion)
                                navController.navigate("discussionDetail/$discussionJson")
                            })
                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }

        }
    )

    viewModel.discussions.observe(LocalLifecycleOwner.current) { item ->
        Log.d("ProfileGet", "Response: $item")
        discussions = item
    }
}