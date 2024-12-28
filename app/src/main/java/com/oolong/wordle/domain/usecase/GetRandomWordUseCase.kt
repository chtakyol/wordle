package com.oolong.wordle.domain.usecase

import com.oolong.wordle.data.WordRepository
import com.oolong.wordle.domain.WordDomainObj
import javax.inject.Inject

class GetRandomWordUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(): WordDomainObj? {
        return wordRepository.getRandomWord()
    }
}