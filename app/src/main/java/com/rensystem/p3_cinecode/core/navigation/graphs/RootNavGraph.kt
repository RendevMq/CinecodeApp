package com.rensystem.p3_cinecode.core.navigation.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rensystem.p3_cinecode.MainScreen
import com.rensystem.p3_cinecode.core.navigation.Graph
import com.rensystem.p3_cinecode.feature.homeFt.ui.HomeMainViewModel
import com.rensystem.p3_cinecode.feature.moviesFt.ui.MovieMainViewModel
import com.rensystem.p3_cinecode.feature.moviesFt.ui.sharedComponents.MovieDetailViewModel
import com.rensystem.p3_cinecode.feature.theatersFt.ui.TheaterMainViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph() {
    val rootNavController = rememberNavController()
    val homeMainViewModel: HomeMainViewModel = hiltViewModel()
    val movieMainViwModel: MovieMainViewModel = hiltViewModel()
    val theaterMainViwModel: TheaterMainViewModel = hiltViewModel()
    val movieDetailViewModel: MovieDetailViewModel = hiltViewModel()

    NavHost(
        navController = rootNavController,
        route = Graph.RootGraph::class, // IMPORTANTE: ahora con ::class
        startDestination = Graph.MainGraph
    ) {

        composable<Graph.MainGraph> {
            MainScreen(
                rootNavHostController = rootNavController,
                homeMainViewModel = homeMainViewModel,
                movieMainViwModel = movieMainViwModel,
                theaterMainViwModel = theaterMainViwModel,
            )
        }
        movieNavGraph(
            rootNavController,
            movieDetailViewModel
        )
        theaterNavGraph(rootNavController,theaterMainViwModel)
//            eccomerceNavGraph(
//                rootNavController,
//            )
////        notificationNavGraph(rootNavController)
//            settingNavGraph(rootNavController)
}
}