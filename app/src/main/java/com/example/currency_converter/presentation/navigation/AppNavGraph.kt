package com.example.currency_converter.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.currency_converter.presentation.AppViewModel
import com.example.currency_converter.presentation.HomeScreen.HomeScreen
import com.example.currency_converter.presentation.HomeScreen.HomeViewModel
import com.example.currency_converter.presentation.country.CountryScreen
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    appViewModel: AppViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(homeViewModel, appViewModel, navController)
        }
        composable("${Routes.SELECT_CURRENCY}/{isFrom}",
            arguments = listOf(navArgument("isFrom") { type = NavType.BoolType })
        ) { backStackEntry ->
            val isFrom = backStackEntry.arguments?.getBoolean("isFrom") ?: true
            CountryScreen(homeViewModel, appViewModel, isFrom, navController)
        }
    }
}
