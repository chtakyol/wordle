package com.oolong.wordle.domain.usecase

import com.oolong.wordle.data.UserPreferencesRepository
import javax.inject.Inject

class SaveTodaySecretWord @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend fun invoke(word: String) {
        userPreferencesRepository.saveTodaySecretWord(word)
    }
}