package com.example.kaizenproject.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kaizenproject.common.ui.theme.Gray
import com.example.kaizenproject.presentation.SportingEventsActions.ShowAllEvents
import com.example.kaizenproject.presentation.SportingEventsActions.ShowFavoriteEvents
import com.example.kaizenproject.presentation.SportingEventsActions.UpdateFavoriteStatus
import com.example.kaizenproject.presentation.components.ExpandableHeader
import com.example.kaizenproject.presentation.components.countdown.EventCountdown

@Composable
fun SportingEventsScreen(
    viewModel: SportingEventsViewModel = hiltViewModel()
) {
    val viewState = viewModel.state.collectAsStateWithLifecycle().value

    SportingEventsScreenLayout(
        state = viewState,
        onActionSent = viewModel::sendAction
    )
}

@Composable
private fun SportingEventsScreenLayout(
    state: SportingEventsState,
    onActionSent: (SportingEventsActions) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Surface(modifier = Modifier.fillMaxSize()) {
        state.error?.let {
            Error(it, snackbarHostState)
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier
                .size(24.dp)
                .wrapContentSize(Alignment.Center)
            )
        } else {
            SportingEvents(
                eventsData = state.eventsForSport,
                onActionSent = onActionSent
            )
        }
    }
}

@Composable
private fun Error(
    error: String,
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(error) {
        snackbarHostState.showSnackbar(error)
    }
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                snackbarData = data,
            )
        }
    )
}

@Composable
private fun SportingEvents(
    eventsData: List<SportingEventData>,
    onActionSent: (SportingEventsActions) -> Unit
) {
    LazyColumn(modifier = Modifier.background(Gray)) {
        items(eventsData) { events ->
            Spacer(modifier = Modifier.padding(top = 16.dp))
            ExpandableHeader(
                title = events.sportName,
                isSwitchEnabled = events.isSwitchEnabled,
                content = {
                    AvailableEvents(events, onActionSent)
                }
            ) { showFavoriteEvents ->
                if (showFavoriteEvents) {
                    onActionSent(ShowFavoriteEvents(events.sportName))
                } else {
                    onActionSent(ShowAllEvents(events.sportName))
                }
            }
        }
    }
}

@Composable
private fun AvailableEvents(
    events: SportingEventData,
    onActionSent: (SportingEventsActions) -> Unit
) {
    LazyRow {
        items(events.sportingEvents) { event ->
            Spacer(modifier = Modifier.padding(start = 24.dp))
            EventCountdown(
                targetDuration = event.eventStartDate.toLong(),
                isFavorite = event.isFavorite,
                onToggleFavorite = {
                    onActionSent(
                        UpdateFavoriteStatus(
                            sportName = events.sportName,
                            eventId = event.eventId,
                            isFavorite = !event.isFavorite
                        )
                    )
                },
                competitor1 = event.competitors.competitor1,
                competitor2 = event.competitors.competitor2
            )
            Spacer(modifier = Modifier.padding(end = 24.dp))
        }
    }
}

@Preview
@Composable
fun SportingEventsScreenPreview() {
    SportingEventsScreenLayout(
        state = SportingEventsState(
            isLoading = false,
            eventsForSport = emptyList(),
            error = null
        ),
        onActionSent = {}
    )
}
