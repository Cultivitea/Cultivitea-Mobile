package com.cultivitea.frontend.ui.composables

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cultivitea.frontend.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.cultivitea.frontend.ui.theme.GrayInput
import com.cultivitea.frontend.ui.theme.PrimaryBrown
import com.cultivitea.frontend.ui.theme.PrimaryGreen

@Composable
fun ClickableAuthText(onNavigateToRegister: () -> Unit, hyperlink: Int, description: Int) {
    Row {
        Text(
            text = stringResource(id = description),
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = stringResource(id = hyperlink),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.clickable { onNavigateToRegister() }
        )
    }
}


@Composable
fun VideoCard(title: String, description: String, videoUrl: String, thumbnailRes: Int) {
    val context = LocalContext.current

    val openYouTube = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    OutlinedCard(shape = RoundedCornerShape(4.dp), modifier= Modifier
        .padding(8.dp)
        .fillMaxWidth(), border = BorderStroke(1.dp, PrimaryBrown)) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium, color = PrimaryGreen, fontSize = 18.sp)
                Text(description, style = MaterialTheme.typography.bodyMedium, color = GrayInput)
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                        // Check if YouTube app is installed
                        intent.setPackage("com.google.android.youtube")
                        if (intent.resolveActivity(context.packageManager) != null) {
                            startActivity(context, intent, null)
                        } else {
                            // Open YouTube in browser if the app is not installed
                            intent.setPackage(null)
                            startActivity(context, intent, null)
                        }
                    },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                ) {
                    Text(text = "Watch Now", style = MaterialTheme.typography.bodySmall, color = Color.White, fontSize = 14.sp)
                }
            }

//            Image(
//                painter = painterResource(id = thumbnailRes),
//                contentDescription = null,
//                modifier = Modifier.size(120.dp),
//                contentScale = ContentScale.Crop
//            )
        }
    }
}

@Composable
fun DiscussionCard(username: String, publishedAt: String, title: String){
    OutlinedCard(shape = RoundedCornerShape(4.dp), modifier= Modifier
        .padding(8.dp)
        .fillMaxWidth(), border = BorderStroke(1.dp, PrimaryBrown)) {
        Column{
            Row {

            }
            Text(title, style = MaterialTheme.typography.titleMedium, color = PrimaryGreen, fontSize = 18.sp)
        }
    }
}

@Preview
@Composable
fun VideoCardPreview(){
    VideoCard(
        title = "Title",
        description = "Description",
        videoUrl = "https://www.youtube.com/watch?v=VkCKHgtsQZk",
        thumbnailRes = 1
    )
}
