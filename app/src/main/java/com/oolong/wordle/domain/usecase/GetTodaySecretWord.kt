package com.oolong.wordle.domain.usecase

import com.oolong.wordle.data.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodaySecretWord @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    fun invoke(): Flow<String?> {
        return userPreferencesRepository.getTodaySecretWord()
    }
}