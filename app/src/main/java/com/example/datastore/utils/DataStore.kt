package com.example.datastore.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensión para DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


private val USER_NAME_KEY = stringPreferencesKey("user_name")
private val USER_AGE_KEY = intPreferencesKey("user_age")
private val THEME_KEY = booleanPreferencesKey("dark_mode")


suspend fun saveUserName(context: Context, userName: String) {
    context.dataStore.edit { preferences ->
        preferences[USER_NAME_KEY] = userName
    }
}

suspend fun saveUserAge(context: Context, age: Int) {
    context.dataStore.edit { preferences ->
        preferences[USER_AGE_KEY] = age
    }
}

suspend fun saveThemePreference(context: Context, isDarkMode: Boolean) {
    context.dataStore.edit { preferences ->
        preferences[THEME_KEY] = isDarkMode
    }
}

fun getUserName(context: Context): Flow<String> {
    return context.dataStore.data.map { preferences ->
        preferences[USER_NAME_KEY] ?: "Usuario desconocido"
    }
}

fun getUserAge(context: Context): Flow<Int> {
    return context.dataStore.data.map { preferences ->
        preferences[USER_AGE_KEY] ?: 0
    }
}

fun getThemePreference(context: Context): Flow<Boolean> {
    return context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: false
    }
}
