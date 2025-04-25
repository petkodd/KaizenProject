package com.example.kaizenproject.data.local

import com.example.kaizenproject.data.local.SportsLocalDataSource
import com.example.kaizenproject.data.local.db.dao.SportsDao
import com.example.kaizenproject.data.local.db.entities.SportsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SportsLocalDataSourceImpl @Inject constructor(
    private val sportsDao: SportsDao
) : SportsLocalDataSource {

    override fun observeSports(): Flow<List<SportsEntity>> = sportsDao.getAllSportEvents()

    override suspend fun insertSports(sports: List<SportsEntity>) = sportsDao.insertSports(sports)

    override suspend fun updateFavorite(eventId: String, isMarkedAsFavorite: Boolean) =
        sportsDao.updateFavorite(
            eventId = eventId,
            isMarkedAsFavorite = isMarkedAsFavorite
        )
}
