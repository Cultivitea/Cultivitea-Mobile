package com.cultivitea.frontend

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.content.ContextCompat
import com.cultivitea.frontend.viewmodel.MainViewModel
import com.cultivitea.frontend.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                setContent {
                    CultiviteaApp(viewModel = viewModel, startDestination = Screen.Profile.route)
                }
            } else {
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                setContent {
                    CultiviteaApp(viewModel = viewModel, startDestination = Screen.Login.route)
                }
            } else {
                requestCameraPermission()
            }
        }
    }

    private fun requestCameraPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) -> {
                setContent {
                    CultiviteaApp(viewModel = viewModel, startDestination = Screen.Profile.route)
                }
            }
            else -> {
                cameraPermissionRequest.launch(Manifest.permission.CAMERA)
            }
        }
    }
}
