package com.example.shoppingapp.data.repository.recipe.search

import com.example.shoppingapp.R
import com.example.shoppingapp.data.datasource.remote.recipes.search.RemoteSearchRecipeDatasource
import com.example.shoppingapp.data.model.local.Recipe
import com.example.shoppingapp.utils.string.StringResolver
import javax.inject.Inject

class DefaultSearchRecipeRepository @Inject constructor(
    private val remoteSearchRecipeDatasource: RemoteSearchRecipeDatasource,
    private val stringResolver: StringResolver
) : SearchRecipeRepository {
    override suspend fun searchRecipe(apiKey: String, query: String, maxFat: Int): List<Recipe>? {
        val response = remoteSearchRecipeDatasource.searchRecipe(
            apiKey = apiKey,
            query = query,
            maxFat = maxFat
        )
        return if (response.isSuccessful) {
            response.body()?.results?.map {
                it.toRecipe()
            }
        } else {
            throw Exception(stringResolver.getString(R.string.general_error))
        }
    }
}