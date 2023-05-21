package com.example.shoppingapp.data.model.remote

data class RemoteAddRecipeBody(
    val servings: Int,
    val title: String,
    val ingredients: List<String>,
    val instructions: String
) {}
