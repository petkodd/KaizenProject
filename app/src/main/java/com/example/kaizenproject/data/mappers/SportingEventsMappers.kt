package com.example.kaizenproject.data.mappers

import com.example.kaizenproject.data.local.db.entities.SportsEntity
import com.example.kaizenproject.domain.model.SportingEvents
import com.example.kaizenproject.data.remote.dto.EDto
import com.example.kaizenproject.data.remote.dto.SportsDtoItem
import com.example.kaizenproject.domain.model.Competitors

fun ArrayList<SportsDtoItem>.mapToSportsEntities(): List<SportsEntity> {
    return this.flatMap { sportsDtoItem ->
        val sportName = sportsDtoItem.d
        sportsDtoItem.eDto.map { eDtoItem ->
            eDtoItem.toSportsEntity(sportName)
        }
    }
}

fun List<SportsEntity>.mapToSportingEvents(): List<SportingEvents> {
    return this.map { sportsEntity ->
        sportsEntity.toSportingEvents()
    }
}

private fun EDto.toSportsEntity(sportName: String): SportsEntity {
    return SportsEntity(
        eventId = this.i,
        sportId = this.si,
        sportName = sportName,
        eventName = this.d,
        eventStartDate = this.tt
    )
}

private fun SportsEntity.toSportingEvents(): SportingEvents {
    val competitors = this.eventName.splitCompetitors()
    return SportingEvents(
        eventId = this.eventId,
        sportId = this.sportId,
        sportName = this.sportName,
        competitors = Competitors(
            competitor1 = competitors.competitor1,
            competitor2 = competitors.competitor2
        ),
        eventStartDate = this.eventStartDate,
        isFavorite = this.isFavorite
    )
}

fun String.splitCompetitors(): Competitors {
    val competitors = this.split(" - ")
    val competitor1 = competitors.getOrNull(0) ?: ""
    val competitor2 = competitors.getOrNull(1) ?: ""
    return Competitors(competitor1, competitor2)
}
