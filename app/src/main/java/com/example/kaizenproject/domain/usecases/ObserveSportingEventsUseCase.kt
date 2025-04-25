package com.example.kaizenproject.domain.usecases

import com.example.kaizenproject.domain.SportsRepository
import com.example.kaizenproject.domain.model.SportingEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveSportingEventsUseCase @Inject constructor(
    private val sportsRepository: SportsRepository
) {
    operator fun invoke() : Flow<Map<String, List<SportingEvents>>> =
        sportsRepository.observeSports().map { events -> events.groupBy { it.sportName } }
}
