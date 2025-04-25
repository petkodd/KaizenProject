package com.example.kaizenproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.kaizenproject.common.AppCoroutineDispatchers
import com.example.kaizenproject.common.CommonResult
import com.example.kaizenproject.domain.model.Competitors
import com.example.kaizenproject.domain.model.SportingEvents
import com.example.kaizenproject.domain.usecases.GetSportsUseCase
import com.example.kaizenproject.domain.usecases.ObserveSportingEventsUseCase
import com.example.kaizenproject.domain.usecases.UpdateFavoriteEventUseCase
import com.example.kaizenproject.presentation.SportingEventData
import com.example.kaizenproject.presentation.SportingEventsActions
import com.example.kaizenproject.presentation.SportingEventsActions.UpdateFavoriteStatus
import com.example.kaizenproject.presentation.SportingEventsState
import com.example.kaizenproject.presentation.SportingEventsViewModel
import com.example.kaizenproject.presentation.mappers.ErrorTypeMapper
import com.example.kaizenproject.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SportingEventsViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getSportsUseCase: GetSportsUseCase = mockk(relaxed = true)
    private val observeSportingEventsUseCase: ObserveSportingEventsUseCase = mockk()
    private val updateFavoriteEventUseCase: UpdateFavoriteEventUseCase = mockk(relaxed = true)
    private val errorTypeMapper: ErrorTypeMapper = mockk()

    private val dispatcherProvider = mockk<AppCoroutineDispatchers> {
        every { this@mockk.io } returns UnconfinedTestDispatcher()
    }

    private var arrangeBuilder = ArrangeBuilder()
    private lateinit var viewModel: SportingEventsViewModel

    @Test
    fun `Should emit empty state when ViewModel is initialized`() = runTest {
        arrangeBuilder.init()
        val expectedState = SportingEventsState(
            isLoading = false,
            eventsForSport = emptyList(),
            error = null
        )

        val actualState = viewModel.state.value
        assertEquals(expectedState, actualState)
    }

    @Test
    fun `Should return data from network if the observed data is empty`() = runTest {
        arrangeBuilder.withSuccessResult()

        val expectedState = SportingEventsState(
            isLoading = false,
            eventsForSport = emptyList(),
            error = null
        )

        val actualState = viewModel.state.value
        assertEquals(expectedState, actualState)
    }

    @Test
    fun `Should observe correct data when the result is not empty`() = runTest {
        arrangeBuilder.withDataResult()

        val expectedState = SportingEventsState(
            isLoading = false,
            eventsForSport =
                listOf(
                    SportingEventData(
                    sportName = "Sport",
                    sportingEvents = listOf(
                        SportingEvents(
                        eventId = "12345",
                        "Sport",
                        "Football",
                        Competitors("Competitor1", "Competitor2"),
                        1234567890,
                        isFavorite = false)
                    ),
                    isSwitchEnabled = false

                )
                ),
            error = null
        )

        viewModel.sendAction(SportingEventsActions.ShowAllEvents("Event"))

        viewModel.state.test {
            assertEquals(expectedState, awaitItem())
        }
    }

    @Test
    fun `Should update favorite status when the event is marked as favorite`() = runTest {
        arrangeBuilder.withUpdatedEvent()

        val expectedState = SportingEventsState(
            isLoading = false,
            eventsForSport =
                listOf(
                    SportingEventData(
                    sportName = "Sport",
                    sportingEvents = listOf(
                        SportingEvents(
                        eventId = "12345",
                        "Sport",
                        "Football",
                        Competitors("Competitor1", "Competitor2"),
                        1234567890,
                        isFavorite = true)
                    ),
                    isSwitchEnabled = true
                )
                ),
            error = null
        )

        viewModel.sendAction(UpdateFavoriteStatus(
            sportName = "12345",
            eventId = "Sport",
            isFavorite = true
        ))

        viewModel.state.test {
            assertEquals(expectedState, awaitItem())
        }
    }

    @Test
    fun `Should display only favorite events when switch is true`() = runTest {
        arrangeBuilder.withSwitchEnabledForFavoriteEvents()

        val expectedState = SportingEventsState(
            isLoading = false,
            eventsForSport =
                listOf(
                    SportingEventData(
                    sportName = "Football",
                    sportingEvents = arrangeBuilder.favoriteFootballEvents(),
                    isSwitchEnabled = true
                )
                ),
            error = null
        )

        viewModel.sendAction(SportingEventsActions.ShowFavoriteEvents("Football"))

        viewModel.state.test {
            assertEquals(expectedState, awaitItem())
        }
    }

    @Test
    fun `Should display all events when switch is false`() = runTest {
        arrangeBuilder.withSwitchEnabledForMixedEvents()

        val expectedState = SportingEventsState(
            isLoading = false,
            eventsForSport =
                listOf(
                    SportingEventData(
                    sportName = "Football",
                    sportingEvents = arrangeBuilder.mixedFootballEvents(),
                    isSwitchEnabled = true
                )
                ),
            error = null
        )

        viewModel.sendAction(SportingEventsActions.ShowAllEvents("Football"))

        viewModel.state.test {
            assertEquals(expectedState, awaitItem())
        }
    }

    @Test
    fun `Should emit false switch when there are no favorite events`() = runTest {
        arrangeBuilder.withSwitchDisabled()

        val expectedState = SportingEventsState(
            isLoading = false,
            eventsForSport =
            listOf(
                SportingEventData(
                sportName = "Sport",
                sportingEvents = listOf(
                    SportingEvents(
                    eventId = "12345",
                    "Sport",
                    "Football",
                    Competitors("Competitor1", "Competitor2"),
                    1234567890,
                    isFavorite = false)
                ),
                isSwitchEnabled = false
            )
            ),
            error = null
        )

        viewModel.sendAction(SportingEventsActions.ShowFavoriteEvents("Football"))

        viewModel.state.test {
            assertEquals(expectedState, awaitItem())
        }
    }

    private inner class ArrangeBuilder {
        fun init() {
            coEvery { observeSportingEventsUseCase.invoke() } returns emptyFlow()
            initViewModel()
        }

        fun withSuccessResult() {
            every { observeSportingEventsUseCase.invoke() } returns emptyFlow()
            coEvery { getSportsUseCase.invoke() } returns CommonResult.Success
            initViewModel()
        }

        fun withDataResult() {
            coEvery { observeSportingEventsUseCase.invoke() } returns flowOf(mockedData(isFavorite = false))
            initViewModel()
        }

        fun withUpdatedEvent() {
            coEvery { updateFavoriteEventUseCase.invoke("12345", true) } returns Unit
            coEvery { observeSportingEventsUseCase.invoke() } returns flowOf(mockedData(isFavorite = true))
            initViewModel()
        }

        fun withSwitchEnabledForFavoriteEvents() {
            coEvery { observeSportingEventsUseCase.invoke() } returns flowOf(mapOf("Football" to favoriteFootballEvents()))
            initViewModel()
        }

        fun withSwitchEnabledForMixedEvents() {
            coEvery { observeSportingEventsUseCase.invoke() } returns flowOf(mapOf("Football" to mixedFootballEvents()))
            initViewModel()
        }

        fun withSwitchDisabled() {
            coEvery { observeSportingEventsUseCase.invoke() } returns flowOf(mockedData(false))
            initViewModel()
        }

        fun mockedData(isFavorite: Boolean): Map<String, List<SportingEvents>> {
            val mockedList = listOf(
                SportingEvents(
                eventId = "12345",
                "Sport",
                "Football",
                Competitors("Competitor1", "Competitor2"),
                1234567890,
                isFavorite = isFavorite)
            )
            return mapOf("Sport" to mockedList)
        }

        fun favoriteFootballEvents() = generateSportingEvents("Football", count = 3, isFavorite = true)
        fun mixedFootballEvents() = favoriteFootballEvents() + generateSportingEvents("Football", count = 11, isFavorite = false)

        private fun generateSportingEvents(sportName: String, count: Int, isFavorite: Boolean): List<SportingEvents> {
            return List(count) { index ->
                SportingEvents(
                    sportId = "SportId$index",
                    eventId = "Event$index",
                    sportName = sportName,
                    competitors = Competitors("Competitor${index}A", "Competitor${index}B"),
                    eventStartDate = 1234567890 + index,
                    isFavorite = isFavorite
                )
            }
        }

        private fun initViewModel() {
            viewModel = SportingEventsViewModel(
                getSportsUseCase = getSportsUseCase,
                observeSportingEventsUseCase = observeSportingEventsUseCase,
                updateFavoriteEventUseCase = updateFavoriteEventUseCase,
                errorMapper = errorTypeMapper,
                coroutineDispatchers = dispatcherProvider
            )
        }
    }
}
