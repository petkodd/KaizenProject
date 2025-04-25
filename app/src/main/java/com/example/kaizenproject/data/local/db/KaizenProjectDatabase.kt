package com.example.kaizenproject.data.local.db

import com.example.kaizenproject.data.local.db.dao.SportsDao

interface KaizenProjectDatabase {
    fun sportsDao(): SportsDao
}
