package com.cultivitea.frontend.data.api.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return getSession()
    }

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
            preferences[USERNAME_KEY] = user.username
            preferences[IMAGE_URL_KEY] = user.imageUrl
            preferences[DATE_OF_BIRTH_KEY] = user.dateOfBirth
            preferences[PHONE_NUMBER_KEY] = user.phoneNumber
            preferences[UID_KEY] = user.uid
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
//            UserModel(
//                preferences[EMAIL_KEY] ?: "",
//                preferences[TOKEN_KEY] ?: "",
//
//                preferences[IS_LOGIN_KEY] ?: false
//            )
            UserModel(
                preferences[TOKEN_KEY] ?: "",
                preferences[UID_KEY] ?: "",
                preferences[PHONE_NUMBER_KEY] ?: "",
                preferences[IMAGE_URL_KEY] ?: "",
                preferences[DATE_OF_BIRTH_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[USERNAME_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false)
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val IMAGE_URL_KEY = stringPreferencesKey("imageUrl")
        private val DATE_OF_BIRTH_KEY = stringPreferencesKey("dateOfBirth")
        private val PHONE_NUMBER_KEY = stringPreferencesKey("phoneNumber")
        private val UID_KEY = stringPreferencesKey("uid")


        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}