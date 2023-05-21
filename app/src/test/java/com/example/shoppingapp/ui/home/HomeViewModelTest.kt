package com.example.shoppingapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.shoppingapp.data.model.local.Recipe
import com.example.shoppingapp.data.model.remote.RemoteAddRecipeBody
import com.example.shoppingapp.data.repository.recipe.add.AddRecipeRepository
import com.example.shoppingapp.data.repository.recipe.search.SearchRecipeRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any

@RunWith(MockitoJUnitRunner::class)
internal class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var searchRecipeRepository: SearchRecipeRepository

    @Mock
    private lateinit var addRecipeRepository: AddRecipeRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(
            mainDispatcher = testDispatcher,
            searchRecipeRepository = searchRecipeRepository,
            addRecipeRepository = addRecipeRepository
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test getRecipes`() = runTest(testDispatcher.scheduler) {
        Mockito.`when`(searchRecipeRepository.searchRecipe(anyString(), anyString(), anyInt()))
            .thenReturn(
                emptyList<Recipe>()
            )
        homeViewModel.getRecipes(anyString(), anyString(), anyInt())

        advanceUntilIdle()

        homeViewModel.homeUiState.test {
            assertThat(awaitItem()).isEqualTo(
                HomeUiState(
                    data = emptyList<Recipe>(),
                    loading = false
                )
            )
        }
    }

    @Test
    fun `test addRecipe`() = runTest(testDispatcher.scheduler) {
        val body = RemoteAddRecipeBody(
            servings = 1,
            title = "",
            ingredients = emptyList<String>(),
            instructions = ""
        )
        Mockito.`when`(addRecipeRepository.addRecipe(apiKey = anyString(), body = any()))
            .thenReturn("")
        homeViewModel.addRecipe(title = "", serving = 1)

        advanceUntilIdle()
        homeViewModel.homeUiState.test {
            assertThat(awaitItem()).isEqualTo(HomeUiState(successfulRecipeAdd = true))
        }
    }
}