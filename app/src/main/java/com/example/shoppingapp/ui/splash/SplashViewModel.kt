package com.example.shoppingapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(@MainDispatcher val mainDispatcher: CoroutineDispatcher) :
    ViewModel() {
    private var _splashUiState = MutableStateFlow(SplashUiState())
    val splashUiState get() = _splashUiState

    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _splashUiState.update { splashUiState ->
            splashUiState.copy(error = "Something went wronge")
        }
    }

    fun isUserSignedIn() {
        viewModelScope.launch(exceptionHandler + mainDispatcher) {
            delay(1500)
            _splashUiState.update { splashUiState ->
                splashUiState.copy(isLoggedIn = true)
            }
        }
    }

    fun consumeSplashUiState() {
        _splashUiState.update { splashUiState ->
            splashUiState.copy(loading = null, isLoggedIn = null, error = null)
        }
    }
}

data class SplashUiState(
    val loading: Boolean? = null,
    val isLoggedIn: Boolean? = null,
    val error: String? = null
)