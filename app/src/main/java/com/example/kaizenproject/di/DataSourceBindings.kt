package com.example.kaizenproject.di

import com.example.kaizenproject.data.local.SportsLocalDataSource
import com.example.kaizenproject.data.local.SportsLocalDataSourceImpl
import com.example.kaizenproject.data.remote.SportsRemoteDataSource
import com.example.kaizenproject.data.remote.SportsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceBindings {

    @Binds
    abstract fun bindRemoteDataSource(source: SportsRemoteDataSourceImpl): SportsRemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(source: SportsLocalDataSourceImpl): SportsLocalDataSource
}
