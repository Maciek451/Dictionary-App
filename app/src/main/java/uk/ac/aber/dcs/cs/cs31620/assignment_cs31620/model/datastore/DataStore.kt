package uk.ac.aber.dcs.cs.cs31620.assignment_cs31620.model.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserData")
    }

    fun getString(key: String): Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[stringPreferencesKey(key)] ?: ""
        }

    suspend fun saveString(name: String, key: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = name
        }
    }

    fun getInt(key: String): Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[intPreferencesKey(key)] ?: 0
        }

    suspend fun saveInt(value: Int, key: String) {
        context.dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }
}