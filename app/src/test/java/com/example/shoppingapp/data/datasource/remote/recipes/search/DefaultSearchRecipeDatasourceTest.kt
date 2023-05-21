package com.example.shoppingapp.data.datasource.remote.recipes.search


import com.example.shoppingapp.BuildConfig
import com.example.shoppingapp.data.api.ApiService
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner



@RunWith(MockitoJUnitRunner::class)
internal class DefaultSearchRecipeDatasourceTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var defaultSearchRecipeDatasource: DefaultRemoteSearchRecipeDatasource

    @Before
    fun setUp() {
        defaultSearchRecipeDatasource = DefaultRemoteSearchRecipeDatasource(apiService = apiService)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test default search recipe datasource`() = runTest {
        defaultSearchRecipeDatasource.searchRecipe(apiKey = "", query = "", maxFat = 0)
//
        Mockito.verify(apiService)
            .searchRecipe(apiKey = anyString(), query = anyString(), maxFat = anyInt())
    }
}