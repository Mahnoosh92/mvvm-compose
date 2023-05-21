package com.example.shoppingapp.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class SplashViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var splashViewModel: SplashViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        splashViewModel = SplashViewModel(mainDispatcher = testDispatcher)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test isUserSignedIn`() = runTest(testDispatcher.scheduler) {
        splashViewModel.isUserSignedIn()

        advanceUntilIdle()
        splashViewModel.splashUiState.test {
            assertThat(SplashUiState(isLoggedIn = true)).isEqualTo(awaitItem())
        }

    }
}