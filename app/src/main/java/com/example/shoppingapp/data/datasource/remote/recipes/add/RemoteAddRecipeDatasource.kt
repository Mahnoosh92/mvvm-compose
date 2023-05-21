package com.example.shoppingapp.data.datasource.remote.recipes.add

import com.example.shoppingapp.data.model.remote.RemoteAddRecipeBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query

interface RemoteAddRecipeDatasource {
    suspend fun addRecipe(
        apiKey: String, body: RemoteAddRecipeBody
    ): Response<Any>
}