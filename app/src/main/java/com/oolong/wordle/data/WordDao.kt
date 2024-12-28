package com.oolong.wordle.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDao {
    @Insert
    suspend fun insertDao(wordEntity: WordEntity)

    @Query("SELECT * FROM word_table WHERE id IN (SELECT id FROM word_table ORDER BY RANDOM() LIMIT 1)")
    suspend fun getRandomWord(): WordEntity?

    @Query("SELECT * FROM word_table")
    suspend fun getWords(): List<WordEntity>
}