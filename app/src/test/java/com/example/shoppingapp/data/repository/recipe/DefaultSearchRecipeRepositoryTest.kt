package com.example.shoppingapp.data.repository.recipe

import com.example.shoppingapp.data.datasource.remote.recipes.search.RemoteSearchRecipeDatasource
import com.example.shoppingapp.data.model.remote.RemoteRecipe
import com.example.shoppingapp.data.model.remote.RemoteSearchRecipeResponse
import com.example.shoppingapp.data.repository.recipe.search.DefaultSearchRecipeRepository
import com.example.shoppingapp.utils.string.StringResolver
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
internal class DefaultSearchRecipeRepositoryTest {

    @Mock
    private lateinit var remoteSearchRecipeDatasource: RemoteSearchRecipeDatasource

    @Mock
    private lateinit var stringResolver: StringResolver

    private lateinit var defaultSearchRecipeRepository: DefaultSearchRecipeRepository

    private val responseRemoteSuccessful =
        Response.success<RemoteSearchRecipeResponse>(
            200,
            RemoteSearchRecipeResponse(
                offset = null,
                number = null,
                results = emptyList<RemoteRecipe>(),
                totalResults = 0
            )
        )
    private val responseRemoteError = Response.error<RemoteSearchRecipeResponse>(
        400,
        "{\"key\":[\"somestuff\"]}"
            .toResponseBody("application/json".toMediaTypeOrNull())
    )

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        defaultSearchRecipeRepository = DefaultSearchRecipeRepository(
            remoteSearchRecipeDatasource = remoteSearchRecipeDatasource,
            stringResolver = stringResolver
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test when searchRecipe is successful , then both remoteSearchRecipeDatasource and localSearchRecipeDatasource are called`() =
        runTest() {
            Mockito.`when`(
                remoteSearchRecipeDatasource.searchRecipe(
                    anyString(),
                    anyString(),
                    anyInt()
                )
            )
                .thenReturn(responseRemoteSuccessful)

            defaultSearchRecipeRepository.searchRecipe( anyString(),anyString(), anyInt())
            Mockito.verify(remoteSearchRecipeDatasource)
                .searchRecipe( anyString(), anyString(), anyInt())
        }

    @Test
    fun `test when searchRecipe is not successful, the exception is thrown`() = runTest {
        Mockito.`when`(
            remoteSearchRecipeDatasource.searchRecipe(
                anyString(),
                anyString(),
                anyInt()
            )
        )
            .thenReturn(responseRemoteError)
        val error = "Something went wrong"
        Mockito.`when`(stringResolver.getString(anyInt())).thenReturn(error)

        try {
            val result =
                defaultSearchRecipeRepository.searchRecipe( anyString(), anyString(), anyInt())
        } catch (e: Exception) {
            assertThat(e.message).isEqualTo(error)
        }
    }
}