package com.example.shoppingapp.data.datasource.remote.recipes.search

import com.example.shoppingapp.BuildConfig
import com.example.shoppingapp.data.api.ApiService
import com.example.shoppingapp.data.model.remote.RemoteSearchRecipeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class DefaultRemoteSearchRecipeDatasource @Inject constructor(private val apiService: ApiService) :
    RemoteSearchRecipeDatasource {
    override suspend fun searchRecipe(
        apiKey: String,
        query: String,
        maxFat: Int
    ): Response<RemoteSearchRecipeResponse> =
        apiService.searchRecipe(apiKey = apiKey, query = query, maxFat = maxFat)

}