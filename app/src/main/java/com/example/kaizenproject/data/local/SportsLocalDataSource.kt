package com.example.kaizenproject.data.local

import com.example.kaizenproject.data.local.db.entities.SportsEntity
import kotlinx.coroutines.flow.Flow

interface SportsLocalDataSource {
    fun observeSports(): Flow<List<SportsEntity>>
    suspend fun insertSports(sports: List<SportsEntity>)
    suspend fun updateFavorite(eventId: String, isMarkedAsFavorite: Boolean)
}
