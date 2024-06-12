package com.cultivitea.frontend.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cultivitea.frontend.data.api.response.PredictionResponse
import com.cultivitea.frontend.data.repository.Repository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.lang.Exception

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _uploadResult = MutableLiveData<PredictionResponse>()
    val uploadResult: LiveData<PredictionResponse> = _uploadResult

    fun predict(file: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                Log.d("MainViewModel", "Predicting image...")
                val response = repository.predict(file)
                Log.d("MainViewModel", "Prediction result: ${response.data}")
                _uploadResult.postValue(response)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error posting stories: ${e.message}", e)
            }
        }
    }
}
