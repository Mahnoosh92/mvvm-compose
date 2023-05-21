package com.example.shoppingapp.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.Destinations
import com.example.shoppingapp.ui.detail.Detail
import com.example.shoppingapp.ui.home.Home
import com.example.shoppingapp.ui.splash.Splash

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destinations.SPLASH.route) {
        composable(Destinations.SPLASH.route) {
            Splash {
                Log.i("mahnoosh", "MyApp: splash is recomposed")
                navController.navigate(it) {
                    popUpTo(Destinations.SPLASH.route) {inclusive=true}
                }
            }
        }
        composable(Destinations.HOME.route) {
            var bottomCurrentItem by remember {
                mutableStateOf("Dashboard")
            }
            Log.i("mahnoosh", "MyApp: myapp home is recomposed")
            Home(bottomCurrentItem = bottomCurrentItem, bottomItemClicked = {
                bottomCurrentItem = it
            }, backPressed = {
                navController.navigateUp()
            }) {
                navController.navigate(Destinations.DETAILS.route)
            }
        }
        composable(Destinations.DETAILS.route) {
            Detail()
        }
        /*...*/
    }
}