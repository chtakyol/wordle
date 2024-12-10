package com.oolong.wordle.ui

import android.app.Application
import android.content.Context
import android.util.Log
import com.oolong.wordle.domain.WordDomainObj
import com.oolong.wordle.domain.usecase.GetRandomWordUseCase
import com.oolong.wordle.domain.usecase.GetSavedDayUseCase
import com.oolong.wordle.domain.usecase.GetWordsUseCase
import com.oolong.wordle.domain.usecase.InsertWordUseCase
import com.oolong.wordle.domain.usecase.ReadWordFileUseCase
import com.oolong.wordle.domain.usecase.SaveCurrentDayUseCase
import com.oolong.wordle.domain.usecase.SaveTodaySecretWord
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var readWordFileUseCase: ReadWordFileUseCase

    @Inject
    lateinit var getWordsUseCase: GetWordsUseCase

    @Inject
    lateinit var insertWordsUseCase: InsertWordUseCase

    @Inject
    lateinit var getSavedDayUseCase: GetSavedDayUseCase

    @Inject
    lateinit var saveCurrentDayUseCase: SaveCurrentDayUseCase

    @Inject
    lateinit var getRandomWordUseCase: GetRandomWordUseCase

    @Inject
    lateinit var saveTodaySecretWord: SaveTodaySecretWord

    override fun onCreate() {
        super.onCreate()
        // todo check if words are cached or not
        saveWordsToRoomDatabase()

        CoroutineScope(Dispatchers.IO).launch {
            val savedDay = getSavedDayUseCase.invoke()
            if (savedDay != LocalTime.now().toString()) {
                saveCurrentDayUseCase
                val secretWord = getRandomWordUseCase()
                secretWord?.let { saveTodaySecretWord.invoke(it.word) }

            }
        }
    }

    private fun saveWordsToRoomDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val words = getWordsUseCase.invoke()
            if (words.isEmpty()){
                val wordsFromJson = readWordFileUseCase.execute()
                wordsFromJson?.let { wordList ->
                    wordList.forEach {
                        insertWordsUseCase(WordDomainObj(word = it, isUsed = false))
                    }
                }
            }
        }
    }
}
