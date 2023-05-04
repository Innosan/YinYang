package com.example.yinyang.ui.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class UserPreferences(private val context: Context) {
    companion object {
        private val NAME = stringPreferencesKey("name")
        private val EMAIL = stringPreferencesKey("email")
        private val PHONE = stringPreferencesKey("phone")
    }

    val name: Flow<String?> = context.userDataStore.data
        .map { preferences -> preferences[NAME] }

    val email: Flow<String?> = context.userDataStore.data
        .map { preferences -> preferences[EMAIL] }

    suspend fun setName(name: String) {
        context.userDataStore.edit { preferences ->
            preferences[NAME] = name
        }
    }

    suspend fun setEmail(email: String, context: Context) {
        context.userDataStore.edit { preferences ->
            preferences[EMAIL] = email
        }
    }
}