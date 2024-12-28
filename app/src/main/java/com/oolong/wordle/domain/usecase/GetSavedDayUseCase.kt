package com.oolong.wordle.domain.usecase

import com.oolong.wordle.data.UserPreferencesRepository
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class GetSavedDayUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend fun invoke(): String? {
        return userPreferencesRepository.getCurrentDayAsString().last()
    }
}