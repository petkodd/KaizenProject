package com.example.kaizenproject.di

import com.example.kaizenproject.data.KaizenApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://ios-kaizen.github.io/MockSports/"

    @Singleton
    @Provides
    fun provideKaizenApi(): KaizenApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(provideGsonConverterFactory())
            .client(provideOkHttpClient())
            .build()
            .create(KaizenApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create(
        GsonBuilder().create())

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectionPool(ConnectionPool(3, 2, TimeUnit.MINUTES))
        .dispatcher(
            Dispatcher().apply {
                maxRequestsPerHost = 15
            }
        )
        .build()
}
