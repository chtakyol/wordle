package com.oolong.wordle.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oolong.wordle.domain.WordDomainObj

@Entity(tableName = "word_table")
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "isUsed") val isUsed: Boolean
)

fun WordEntity.toDomain(): WordDomainObj {
    return WordDomainObj(
        word = word,
        isUsed = isUsed
    )
}
