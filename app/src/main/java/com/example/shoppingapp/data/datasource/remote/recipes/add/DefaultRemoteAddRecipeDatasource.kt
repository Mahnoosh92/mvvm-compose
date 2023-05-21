package com.example.shoppingapp.data.datasource.remote.recipes.add

import com.example.shoppingapp.data.api.ApiService
import com.example.shoppingapp.data.model.remote.RemoteAddRecipeBody
import retrofit2.Response
import javax.inject.Inject

class DefaultRemoteAddRecipeDatasource @Inject constructor(private val apiService: ApiService) :
    RemoteAddRecipeDatasource {
    override suspend fun addRecipe(apiKey: String, body: RemoteAddRecipeBody): Response<Any> {
        return apiService.addRecipe(apiKey = apiKey, body = body)
    }
}