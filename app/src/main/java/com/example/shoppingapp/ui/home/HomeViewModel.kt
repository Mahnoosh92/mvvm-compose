package com.example.shoppingapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.BuildConfig
import com.example.shoppingapp.data.di.MainDispatcher
import com.example.shoppingapp.data.model.local.Recipe
import com.example.shoppingapp.data.model.remote.RemoteAddRecipeBody
import com.example.shoppingapp.data.repository.recipe.add.AddRecipeRepository
import com.example.shoppingapp.data.repository.recipe.search.SearchRecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val searchRecipeRepository: SearchRecipeRepository,
    private val addRecipeRepository: AddRecipeRepository
) : ViewModel() {
    private var _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState get() = _homeUiState.asStateFlow()

    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _homeUiState.update { homeUiState ->
            homeUiState.copy(error = exception.message)
        }
    }

    fun getRecipes(
        apiKey: String = BuildConfig.API_KEY,
        query: String = "pasta",
        maxFat: Int = 25
    ) {
        viewModelScope.launch(exceptionHandler + mainDispatcher) {
            searchRecipeRepository.searchRecipe(apiKey = apiKey, query = query, maxFat = maxFat)
                ?.let { list ->
                    _homeUiState.update { homeUiState ->
                        homeUiState.copy(data = list, loading = false)
                    }
                }
        }
    }

    fun addRecipe(title: String, serving: Int) {
        val body = RemoteAddRecipeBody(
            servings = serving,
            title = title,
            ingredients = listOf("aa"),
            instructions = "jghhdf"
        )
        viewModelScope.launch(exceptionHandler + mainDispatcher) {
            val result = addRecipeRepository.addRecipe(apiKey = BuildConfig.API_KEY, body = body)
            _homeUiState.update { homeUiState ->
                homeUiState.copy(successfulRecipeAdd = true)
            }
        }
    }

    fun consumeSuccessfulAddTag() {
        _homeUiState.update { homeUiState ->
            homeUiState.copy(successfulRecipeAdd = null)
        }
    }
}

data class HomeUiState(
    val data: List<Recipe>? = null,
    val error: String? = null,
    val loading: Boolean? = true,
    val successfulRecipeAdd: Boolean? = null,
)