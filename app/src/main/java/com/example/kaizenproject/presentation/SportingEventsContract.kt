package com.example.kaizenproject.presentation

import com.example.kaizenproject.common.ui.Action
import com.example.kaizenproject.common.ui.ScreenState
import com.example.kaizenproject.domain.model.SportingEvents

data class SportingEventsState(
    val isLoading: Boolean,
    val eventsForSport: List<SportingEventData>,
    val error: String?
) : ScreenState

data class SportingEventData(
    val sportName: String,
    val sportingEvents: List<SportingEvents>,
    val isSwitchEnabled: Boolean
)

sealed class SportingEventsActions : Action {

    data class UpdateFavoriteStatus(
        val sportName: String,
        val eventId: String,
        val isFavorite: Boolean
    ) : SportingEventsActions()

    data class ShowFavoriteEvents(val sportName: String) : SportingEventsActions()
    data class ShowAllEvents(val sportName: String) : SportingEventsActions()
}
