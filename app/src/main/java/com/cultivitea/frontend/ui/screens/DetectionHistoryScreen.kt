package com.cultivitea.frontend.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.cultivitea.frontend.data.api.response.HistoryItem
import com.cultivitea.frontend.ui.composables.CustomAppBar
import com.cultivitea.frontend.ui.composables.HistoryCard
import com.cultivitea.frontend.ui.theme.NavBrown
import com.cultivitea.frontend.viewmodel.MainViewModel

@Composable
fun DetectionHistoryScreen(navController : NavController, viewModel: MainViewModel) {
    var history by remember { mutableStateOf<List<HistoryItem>?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getDetectionHistory()
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomAppBar(screenTitle = "Riwayat", onBackClick = {navController.popBackStack()}, showBack = true)
        },
        content = { paddingValues ->
            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())) {
                history?.forEach { detection ->
                    HistoryCard(detection.history!!)
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    )
    viewModel.histories.observe(LocalLifecycleOwner.current) { item ->
        Log.d("ProfileGet", "Response: $item")
        history = item
    }
}