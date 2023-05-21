package com.example.shoppingapp.data.model.remote

import com.example.shoppingapp.data.model.local.SearchRecipeResponse
import com.google.gson.annotations.SerializedName

data class RemoteSearchRecipeResponse(
    @SerializedName("offset") val offset: Int?,
    @SerializedName("number") val number: Int?,
    @SerializedName("results") val results: List<RemoteRecipe>?,
    @SerializedName("totalResults") val totalResults: Int,
) {
    fun toSearchRecipeResponse() = SearchRecipeResponse(
        offset = offset,
        number = number,
        results = results?.map {
            it.toRecipe()
        },
        totalResults = totalResults
    )
}
