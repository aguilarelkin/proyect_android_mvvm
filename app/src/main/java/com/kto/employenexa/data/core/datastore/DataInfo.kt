package com.kto.employenexa.data.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class DataInfo @Inject constructor(@ApplicationContext context: Context) {

    private val settingsDataStore = context.dataStore

    companion object {
        const val TOKEN = "token"
    }

    suspend fun saveToken(value: String) {
        settingsDataStore.edit { preference ->
            preference[stringPreferencesKey(TOKEN)] = value
        }
    }

    fun getSettings(): Flow<String?> {
        return settingsDataStore.data.map { preferences ->
            preferences[stringPreferencesKey(TOKEN)]
        }
    }

}