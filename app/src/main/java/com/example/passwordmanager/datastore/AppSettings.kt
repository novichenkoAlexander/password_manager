package com.example.passwordmanager.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.passwordmanager.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    "${BuildConfig.APPLICATION_ID}_datastore"
)

class AppSettings(context: Context) {

    private val dataStore = context.dataStore

    fun passwordFlow(): Flow<Long> = dataStore.data.map { preferences ->
        preferences[longPreferencesKey(USER_KEY)] ?: 0L
    }

    suspend fun setPassword(password: Long) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(USER_KEY)] = password
        }
    }

    suspend fun getPassword(): Long = passwordFlow().first()

    companion object {
        const val USER_KEY = "USER_KEY"
    }


}