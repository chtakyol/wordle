package com.oolong.wordle

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.oolong.wordle.data.WordDao
import com.oolong.wordle.data.WordDatabase
import com.oolong.wordle.domain.usecase.ReadWordFileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    fun provideReadWordsFromJsonUseCase(context: Context): ReadWordFileUseCase {
        return ReadWordFileUseCase(context)
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): WordDatabase {
        return Room.databaseBuilder(
            context,
            WordDatabase::class.java,
            "word_database"
        ).build()
    }

    @Provides
    fun provideWordDao(database: WordDatabase): WordDao {
        return database.wordDao()
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile("user_prefs") }
        )
    }
}