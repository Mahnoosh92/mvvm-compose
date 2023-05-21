package com.example.shoppingapp.data.repository.recipe.add

import com.example.shoppingapp.data.datasource.remote.recipes.add.RemoteAddRecipeDatasource
import com.example.shoppingapp.data.model.remote.RemoteAddRecipeBody
import javax.inject.Inject

class DefaultAddRecipeRepository @Inject constructor(private val remoteAddRecipeDatasource: RemoteAddRecipeDatasource) :
    AddRecipeRepository {
    override suspend fun addRecipe(apiKey: String , body: RemoteAddRecipeBody): Any {
        val response = remoteAddRecipeDatasource.addRecipe(apiKey = apiKey, body = body)
        if (!response.isSuccessful) {
            throw Exception("Something went wronge")
        } else {
            return response
        }
    }

}