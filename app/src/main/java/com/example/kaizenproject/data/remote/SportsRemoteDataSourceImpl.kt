package com.example.kaizenproject.data.remote

import com.example.kaizenproject.data.KaizenApi
import com.example.kaizenproject.data.remote.dto.SportsDtoItem
import javax.inject.Inject

class SportsRemoteDataSourceImpl @Inject constructor(
    private val kaizenApi: KaizenApi
) : SportsRemoteDataSource {

    override suspend fun getSports(): ArrayList<SportsDtoItem> {
        return kaizenApi.getSports()
    }
}
