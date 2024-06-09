package com.cultivitea.frontend

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    private val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, navigate to DetectorScreen
                setContent {
                    CultiviteaApp(startDestination = Screen.Detector.route)
                }
            } else {
                // Permission denied, handle appropriately
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) -> {
                // Permission already granted, set the content
                setContent {
                    CultiviteaApp(startDestination = Screen.Detector.route)
                }
            }
            else -> {
                // Request permission
                cameraPermissionRequest.launch(Manifest.permission.CAMERA)
            }
        }
    }
}
