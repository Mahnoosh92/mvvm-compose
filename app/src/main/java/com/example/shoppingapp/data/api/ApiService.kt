package com.example.shoppingapp.data.api

import com.example.shoppingapp.data.model.remote.RemoteAddRecipeBody
import com.example.shoppingapp.data.model.remote.RemoteSearchRecipeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/complexSearch")
    suspend fun searchRecipe(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("maxFat") maxFat: Int
    ): Response<RemoteSearchRecipeResponse>

    @POST("recipes/analyze")
    suspend fun addRecipe(
        @Query("apiKey") apiKey: String,
        @Body body: RemoteAddRecipeBody
    ): Response<Any>

}