package com.example.kaizenproject.di

import android.content.Context
import com.example.kaizenproject.common.AppCoroutineDispatchers
import com.example.kaizenproject.presentation.mappers.ErrorTypeMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideCoroutineDispatchers() = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )

    @Provides
    fun provideErrorMapper(@ApplicationContext context: Context) = ErrorTypeMapper(context)
}
