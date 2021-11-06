package com.xda.sa2ration.repository

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Sa2rationRepository @Inject constructor(@ApplicationContext appContext: Context) {
    private val Context.dataStore by preferencesDataStore("sa2ration")
    private val dataStore = appContext.dataStore

    fun<T> readData(key : Preferences.Key<T>, defaultValue : T): Flow<T> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[key] ?: defaultValue
        }
        .distinctUntilChanged()

    suspend fun<T> storeData(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    companion object PreferencesKey {
        val SATURATION_VALUE = floatPreferencesKey("saturation_value")
        val ENABLE_DCI = booleanPreferencesKey("enable_dci")
        val APPLY_ON_BOOT = booleanPreferencesKey("apply_on_boot")
    }
}