package com.example.shoppingapp.data.datasource.remote.recipes.search

import com.example.shoppingapp.BuildConfig
import com.example.shoppingapp.data.model.remote.RemoteSearchRecipeResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteSearchRecipeDatasource {
    suspend fun searchRecipe(
        apiKey: String,
        query: String,
        maxFat: Int
    ): Response<RemoteSearchRecipeResponse>
}