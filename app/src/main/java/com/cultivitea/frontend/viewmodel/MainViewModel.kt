package com.cultivitea.frontend.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.cultivitea.frontend.data.api.pref.UserModel
import com.cultivitea.frontend.data.api.response.PredictionResponse
import com.cultivitea.frontend.data.api.response.LoginResponse
import com.cultivitea.frontend.data.api.response.SignUpResponse
import com.cultivitea.frontend.data.repository.Repository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

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
                Log.e("MainViewModel", "Error predicting image: ${e.message}", e)
            }
        }
    }

    fun loginUser(email: String, password: String, onLoginResult: (LoginResponse?, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val loginResponse = repository.loginUser(email, password)
                onLoginResult(loginResponse, null)
            } catch (e: HttpException) {
                val loginResponse = parseLoginHttpException(e)
                val errorMessage = parseHttpException(e)
                onLoginResult(loginResponse, errorMessage)
            } catch (e: IOException) {
                onLoginResult(null, "Network error: ${e.message}")
            } catch (e: Exception) {
                onLoginResult(null, "Unexpected error: ${e.message}")
            }
        }
    }

    private fun parseHttpException(e: HttpException): String {
        val errorBody = e.response()?.errorBody()?.string()
        return if (errorBody != null) {
            try {
                JSONObject(errorBody).getString("message")
            } catch (jsonException: JSONException) {
                "HTTP ${e.code()}: ${e.message()}"
            }
        } else {
            "HTTP ${e.code()}: ${e.message()}"
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun registerUser(username: String, email: String, password: String, onRegisterResult: (SignUpResponse?, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val signUpResponse = repository.register(username, email, password)
                onRegisterResult(signUpResponse, null)
            } catch (e: HttpException) {
                val signUpResponse = parseSignUpHttpException(e)
                if (signUpResponse != null) {
                    onRegisterResult(signUpResponse, null)
                } else {
                    val errorMessage = "HTTP ${e.code()}: ${e.message()}"
                    onRegisterResult(null, errorMessage)
                }
            } catch (e: IOException) {
                onRegisterResult(null, "Network error: ${e.message}")
            } catch (e: Exception) {
                onRegisterResult(null, "Unexpected error: ${e.message}")
            }
        }
    }

    private fun parseSignUpHttpException(e: HttpException): SignUpResponse? {
        val errorBody = e.response()?.errorBody()?.string()
        return if (errorBody != null) {
            try {
                Gson().fromJson(errorBody, SignUpResponse::class.java)
            } catch (jsonException: JSONException) {
                null
            }
        } else {
            null
        }
    }

    private fun parseLoginHttpException(e: HttpException): LoginResponse? {
        val errorBody = e.response()?.errorBody()?.string()
        return if (errorBody != null) {
            try {
                Gson().fromJson(errorBody, LoginResponse::class.java)
            } catch (jsonException: JSONException) {
                null
            }
        } else {
            null
        }
    }
}
