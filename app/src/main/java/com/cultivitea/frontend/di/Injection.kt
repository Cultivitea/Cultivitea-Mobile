package com.cultivitea.frontend.di

import android.content.Context
import com.cultivitea.frontend.data.api.pref.UserPreference
import com.cultivitea.frontend.data.api.pref.dataStore
import com.cultivitea.frontend.data.api.remote.ApiConfig
import com.cultivitea.frontend.data.repository.Repository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {   fun provideRepository(context: Context): Repository {
    val pref = UserPreference.getInstance(context.dataStore)
    val user = runBlocking { pref.getUser().first() }
    val apiService = ApiConfig.getApiService(user.token)
    return Repository.getInstance(apiService, pref)
}
}