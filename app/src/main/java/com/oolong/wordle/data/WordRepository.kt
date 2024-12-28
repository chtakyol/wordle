package com.oolong.wordle.data

import com.oolong.wordle.domain.WordDomainObj
import com.oolong.wordle.domain.toEntity
import javax.inject.Inject

class WordRepository @Inject constructor(
    private val wordDao: WordDao
){
    suspend fun insertWord(wordEntity: WordDomainObj) {
        wordDao.insertDao(wordEntity.toEntity())
    }

    suspend fun getRandomWord(): WordDomainObj? {
        return wordDao.getRandomWord()?.toDomain()
    }

    suspend fun getWords(): List<WordDomainObj> {
        return wordDao.getWords().map { it.toDomain() }
    }
}