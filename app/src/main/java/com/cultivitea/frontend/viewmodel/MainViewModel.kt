package com.cultivitea.frontend.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.cultivitea.frontend.data.api.pref.UserModel
import com.cultivitea.frontend.data.api.response.*
import com.cultivitea.frontend.data.repository.Repository
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

    private val _histories = MutableLiveData<List<HistoryItem>>()
    val histories: LiveData<List<HistoryItem>> = _histories

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

    private suspend fun <T> handleHttpRequest(
        request: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val response = request()
            onSuccess(response)
        } catch (e: HttpException) {
            onError(parseHttpException(e))
        } catch (e: IOException) {
            onError("Network error: ${e.message}")
        } catch (e: Exception) {
            onError("Unexpected error: ${e.message}")
        }
    }

    private fun parseHttpException(e: HttpException): String {
        val errorBody = e.response()?.errorBody()?.string()
        return if (errorBody != null) {
            try {
                val json = JSONObject(errorBody)
                val message = json.getString("message")
                val details = json.optJSONObject("details")
                if (details != null) {
                    val detailMessages = details.keys().asSequence().map { key ->
                        "$key: ${details.getString(key)}"
                    }.joinToString(", ")
                    "$message - $detailMessages"
                } else {
                    message
                }
            } catch (jsonException: JSONException) {
                "HTTP ${e.code()}: ${e.message()}"
            }
        } else {
            "HTTP ${e.code()}: ${e.message()}"
        }
    }

    fun predict(file: MultipartBody.Part) {
        viewModelScope.launch {
            handleHttpRequest(
                request = { repository.predict(file) },
                onSuccess = { response ->
                    Log.d("MainViewModel", "Prediction result: ${response.data}")
                    _uploadResult.postValue(response)
                },
                onError = { message ->
                    Log.e("MainViewModel", "Error predicting image: $message")
                    _uploadResult.postValue(PredictionResponse(error = true, message = message, null))
                }
            )
        }
    }

    fun getDetectionHistory() {
        viewModelScope.launch {
            handleHttpRequest(
                request = { repository.getDetectionHistory() },
                onSuccess = { response ->
                    Log.d("MainViewModel", "Detection history result: ${response.data}")
                    _histories.postValue(response.data!!)
                },
                onError = { message -> Log.e("MainViewModel", "Error fetching detection history: $message") }
            )
        }
    }

    fun editProfile(
        name: String, phoneNumber: String, dateOfBirth: String, image: MultipartBody.Part?,
        onEditResult: (EditProfileResponse?, String?) -> Unit
    ) {
        viewModelScope.launch {
            handleHttpRequest(
                request = { repository.editProfile(token, id, name, phoneNumber, dateOfBirth, image) },
                onSuccess = { response -> onEditResult(response, null) },
                onError = { message -> onEditResult(null, message) }
            )
        }
    }

    fun getProfile(token: String, id: String) {
        viewModelScope.launch {
            handleHttpRequest(
                request = { repository.getProfile(token, id) },
                onSuccess = { response ->
                    Log.d("MainViewModel", "Profile result: ${response.userCredential}")
                    response.userCredential?.let {
                        val user = UserModel(
                            token, it.uid!!, it.phoneNumber!!, it.imageUrl!!, it.dateOfBirth!!, it.email!!, it.username!!, true
                        )
                        saveSession(user)
                    }
                },
                onError = { message -> Log.e("MainViewModel", "Error getting profile: $message") }
            )
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            try {
                repository.saveSession(user)
            } finally {
                token = user.token
                id = user.uid
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        Log.d("MainViewModel", "Token: $token, ID: $id")
        if (token.isNotEmpty() && id.isNotEmpty()) {
            getProfile(token, id)
        }
        return repository.getSession().asLiveData()
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

    fun loginUser(email: String, password: String, onLoginResult: (LoginResponse?, String?) -> Unit) {
        viewModelScope.launch {
            handleHttpRequest(
                request = { repository.loginUser(email, password) },
                onSuccess = { response -> onLoginResult(response, null) },
                onError = { message -> onLoginResult(null, message) }
            )
        }
    }

    fun registerUser(username: String, email: String, password: String, onRegisterResult: (SignUpResponse?, String?) -> Unit) {
        viewModelScope.launch {
            handleHttpRequest(
                request = { repository.register(username, email, password) },
                onSuccess = { response -> onRegisterResult(response, null) },
                onError = { message -> onRegisterResult(null, message) }
            )
        }
    }

    fun getAllDiscussions() {
        viewModelScope.launch {
            handleHttpRequest(
                request = { repository.getDiscussions() },
                onSuccess = { response ->
                    if (!response.error!!) {
                        _discussions.postValue(response.data!!)
                    } else {
                        Log.e("MainViewModel", "Error fetching discussions: ${response.message}")
                    }
                },
                onError = { message -> Log.e("MainViewModel", "Error fetching discussions: $message") }
            )
        }
    }

    fun getComments(discussionId: String) {
        viewModelScope.launch {
            handleHttpRequest(
                request = { repository.getDiscussionsComments(discussionId) },
                onSuccess = { response ->
                    if (!response.error!!) {
                        _comments.postValue(response.data!!)
                    } else {
                        Log.e("MainViewModel", "Error fetching comments: ${response.message}")
                    }
                },
                onError = { message -> Log.e("MainViewModel", "Error fetching comments: $message") }
            )
        }
    }

    fun addComment(discussionId: String, comment: String) {
        viewModelScope.launch {
            handleHttpRequest(
                request = { repository.addComment(discussionId, comment) },
                onSuccess = { response ->
                    if (!response.error!!) {
                        getComments(discussionId)
                    } else {
                        Log.e("MainViewModel", "Error adding comment: ${response.message}")
                    }
                },
                onError = { message -> Log.e("MainViewModel", "Error adding comment: $message") }
            )
        }
    }

    fun addDiscussion(title: String, content: String, onAddResult: (AddDiscussionResponse?) -> Unit) {
        viewModelScope.launch {
            handleHttpRequest(
                request = { repository.addDiscussion(title, content) },
                onSuccess = { response -> onAddResult(response) },
                onError = { message -> Log.e("MainViewModel", "Error adding discussion: $message") }
            )
        }
    }
}
