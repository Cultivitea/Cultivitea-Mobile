package com.cultivitea.frontend.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cultivitea.frontend.data.api.response.DiscussionItem
import com.cultivitea.frontend.ui.composables.DiscussionCard
import com.cultivitea.frontend.ui.composables.VideoCard
import com.cultivitea.frontend.ui.theme.NavBrown
import com.cultivitea.frontend.ui.theme.PrimaryBrown
import com.cultivitea.frontend.viewmodel.MainViewModel
import com.google.gson.Gson

@Composable
fun ForumScreen(navController: NavController, viewModel: MainViewModel) {
    var discussions by remember { mutableStateOf<List<DiscussionItem>?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getAllDiscussions()
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(text ="Featured Videos", style = MaterialTheme.typography.titleMedium.copy(color = NavBrown, fontSize = 24.sp))
        Spacer(modifier = Modifier.padding(8.dp))
        Column {
            VideoCard(
                title = "Title",
                description = "Description",
                videoUrl = "https://www.youtube.com/watch?v=VkCKHgtsQZk",
                thumbnailRes = 1
            )
            Spacer(modifier = Modifier.padding(4.dp))
            VideoCard(
                title = "Title",
                description = "Description",
                videoUrl = "https://www.youtube.com/watch?v=VkCKHgtsQZk",
                thumbnailRes = 1
            )
            Spacer(modifier = Modifier.padding(4.dp))
            VideoCard(
                title = "Title",
                description = "Description",
                videoUrl = "https://www.youtube.com/watch?v=VkCKHgtsQZk",
                thumbnailRes = 1
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Discussion Threads",
            style = MaterialTheme.typography.titleMedium.copy(color = NavBrown, fontSize = 24.sp)
        )
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

    viewModel.discussions.observe(LocalLifecycleOwner.current) { item ->
        Log.d("ProfileGet", "Response: $item")
        discussions = item

    }
}

//@Composable
//@Preview
//fun ForumScreenPreview() {
//    ForumScreen()
//}