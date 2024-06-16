package com.cultivitea.frontend.helper

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.cultivitea.frontend.viewmodel.MainViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun getFileFromUri(context: Context, uri: Uri): File? {
    val contentResolver = context.contentResolver
    val fileName = getFileName(contentResolver, uri)
    val file = File(context.cacheDir, fileName)
    try {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
    return file
}

@SuppressLint("Range")
fun getFileName(contentResolver: ContentResolver, uri: Uri): String {
    var name = "temp_file"
    val cursor = contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            name = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
    }
    return name
}

fun uploadImage(context: Context, uri: Uri, viewModel: MainViewModel) {
    val file = getFileFromUri(context, uri)
    file?.let {
        val requestFile = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("image", it.name, requestFile)
        viewModel.predict(multipartBody)
    }
}

fun getTimeAgo(createdAt: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")

    try {
        val date = sdf.parse(createdAt)
        val now = Date()

        val diff = now.time - date.time
        val days = diff / (1000 * 60 * 60 * 24)

        return when {
            days > 6 -> {
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                formattedDate.format(date)
            }
            days > 0 -> "${days.toInt()} ${if (days.toInt() == 1) "day" else "days"} ago"
            else -> {
                val seconds = diff / 1000
                val minutes = seconds / 60
                val hours = minutes / 60

                when {
                    hours > 0 -> "${hours.toInt()} ${if (hours.toInt() == 1) "hour" else "hours"} ago"
                    minutes > 0 -> "${minutes.toInt()} ${if (minutes.toInt() == 1) "minute" else "minutes"} ago"
                    else -> "just now"
                }
            }
        }
    } catch (e: ParseException) {
        e.printStackTrace()
        return createdAt
    }
}

fun pluralize(value: Long, unit: String): String {
    return if (value == 1L) "$value $unit" else "$value ${unit}s"
}
