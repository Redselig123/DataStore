package com.example.datastore.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

fun saveThemePreference(context: Context, isDarkMode: Boolean){
    CoroutineScope(Dispatchers.IO).launch{
        val themeKey = booleanPreferencesKey("dark_mode")
        context.dataStore.edit { preferences ->
            preferences[themeKey] = isDarkMode
        }
    }
}
fun getThemePreference(context: Context): Flow<Boolean> {
        val themeKey = booleanPreferencesKey("dark_mode")
    return context.dataStore.data
        .map{ preferences -> preferences[themeKey]?:false }
}