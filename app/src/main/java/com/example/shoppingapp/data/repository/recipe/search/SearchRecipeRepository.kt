package com.example.shoppingapp.data.repository.recipe.search

import com.example.shoppingapp.BuildConfig
import com.example.shoppingapp.data.model.local.Recipe
import com.example.shoppingapp.data.model.remote.RemoteAddRecipeBody
import kotlinx.coroutines.flow.Flow

interface SearchRecipeRepository {
    suspend fun searchRecipe(
        apiKey: String,
        query: String,
        maxFat: Int
    ): List<Recipe>?
}