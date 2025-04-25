package com.example.kaizenproject.domain.model

data class SportingEvents(
    val eventId: String,
    val sportId: String,
    val sportName: String,
    val competitors: Competitors,
    val eventStartDate: Int,
    val isFavorite: Boolean
)

data class Competitors(
    val competitor1: String,
    val competitor2: String,
)
