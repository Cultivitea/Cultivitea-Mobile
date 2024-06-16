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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.cultivitea.frontend.ui.nav.Screen
import com.cultivitea.frontend.ui.theme.GrayInput
import com.cultivitea.frontend.ui.theme.NavBrown
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    screenTitle: String,
    onBackClick: () -> Unit = {},
    showLogout: Boolean = false,
    onLogoutClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = screenTitle)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick, colors = IconButtonDefaults.iconButtonColors(
                contentColor = PrimaryGreen
            )) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            if (showLogout) {
                IconButton(onClick = onLogoutClick, colors = IconButtonDefaults.iconButtonColors(
                    contentColor = PrimaryGreen
                )) {
                    Icon(Icons.Filled.Logout, contentDescription = "Logout")
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.White,
            titleContentColor = PrimaryGreen,
        )
    )
}


@Composable
fun ProfileImage(imageUrl: String?) {
    if (imageUrl.isNullOrEmpty()) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier
                .size(300.dp)
                .clip(CircleShape)
                .border(2.dp, PrimaryGreen, CircleShape)
        )
    } else {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(300.dp)
                .clip(CircleShape)
                .border(2.dp, PrimaryGreen, CircleShape)
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Detector,
        Screen.Forum,
        Screen.Profile
    )
    NavigationBar(
        contentColor = PrimaryBrown,
        containerColor = Color.White,

    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryBrown,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.Transparent,
                    indicatorColor = Color.White
                ),
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
