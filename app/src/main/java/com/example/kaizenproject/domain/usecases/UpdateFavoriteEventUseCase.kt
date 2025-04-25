package com.example.kaizenproject.domain.usecases

import com.example.kaizenproject.domain.SportsRepository
import javax.inject.Inject

class UpdateFavoriteEventUseCase @Inject constructor(
    private val repository: SportsRepository
) {
    suspend operator fun invoke(eventId: String, isMarkedAsFavorite: Boolean) {
        repository.updateFavorite(
            eventId = eventId,
            isMarkedAsFavorite = isMarkedAsFavorite
        )
    }
}
