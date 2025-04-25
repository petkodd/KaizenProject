package com.example.kaizenproject.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kaizenproject.data.local.db.entities.SportsEntity

@Database(entities = [SportsEntity::class], version = 1)
abstract class KaizenProjectRoomDatabase : RoomDatabase(), KaizenProjectDatabase
