package com.example.shoppingapp.data.repository.recipe.add

import com.example.shoppingapp.data.datasource.remote.recipes.add.RemoteAddRecipeDatasource
import com.example.shoppingapp.data.model.remote.RemoteAddRecipeBody
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any

import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
internal class DefaultAddRecipeRepositoryTest {

    private lateinit var defaultAddRecipeRepository: DefaultAddRecipeRepository

    @Mock
    private lateinit var remoteAddRecipeDatasource: RemoteAddRecipeDatasource

    private val sucessfulResponse = Response.success<Any>(200, Any())
    private val failureResponse = Response.error<Any>(
        400, "{\"key\":[\"somestuff\"]}"
            .toResponseBody("application/json".toMediaTypeOrNull())
    )

    @Before
    fun setUp() {
        defaultAddRecipeRepository =
            DefaultAddRecipeRepository(remoteAddRecipeDatasource = remoteAddRecipeDatasource)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test addRecipe is successful`() = runTest {
        val remoteAddRecipeBody = RemoteAddRecipeBody(
            servings = 1,
            title = "",
            ingredients = emptyList<String>(),
            instructions = ""
        )
        Mockito.`when`(remoteAddRecipeDatasource.addRecipe(apiKey = "", body = remoteAddRecipeBody))
            .thenReturn(sucessfulResponse)
        val response = remoteAddRecipeDatasource.addRecipe(apiKey = "", body = remoteAddRecipeBody)

        assertThat(response).isNotNull()
        Mockito.verify(remoteAddRecipeDatasource).addRecipe(anyString(), any())
    }
}