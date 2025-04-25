package com.example.kaizenproject.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SportsDtoItem(
    @SerializedName("d")
    val d: String,
    @SerializedName("e")
    val eDto: List<EDto>,
    @SerializedName("i")
    val i: String
)

data class EDto(
    @SerializedName("d")
    val d: String,
    @SerializedName("i")
    val i: String,
    @SerializedName("sh")
    val sh: String,
    @SerializedName("si")
    val si: String,
    @SerializedName("tt")
    val tt: Int
)
