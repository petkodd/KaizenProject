package com.example.kaizenproject.domain.usecases

import com.example.kaizenproject.domain.SportsRepository
import com.example.kaizenproject.common.CommonResult
import javax.inject.Inject

class GetSportsUseCase @Inject constructor(
    private val sportsRepository: SportsRepository
) {
    suspend operator fun invoke() : CommonResult = sportsRepository.getSports()
}
