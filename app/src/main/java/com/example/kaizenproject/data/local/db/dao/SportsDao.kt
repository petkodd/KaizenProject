package com.example.kaizenproject.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kaizenproject.data.local.db.entities.SportsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SportsDao {

    @Query("SELECT * FROM SportsEntity")
    fun getAllSportEvents(): Flow<List<SportsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSports(sports: List<SportsEntity>) {
        sports.forEach {
            insert(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(details: SportsEntity)

    @Query("UPDATE SportsEntity SET isFavorite = :isMarkedAsFavorite WHERE eventId = :eventId")
    suspend fun updateFavorite(eventId: String, isMarkedAsFavorite: Boolean)
}
