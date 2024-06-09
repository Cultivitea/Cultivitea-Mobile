package com.cultivitea.frontend.ui.screens

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.cultivitea.frontend.ui.theme.PrimaryGreen
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun DetectorScreen() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }
    val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp)
        ) {
            if (imageUri == null) {
                AndroidView({ previewView }, modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp) )
            } else {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp)) {
                    Image(
                        bitmap = loadImageBitmap(context, imageUri!!),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(horizontal = 8.dp)
                            .clickable {
                                imageUri = null
                            }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {
                coroutineScope.launch {
                    captureImage(imageCapture, context) { uri ->
                        imageUri = uri
                    }
                }
            },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth().padding(horizontal = 20.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = PrimaryGreen // Primary green color for text and outline
            ),
            border = BorderStroke(1.dp, PrimaryGreen)
        ) {
            Text(text = "Scan",
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 18.sp, color = PrimaryGreen, fontWeight = FontWeight.Normal),)
        }
    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

private fun captureImage(imageCapture: ImageCapture, context: Context, onImageCaptured: (Uri) -> Unit) {
    val name = "Cultivitea_${System.currentTimeMillis()}.jpeg"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
        }
    }
    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        .build()
    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                outputFileResults.savedUri?.let {
                    onImageCaptured(it)
                }
            }

            override fun onError(exception: ImageCaptureException) {
                println("Failed $exception")
            }
        })
}

@Composable
private fun loadImageBitmap(context: Context, uri: Uri): ImageBitmap {
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(uri) {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(uri)
            .allowHardware(false)
            .build()
        val result = (loader.execute(request) as SuccessResult).drawable
        bitmap = (result.toBitmap()).asImageBitmap()
    }
    return bitmap ?: ImageBitmap(1, 1)
}
