package com.example.kaizenproject.presentation

import androidx.lifecycle.viewModelScope
import com.example.kaizenproject.common.AppCoroutineDispatchers
import com.example.kaizenproject.common.CommonResult.Error
import com.example.kaizenproject.common.CommonResult.Success
import com.example.kaizenproject.common.ui.BaseViewModel
import com.example.kaizenproject.common.ui.ErrorType
import com.example.kaizenproject.domain.model.SportingEvents
import com.example.kaizenproject.domain.usecases.GetSportsUseCase
import com.example.kaizenproject.domain.usecases.ObserveSportingEventsUseCase
import com.example.kaizenproject.domain.usecases.UpdateFavoriteEventUseCase
import com.example.kaizenproject.presentation.SportingEventsActions.ShowAllEvents
import com.example.kaizenproject.presentation.SportingEventsActions.ShowFavoriteEvents
import com.example.kaizenproject.presentation.SportingEventsActions.UpdateFavoriteStatus
import com.example.kaizenproject.presentation.mappers.ErrorTypeMapper
import com.example.kaizenproject.presentation.mappers.mapAllSportEvents
import com.example.kaizenproject.presentation.mappers.mapFavoriteEvent
import com.example.kaizenproject.presentation.mappers.mapFavoriteEventPerSport
import com.example.kaizenproject.presentation.mappers.mapSportingEventsToData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportingEventsViewModel @Inject constructor(
    private val getSportsUseCase: GetSportsUseCase,
    private val observeSportingEventsUseCase: ObserveSportingEventsUseCase,
    private val updateFavoriteEventUseCase: UpdateFavoriteEventUseCase,
    private val errorMapper: ErrorTypeMapper,
    coroutineDispatchers: AppCoroutineDispatchers
) : BaseViewModel<SportingEventsState, SportingEventsActions>() {

    private val eventsMap = mutableMapOf<String, List<SportingEvents>>()

    init {
        viewModelScope.launch(coroutineDispatchers.io) {
            observeSportingEvents()
        }
    }

    override fun createInitialScreenState() = SportingEventsState(
        isLoading = false,
        eventsForSport = emptyList(),
        error = null
    )

    override suspend fun handleActions(action: SportingEventsActions) {
        when (action) {
            is UpdateFavoriteStatus -> updateFavoriteEventStatus(action)
            is ShowFavoriteEvents -> updateStateWithFavoriteEvents(action.sportName)
            is ShowAllEvents -> updateStateWithAllSportEvents(action.sportName)
        }
    }

    private suspend fun updateFavoriteEventStatus(action: UpdateFavoriteStatus) {
        updateFavoriteEventUseCase.invoke(
            eventId = action.eventId,
            isMarkedAsFavorite = action.isFavorite
        )
        val updatedEvents = state.value.eventsForSport.mapFavoriteEvent(
            sportName = action.sportName,
            eventId = action.eventId,
            isFavorite = action.isFavorite
        )
        val isSwitchEnabledPerSport = updatedEvents
            .find { it.sportName == action.sportName }
            ?.sportingEvents
            ?.any { it.isFavorite }

        if (isSwitchEnabledPerSport != null) {
            updateScreenState {
                eventsMap.putAll(updatedEvents.mapSportingEventsToData())
                copy(eventsForSport = updatedEvents.map { eventData ->
                    if (eventData.sportName == action.sportName) {
                        eventData.copy(isSwitchEnabled = isSwitchEnabledPerSport)
                    } else {
                        eventData
                    }
                })
            }
        }
    }

    private suspend fun updateStateWithFavoriteEvents(sportName: String) {
        updateScreenState {
            copy(eventsForSport = state.value.eventsForSport.mapFavoriteEventPerSport(sportName))
        }
    }

    private suspend fun updateStateWithAllSportEvents(sportName: String) {
        updateScreenState {
            copy(eventsForSport = eventsMap.mapAllSportEvents(
                sportName = sportName,
                eventData = state.value.eventsForSport
            ))
        }
    }

    private suspend fun observeSportingEvents() {
        observeSportingEventsUseCase.invoke().collectLatest { sportingEvents ->
            if (sportingEvents.isEmpty()) { getSports() }
            updateScreenState {
                eventsMap.putAll(sportingEvents)
                copy(
                    isLoading = false,
                    eventsForSport = sportingEvents.mapSportingEventsToData()
                )
            }
        }
    }

    private suspend fun getSports() {
        updateScreenState { copy(isLoading = true) }
        when (val result = getSportsUseCase.invoke()) {
            is Success -> {
                updateScreenState { copy(isLoading = false) }
                observeSportingEvents()
            }
            is Error -> updateErrorState(result.error)
        }
    }

    private suspend fun updateErrorState(error: ErrorType) {
        updateScreenState {
            copy(
                isLoading = false,
                error = errorMapper.mapError(
                    error = error.errorType
                )
            )
        }
    }
}
