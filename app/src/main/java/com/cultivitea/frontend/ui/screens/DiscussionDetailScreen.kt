package com.cultivitea.frontend.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cultivitea.frontend.data.api.response.CommentItem
import com.cultivitea.frontend.data.api.response.DiscussionItem
import com.cultivitea.frontend.helper.getTimeAgo
import com.cultivitea.frontend.ui.composables.CommentCard
import com.cultivitea.frontend.ui.theme.GrayInput
import com.cultivitea.frontend.ui.theme.NavBrown
import com.cultivitea.frontend.viewmodel.MainViewModel

@Composable
fun DiscussionDetailScreen(discussionItem: DiscussionItem, viewModel: MainViewModel) {
    var comments by remember { mutableStateOf<List<CommentItem>?>(null) }


    LaunchedEffect(discussionItem.discussionId) {
        viewModel.getComments(discussionItem.discussionId!!)
    }

    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)) {
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
                style = MaterialTheme.typography.labelMedium.copy(color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Light)
            )
        }
        HorizontalDivider(color = GrayInput.copy(alpha = 0.3f), thickness = 1.dp, modifier = Modifier.padding(vertical = 20.dp))
        Column {
            Text(
                text = "Comments",
                style = MaterialTheme.typography.titleMedium.copy(color = NavBrown, fontSize = 24.sp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            comments?.forEach { comment ->
                CommentCard(comment)
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }

    viewModel.comments.observe(LocalLifecycleOwner.current) { item ->
        Log.d("ProfileGet", "Response: $item")
        comments = item
    }
}
