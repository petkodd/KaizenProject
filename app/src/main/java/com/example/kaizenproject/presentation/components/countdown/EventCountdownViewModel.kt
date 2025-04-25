package com.example.kaizenproject.presentation.components.countdown

import androidx.lifecycle.ViewModel
import com.example.kaizenproject.common.AppCoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class EventCountdownViewModel @Inject constructor(
    private val coroutineDispatchers: AppCoroutineDispatchers
) : ViewModel() {

    internal fun startCountdown(targetDuration: Long) = flow {
        var timeLeft = targetDuration
        while (timeLeft > 0) {
            timeLeft -= 1000
            emit(formatTime(timeLeft))
            delay(1000L)
        }
    }.flowOn(coroutineDispatchers.io)

    private fun formatTime(millis: Long): String {
        val msInHour = 1000 * 60 * 60
        val msInMinute = 1000 * 60
        val msInSecond = 1000

        val hours = (millis / msInHour).toInt()
        val minutes = ((millis % msInHour) / msInMinute).toInt()
        val seconds = ((millis % msInMinute) / msInSecond).toInt()

        return "%02d:%02d:%02d".format(hours, minutes, seconds)
    }
}
