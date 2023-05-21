package com.example.shoppingapp.data.repository.recipe.add

import com.example.shoppingapp.data.model.remote.RemoteAddRecipeBody

interface AddRecipeRepository {
    suspend fun addRecipe(
        apiKey: String, body: RemoteAddRecipeBody
    ):Any
}