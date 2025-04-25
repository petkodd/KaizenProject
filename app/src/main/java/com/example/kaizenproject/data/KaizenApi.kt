package com.example.kaizenproject.data

import com.example.kaizenproject.data.remote.dto.SportsDtoItem
import retrofit2.http.GET

interface KaizenApi {

    @GET("sports.json")
    suspend fun getSports(): ArrayList<SportsDtoItem>
}
