package com.oolong.wordle.domain.usecase

import com.oolong.wordle.data.WordRepository
import com.oolong.wordle.domain.WordDomainObj
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(): List<WordDomainObj> {
        return wordRepository.getWords()
    }
}