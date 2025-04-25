package com.example.kaizenproject.data.repository

import com.example.kaizenproject.domain.SportsRepository
import com.example.kaizenproject.domain.model.SportingEvents
import com.example.kaizenproject.common.CommonResult
import com.example.kaizenproject.data.local.SportsLocalDataSource
import com.example.kaizenproject.data.mappers.mapToErrorType
import com.example.kaizenproject.data.mappers.mapToSportingEvents
import com.example.kaizenproject.data.mappers.mapToSportsEntities
import com.example.kaizenproject.data.remote.SportsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SportsRepositoryImpl @Inject constructor(
    private val remoteDataSource: SportsRemoteDataSource,
    private val localDataSource: SportsLocalDataSource,

    ) : SportsRepository {

    override fun observeSports(): Flow<List<SportingEvents>> =
        localDataSource.observeSports().map { it.mapToSportingEvents() }

    override suspend fun getSports() : CommonResult = runCatching {
        val sports = remoteDataSource.getSports().mapToSportsEntities()
        localDataSource.insertSports(sports)
    }.fold(
        onSuccess = { CommonResult.Success },
        onFailure = { CommonResult.Error(it.mapToErrorType()) }
    )

    override suspend fun updateFavorite(eventId: String, isMarkedAsFavorite: Boolean) =
        localDataSource.updateFavorite(
            eventId = eventId,
            isMarkedAsFavorite = isMarkedAsFavorite
        )
}
