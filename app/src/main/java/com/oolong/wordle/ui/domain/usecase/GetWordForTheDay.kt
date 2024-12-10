package com.oolong.wordle.domain.usecase

import com.oolong.wordle.data.UserPreferencesRepository
import com.oolong.wordle.data.WordRepository
import com.oolong.wordle.domain.WordDomainObj
import java.time.LocalDate
import javax.inject.Inject

class GetWordForTheDay @Inject constructor(
    private val wordRepository: WordRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(): WordDomainObj? {

        val currentDay = LocalDate.now()

        return wordRepository.getRandomWord()
    }
}