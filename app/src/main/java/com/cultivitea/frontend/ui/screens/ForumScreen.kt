package com.cultivitea.frontend.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cultivitea.frontend.ui.composables.VideoCard

@Composable
fun ForumScreen() {
    Column {
        Text(text ="Featured Videos")
        Spacer(modifier = Modifier.padding(8.dp))
        Column {
            VideoCard(
                title = "Title",
                description = "Description",
                videoUrl = "https://www.youtube.com/watch?v=VkCKHgtsQZk",
                thumbnailRes = 1
            )
            VideoCard(
                title = "Title",
                description = "Description",
                videoUrl = "https://www.youtube.com/watch?v=VkCKHgtsQZk",
                thumbnailRes = 1
            )
            VideoCard(
                title = "Title",
                description = "Description",
                videoUrl = "https://www.youtube.com/watch?v=VkCKHgtsQZk",
                thumbnailRes = 1
            )
        }
    }
}