package com.example.kaizenproject.di

import android.content.Context
import android.os.Debug
import androidx.room.Room
import com.example.kaizenproject.data.local.db.KaizenProjectDatabase
import com.example.kaizenproject.data.local.db.KaizenProjectRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    private val databaseName = "kaizen-challenge.db"

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): KaizenProjectRoomDatabase {
        val builder = Room.databaseBuilder(context, KaizenProjectRoomDatabase::class.java, databaseName)
            .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }

    @Provides
    fun bindSportsDao(db: KaizenProjectDatabase) = db.sportsDao()
}
