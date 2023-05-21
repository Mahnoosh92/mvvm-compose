package com.example.shoppingapp.data.datasource.remote.recipes.add


import com.example.shoppingapp.data.api.ApiService
import com.example.shoppingapp.data.model.remote.RemoteAddRecipeBody
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any

@RunWith(MockitoJUnitRunner::class)
internal class DefaultRemoteAddRecipeDatasourceTest {

    private lateinit var defaultRemoteAddRecipeDatasource: DefaultRemoteAddRecipeDatasource

    @Mock
    private lateinit var apiService: ApiService


    private val remoteAddRecipeBody = RemoteAddRecipeBody(servings = 1, title = "1", ingredients = emptyList(), instructions = "")

    @Before
    fun setUp() {
        defaultRemoteAddRecipeDatasource = DefaultRemoteAddRecipeDatasource(apiService = apiService)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test addRecipe`() = runTest {
        defaultRemoteAddRecipeDatasource.addRecipe(apiKey = "", body = remoteAddRecipeBody)

        Mockito.verify(apiService).addRecipe(apiKey = anyString(), body = any())
    }
}