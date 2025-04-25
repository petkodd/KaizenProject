package com.example.kaizenproject.di

import com.example.kaizenproject.domain.SportsRepository
import com.example.kaizenproject.data.repository.SportsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryBinds {

    @Binds
    abstract fun bindSportsRepository(repository: SportsRepositoryImpl): SportsRepository
}
