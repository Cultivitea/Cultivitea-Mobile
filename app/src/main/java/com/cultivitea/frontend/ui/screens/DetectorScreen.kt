package com.cultivitea.frontend.ui.screens

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.cultivitea.frontend.helper.uploadImage
import com.cultivitea.frontend.ui.composables.CustomAppBar
import com.cultivitea.frontend.ui.theme.PrimaryBrown
import com.cultivitea.frontend.ui.theme.PrimaryGreen
import com.cultivitea.frontend.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun DetectorScreen(viewModel: MainViewModel) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var predictionResult by remember { mutableStateOf<String?>(null) }
    var predictionSuggestion by remember { mutableStateOf<String?>(null) }
    var predictionMessage by remember { mutableStateOf<String?>(null) }
    var predictionError by remember { mutableStateOf<Boolean?>(false) }
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember { ImageCapture.Builder().build() }
    val coroutineScope = rememberCoroutineScope()
    var cameraProviderBound by remember { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUri = it
            isLoading = true
            coroutineScope.launch {
                try {
                    uploadImage(context, it, viewModel)
                } finally {
                    isLoading = false
                }
            }
        }
    }

    LaunchedEffect(cameraProviderBound) {
        if (!cameraProviderBound) {
            val cameraProvider = context.getCameraProvider()
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
            preview.setSurfaceProvider(previewView.surfaceProvider)
            cameraProviderBound = true
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = { CustomAppBar(screenTitle = "Tea Disease Detector") },
        content = { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        contentAlignment = Alignment.TopCenter,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(16.dp)
                    ) {
                        if (imageUri == null) {
                            AndroidView({ previewView }, modifier = Modifier

                                .padding(horizontal = 20.dp, vertical = 16.dp).requiredHeight(150.dp))
                        } else {
                            Box(modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 16.dp)) {
                                Image(
                                    bitmap = loadImageBitmap(context, imageUri!!),
                                    contentDescription = null,

                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    val color = if (imageUri == null) PrimaryGreen else Color.Red

                    OutlinedButton(
                        onClick = {
                            if (imageUri == null) {
                                isLoading = true
                                coroutineScope.launch {
                                    captureImage(imageCapture, context) { uri ->
                                        imageUri = uri
                                        uri?.let {
                                            uploadImage(context, it, viewModel)
                                        }
                                    }
                                }
                            } else {
                                imageUri = null
                                predictionResult = null
                                predictionSuggestion = null
                            }
                        },
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = color
                        ),
                        border = BorderStroke(1.dp, color)
                    ) {
                        Text(
                            text = if (imageUri == null) "Scan" else "Delete",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 18.sp,
                                color = color,
                                fontWeight = FontWeight.Normal
                            ),
                        )
                    }

                    // Button to pick an image from the gallery
                    OutlinedButton(
                        onClick = {
                            galleryLauncher.launch("image/*")
                        },
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = PrimaryGreen
                        ),
                        border = BorderStroke(1.dp, PrimaryGreen)
                    ) {
                        Text(
                            text = "Pick from Gallery",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 18.sp,
                                color = PrimaryGreen,
                                fontWeight = FontWeight.Normal
                            ),
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "Scan Result",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 24.sp, color = PrimaryBrown)
                        )
                        if (predictionResult != null && predictionSuggestion != null) {
                            val resultColor = if (predictionResult == "Tea Healthy") PrimaryGreen else Color.Red
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(
                                text = "$predictionResult",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = resultColor
                                ),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = "$predictionSuggestion",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                        if (predictionError!!) {
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(
                                text = "$predictionMessage",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                ),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }

                if (isLoading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    )

    viewModel.uploadResult.observe(LocalLifecycleOwner.current) { response ->
        Log.d("DetectorScreen", "Response: $response")
        isLoading = false
        predictionResult = response?.data?.result
        predictionSuggestion = response?.data?.suggestion
        predictionError = response?.error
        predictionMessage = response?.message
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
                outputFileResults.savedUri?.let { onImageCaptured(it) }
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
