package com.rensystem.p3_cinecode.core.navigation.graphs

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.rensystem.p3_cinecode.core.navigation.MoviesRouteScreen
import com.rensystem.p3_cinecode.core.navigation.type.createNavType
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.ui.sharedComponents.MovieDetailScreen
import com.rensystem.p3_cinecode.feature.moviesFt.ui.sharedComponents.MovieDetailViewModel
import kotlin.reflect.typeOf

//MoviesNavGraph.kt
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.movieNavGraph(
    rootNavController: NavHostController,
    movieDetailViewModel: MovieDetailViewModel
//    sharedTransitionScope: SharedTransitionScope,
//    eccomerceMainViewModel: EccomerceMainViewModel,
) {
    composable<MoviesRouteScreen.MovieDetail>(
        typeMap = mapOf(typeOf<MovieItem>() to createNavType<MovieItem>())
    ) { backStackEntry ->

        val route: MoviesRouteScreen.MovieDetail = backStackEntry.toRoute()
        val movieItem = route.movieItem

        val movieDetailUIPagerState by movieDetailViewModel.uiState.collectAsState()

        // Inicializar el ViewModel con el parámetro SOLO una vez
        LaunchedEffect(key1 = Unit) {
            movieDetailViewModel.loadMovieDetailData(movieItem)
        }

        MovieDetailScreen(
            uiState = movieDetailUIPagerState,
            movieItem = movieItem,
            onBackClick = { rootNavController.navigateUp() },
        )
//        FoodItemDetailView(
//            foodItem = foodItem.foodItem,
//            onBackClick = { rootNavController.navigateUp() },
//            onUpdateQuantity = { foodItem, quantity ->
//                // Handle quantity update
//                eccomerceMainViewModel.updateQuantity(foodItem, quantity)
//            },
//            sharedTransitionScope = sharedTransitionScope,
//            animatedContentScope = this@composable,
//        )
    }
}