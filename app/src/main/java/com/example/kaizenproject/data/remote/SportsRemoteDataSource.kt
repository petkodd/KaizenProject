package com.example.kaizenproject.data.remote

import com.example.kaizenproject.data.remote.dto.SportsDtoItem

interface SportsRemoteDataSource {
    suspend fun getSports(): ArrayList<SportsDtoItem>
}
