package com.example.shoppingapp.data.model.remote

import com.example.shoppingapp.data.model.local.Recipe
import com.google.gson.annotations.SerializedName

data class RemoteRecipe(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("imageType") val imageType: String?,
) {
    fun toRecipe() = Recipe(id = id, title = title, image = image, imageType = imageType)
}
