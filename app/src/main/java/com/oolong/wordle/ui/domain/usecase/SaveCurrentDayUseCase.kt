package com.oolong.wordle.domain.usecase

import com.oolong.wordle.data.UserPreferencesRepository
import java.time.LocalTime
import javax.inject.Inject

class SaveCurrentDayUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend fun invoke() {
        val currentDay = LocalTime.now()
        userPreferencesRepository.setCurrentDay(currentDay.toString())
    }
}