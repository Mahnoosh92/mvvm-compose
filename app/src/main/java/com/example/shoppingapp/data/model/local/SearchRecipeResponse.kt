package com.example.shoppingapp.data.model.local

data class SearchRecipeResponse(
    val offset: Int?,
    val number: Int?,
    val results: List<Recipe>?,
    val totalResults: Int
)