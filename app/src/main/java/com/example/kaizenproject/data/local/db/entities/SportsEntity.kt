package com.example.kaizenproject.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SportsEntity(
    @PrimaryKey
    @ColumnInfo(name = "eventId") var eventId: String,
    @ColumnInfo(name = "sportId") val sportId: String,
    @ColumnInfo(name = "sportName") val sportName: String,
    @ColumnInfo(name = "eventName") val eventName: String,
    @ColumnInfo(name = "eventStartDate") val eventStartDate: Int,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean = false
)
