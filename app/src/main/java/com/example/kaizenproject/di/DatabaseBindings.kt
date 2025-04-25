package com.example.kaizenproject.di

import com.example.kaizenproject.data.local.db.KaizenProjectDatabase
import com.example.kaizenproject.data.local.db.KaizenProjectRoomDatabase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModuleBinds {

    @Binds
    abstract fun bindKaizenProjectDatabase(db: KaizenProjectRoomDatabase): KaizenProjectDatabase
}

