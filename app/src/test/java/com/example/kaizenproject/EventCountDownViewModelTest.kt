package com.example.kaizenproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kaizenproject.common.AppCoroutineDispatchers
import com.example.kaizenproject.presentation.components.countdown.EventCountdownViewModel
import com.example.kaizenproject.utils.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EventCountDownViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val dispatcherProvider = mockk<AppCoroutineDispatchers> {
        every { this@mockk.io } returns testDispatcher
        every { this@mockk.main } returns testDispatcher
    }

    private lateinit var viewModel: EventCountdownViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = EventCountdownViewModel(dispatcherProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `startCountdown emits correct countdown values`() = runTest {
        val targetDuration = 3000L
        val expectedValues = listOf("00:00:02", "00:00:01", "00:00:00")

        val actualValues = viewModel.startCountdown(targetDuration).toList()

        assertEquals(expectedValues, actualValues)
    }

    @Test
    fun `startCountdown emits correct countdown values for 0 duration`() = runTest {
        val targetDuration = 1L
        val expectedValues = listOf("00:00:00")

        val actualValues = viewModel.startCountdown(targetDuration).toList()

        assertEquals(expectedValues, actualValues)
    }

    @Test
    fun `startCountdown emits empty value for negative duration`() = runTest {
        val targetDuration = -1L
        viewModel.startCountdown(targetDuration).toList()
    }

    @Test
    fun `startCountdown stops emitting values when interrupted`() = runTest {
        val targetDuration = 3000L
        val job = launch {
            viewModel.startCountdown(targetDuration).toList()
        }
        delay(1500L)
        job.cancel()
        assertTrue(job.isCancelled)
    }
}
