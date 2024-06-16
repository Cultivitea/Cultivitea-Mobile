package com.cultivitea.frontend.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.cultivitea.frontend.data.api.pref.UserModel
import com.cultivitea.frontend.data.api.response.AddDiscussionResponse
import com.cultivitea.frontend.data.api.response.CommentItem
import com.cultivitea.frontend.data.api.response.DiscussionItem
import com.cultivitea.frontend.data.api.response.EditProfileResponse
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

    private val _discussions = MutableLiveData<List<DiscussionItem>>()
    val discussions: LiveData<List<DiscussionItem>> = _discussions

    private val _uploadResult = MutableLiveData<PredictionResponse>()
    val uploadResult: LiveData<PredictionResponse> = _uploadResult

    private val _comments = MutableLiveData<List<CommentItem>>()
    val comments: LiveData<List<CommentItem>> = _comments

    private var token = ""
    private var id = ""

    init {
        viewModelScope.launch {
            repository.getSession().collect { user ->
                token = user.token
                id = user.uid
            }
        }
    }

    fun predict(file: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                Log.d("MainViewModel", "Predicting image...")
                val response = repository.predict(file)
                Log.d("MainViewModel", "Prediction result: ${response.data}")
                _uploadResult.postValue(response)
            } catch (e: HttpException) {
                Log.e("MainViewModel", "Error predicting image: ${e.message}", e)
                val response = parsePredictException(e)
                if (response != null){
                    _uploadResult.postValue(response!!)
                } else {
                    _uploadResult.postValue(PredictionResponse(error = true, message = e.message(), null))
                }
            }
        }
    }

    fun editProfile(
        name: String,
        phoneNumber: String,
        dateOfBirth: String,
        image: MultipartBody.Part?,
        onEditResult: (EditProfileResponse?, String?) -> Unit
    ) {

        viewModelScope.launch {
            try {
                val editResponse = repository.editProfile(token, id, name, phoneNumber, dateOfBirth, image)
//                _editProfileResult.postValue(editResponse)
                onEditResult(editResponse, null)
            } catch (e: HttpException) {
                val errorMessage = parseHttpException(e)
                onEditResult(null, errorMessage)
            } catch (e: IOException) {
                onEditResult(null, "Network error: ${e.message}")
            } catch (e: Exception) {
                onEditResult(null, "Unexpected error: ${e.message}")
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
            try{
                repository.saveSession(user)
            } finally {
                token = user.token
                id = user.uid
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        Log.d("MainViewModel", "Token: $token, ID: $id")
        Log.d("MainViewModel", "Getting session...")
        if (token.isNotEmpty() && id.isNotEmpty()){
            getProfile(token, id)
        }
        Log.d("MainViewModel", "Session result: ${repository.getSession().asLiveData()}")
        return repository.getSession().asLiveData()
    }


     fun getProfile(token: String, id: String)  {
        viewModelScope.launch {
            try {
                Log.d("MainViewModel", "Getting profile...")
                val response = repository.getProfile(token, id)
                Log.d("MainViewModel", "Profile result: ${response.userCredential}")
                if (response?.userCredential != null){
                    val user = UserModel(
                        token,
                        response.userCredential.uid!!,
                        response.userCredential.phoneNumber!!,
                        response.userCredential.imageUrl!!,
                        response.userCredential.dateOfBirth!!,
                        response.userCredential.email!!,
                        response.userCredential.username!!,
                        true
                    )
                    saveSession(user)
                }
            } catch (e: HttpException) {
                Log.e("MainViewModel", "Error saving session: ${e.message}", e)

            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            clearUserData()
        }
    }

    private fun clearUserData() {
        token = ""
        id = ""
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

    fun getAllDiscussions() {
        viewModelScope.launch {
            try {
                val response = repository.getDiscussions()
                if (!response.error!!) {
                    _discussions.postValue(response.data!!)
                } else {
                    Log.e("MainViewModel", "Error fetching discussions: ${response.message}")
                }
            } catch (e: HttpException) {
                Log.e("MainViewModel", "HTTP error fetching discussions: ${e.message}", e)
            } catch (e: IOException) {
                Log.e("MainViewModel", "Network error fetching discussions: ${e.message}", e)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Unexpected error fetching discussions: ${e.message}", e)
            }
        }
    }

    fun getComments(discussionId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getDiscussionsComments(discussionId)
                if (!response.error!!) {
                    _comments.postValue(response.data!!)
                } else {
                    Log.e("MainViewModel", "Error fetching comments: ${response.message}")
                }
            } catch (e: HttpException) {
                Log.e("MainViewModel", "HTTP error fetching comments: ${e.message}", e)
            } catch (e: IOException) {
                Log.e("MainViewModel", "Network error fetching comments: ${e.message}", e)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Unexpected error fetching comments: ${e.message}", e)
            }
        }
    }

    fun addComment(discussionId: String, comment: String) {
        viewModelScope.launch {
            try {
                val response = repository.addComment(discussionId, comment)
                if (!response.error!!) {
                    getComments(discussionId)
                } else {
                    Log.e("MainViewModel", "Error adding comment: ${response.message}")
                }
            } catch (e: HttpException) {
                Log.e("MainViewModel", "HTTP error adding comment: ${e.message}", e)
            } catch (e: IOException) {
                Log.e("MainViewModel", "Network error adding comment: ${e.message}", e)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Unexpected error adding comment: ${e.message}", e)
            }
        }
    }

    fun addDiscussion(title: String, content: String, onAddResult: (AddDiscussionResponse?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.addDiscussion(title, content)
                onAddResult(response)
            } catch (e: HttpException) {
                Log.e("MainViewModel", "HTTP error adding comment: ${e.message}", e)
            } catch (e: IOException) {
                Log.e("MainViewModel", "Network error adding comment: ${e.message}", e)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Unexpected error adding comment: ${e.message}", e)
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

    private fun parsePredictException(e: HttpException): PredictionResponse? {
        val errorBody = e.response()?.errorBody()?.string()
        return if (errorBody != null) {
            try {
                Gson().fromJson(errorBody, PredictionResponse::class.java)
            } catch (jsonException: JSONException) {
                null
            }
        } else {
            null
        }
    }
}
