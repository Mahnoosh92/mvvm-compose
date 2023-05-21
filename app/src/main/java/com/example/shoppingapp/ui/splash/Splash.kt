package com.example.shoppingapp.ui.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.shoppingapp.Destinations

@Composable
fun Splash(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel= hiltViewModel(),
    navigate: (String) -> Unit
) {
//    val viewModel: SplashViewModel = hiltViewModel()
    SplashAction(viewModel = viewModel) {
        navigate(it)
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LaunchedEffect(key1 = Unit) {
            viewModel.isUserSignedIn()
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Logo", style = MaterialTheme.typography.labelMedium)
            Text(text = "Text", style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun SplashAction(viewModel: SplashViewModel, navigate: (String) -> Unit) {
    val splashUiState = viewModel.splashUiState.collectAsState()

    if (splashUiState.value.isLoggedIn != null && splashUiState.value.isLoggedIn == true) {
        viewModel.consumeSplashUiState()
        navigate(Destinations.HOME.route)
    }
}
