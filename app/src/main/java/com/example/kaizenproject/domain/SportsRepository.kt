package com.example.kaizenproject.domain

import com.example.kaizenproject.common.CommonResult
import com.example.kaizenproject.domain.model.SportingEvents
import kotlinx.coroutines.flow.Flow

interface SportsRepository {

    fun observeSports(): Flow<List<SportingEvents>>

    suspend fun getSports() : CommonResult

    suspend fun updateFavorite(eventId: String, isMarkedAsFavorite: Boolean)
}
