package com.oolong.wordle.domain

import com.oolong.wordle.data.WordEntity

data class WordDomainObj(
    val word: String,
    val isUsed: Boolean
)

fun WordDomainObj.toEntity(): WordEntity {
    return WordEntity(
        word = word,
        isUsed = isUsed
    )
}