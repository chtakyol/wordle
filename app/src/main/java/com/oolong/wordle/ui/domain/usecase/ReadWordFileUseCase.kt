package com.oolong.wordle.domain.usecase

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

const val fileName = "words.json"
class ReadWordFileUseCase @Inject constructor(private val context: Context) {
    suspend fun execute(): List<String>? = withContext(Dispatchers.IO) {
        return@withContext try {
            val inputStream = context.assets.open(fileName)
            val reader = InputStreamReader(inputStream)
            val listType = object : TypeToken<List<String>>() {}.type
            val wordsList: List<String> = Gson().fromJson(reader, listType)
            inputStream.close()
            wordsList
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}