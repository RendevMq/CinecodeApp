package com.rensystem.p3_cinecode.core.navigation.graphs

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rensystem.p3_cinecode.core.navigation.Graph
import com.rensystem.p3_cinecode.core.navigation.MainRouteScreen
import com.rensystem.p3_cinecode.core.navigation.MoviesRouteScreen
import com.rensystem.p3_cinecode.core.navigation.TheatersRouteScreen
import com.rensystem.p3_cinecode.feature.concessionFt.ConcessionMainScreen
import com.rensystem.p3_cinecode.feature.homeFt.HomeMainScreen
import com.rensystem.p3_cinecode.feature.homeFt.ui.HomeMainViewModel
import com.rensystem.p3_cinecode.feature.moreFt.MoreMainScreen
import com.rensystem.p3_cinecode.feature.moviesFt.MoviesMainScreen
import com.rensystem.p3_cinecode.feature.moviesFt.ui.MovieMainViewModel
import com.rensystem.p3_cinecode.feature.moviesFt.ui.sharedComponents.MovieDetailViewModel
import com.rensystem.p3_cinecode.feature.theatersFt.TheatersMainScreen
import com.rensystem.p3_cinecode.feature.theatersFt.ui.TheaterMainViewModel

@Composable
fun MainGraph(
    rootNavController: NavHostController,
    homeNavController: NavHostController,
    innerPadding: PaddingValues,
    homeMainViewModel: HomeMainViewModel,
    movieMainViwModel: MovieMainViewModel,
    theaterMainViwModel: TheaterMainViewModel,
) {
    NavHost(
        navController = homeNavController,
        route = Graph.MainGraph::class,
        startDestination = MainRouteScreen.Movies
    ) {
        composable<MainRouteScreen.Home> {
            HomeMainScreen(innerPadding, homeMainViewModel = homeMainViewModel)
        }
        composable<MainRouteScreen.Movies> {
            MoviesMainScreen(
                innerPadding = innerPadding,
                navigateToDetail = { movieItem ->
                    rootNavController.navigate(MoviesRouteScreen.MovieDetail(movieItem))
                },
                moviesMainViewModel = movieMainViwModel
            )
        }
        composable<MainRouteScreen.Theaters> {
            TheatersMainScreen(
                innerPadding,
                theaterMainViewModel = theaterMainViwModel,
                navigateToDetail = { theaterItem ->
                    rootNavController.navigate(TheatersRouteScreen.TheaterDetail(theaterItem.id))
                }
            )
        }
        composable<MainRouteScreen.Concessions> {
            ConcessionMainScreen(innerPadding)
        }
        composable<MainRouteScreen.More> {
            MoreMainScreen(innerPadding)
        }
    }
}