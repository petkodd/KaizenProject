package com.example.kaizenproject.presentation.mappers

import com.example.kaizenproject.domain.model.SportingEvents
import com.example.kaizenproject.presentation.SportingEventData

fun Map<String, List<SportingEvents>>.mapSportingEventsToData() : List<SportingEventData> {
    return map { entry ->
        SportingEventData(
            sportName = entry.key,
            sportingEvents = entry.value,
            isSwitchEnabled = entry.value.any { it.isFavorite }
        )
    }
}

fun List<SportingEventData>.mapSportingEventsToData() : Map<String, List<SportingEvents>> {
    return associate { eventData ->
        eventData.sportName to eventData.sportingEvents
    }
}

fun MutableMap<String, List<SportingEvents>>.mapAllSportEvents(
    sportName: String,
    eventData: List<SportingEventData>
) = mapValues { entry ->
    if (entry.key == sportName) {
        entry.value
    } else {
        eventData
            .find { it.sportName == entry.key }?.sportingEvents ?: emptyList()
    }
}.mapSportingEventsToData()

fun List<SportingEventData>.mapFavoriteEventPerSport(sportName: String) = map {
    if (it.sportName == sportName) {
        it.copy(sportingEvents = it.sportingEvents
            .filter { event -> event.isFavorite }
        )
    } else {
        it
    }
}

fun List<SportingEventData>.mapFavoriteEvent(
    sportName: String,
    eventId: String,
    isFavorite: Boolean
) = this.map { eventData ->
    if (eventData.sportName == sportName) {
        val eventsForSport = eventData.sportingEvents
        val isSwitchEnabled = eventsForSport
            .any { it.isFavorite && it.sportName == sportName }

        eventData.copy(
            sportingEvents = eventData.sportingEvents.map { event ->
                if (event.eventId == eventId) {
                    event.copy(isFavorite = isFavorite)
                } else {
                    event
                }
            }, isSwitchEnabled = isSwitchEnabled
        )
    } else {
        eventData
    }
}
