package com.oolong.wordle.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val currentDayKey = stringPreferencesKey("current_day")
    private val todaySecretWord = stringPreferencesKey("today_secret_word")
    private val areWordsCached = booleanPreferencesKey("is_cached")

    fun getCurrentDayAsString(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[currentDayKey]
        }
    }

    suspend fun setCurrentDay(currentDayAsString: String) {
        dataStore.edit { preferences ->
            preferences[currentDayKey] = currentDayAsString
        }
    }

    fun getTodaySecretWord(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[todaySecretWord]
        }
    }

    suspend fun saveTodaySecretWord(word: String) {
        dataStore.edit { preferences ->
            preferences[todaySecretWord] = word
        }
    }

    fun getAreWordsCached(): Flow<Boolean?> {
        return dataStore.data.map { preferences ->
            preferences[areWordsCached]
        }
    }

    suspend fun saveTodaySecretWord(areCached: Boolean) {
        dataStore.edit { preferences ->
            preferences[areWordsCached] = areCached
        }
    }

}