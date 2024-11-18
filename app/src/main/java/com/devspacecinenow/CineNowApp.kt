package com.devspacecinenow

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devspacecinenow.detail.presentation.MovieDetailScreen
import com.devspacecinenow.list.presentation.MovieListViewModel
import com.devspacecinenow.list.presentation.ui.MovieListScreen

//NavHost coloca todas as telas no nele
@Composable
fun CineNowApp(
    listViewModel: MovieListViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "movieList") {
        composable(route = "movieList") {
            MovieListScreen(navController, listViewModel)
        }
        composable(
            route = "movieDetail" + "/{itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
            })

        ){ backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getString("itemId"))
            MovieDetailScreen(movieId, navController)
        }
    }
}