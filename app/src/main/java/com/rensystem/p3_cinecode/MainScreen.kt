package com.rensystem.p3_cinecode

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rensystem.p3_cinecode.core.navigation.BottomNavigationBar
import com.rensystem.p3_cinecode.core.navigation.graphs.MainGraph
import com.rensystem.p3_cinecode.core.utils.bottomNavigationItemList
import com.rensystem.p3_cinecode.feature.homeFt.ui.HomeMainViewModel
import com.rensystem.p3_cinecode.feature.moviesFt.ui.MovieMainViewModel
import com.rensystem.p3_cinecode.feature.moviesFt.ui.sharedComponents.MovieDetailViewModel
import com.rensystem.p3_cinecode.feature.theatersFt.ui.TheaterMainViewModel

fun NavBackStackEntry?.simpleRoute(): String? {
    return this?.destination?.route?.substringAfterLast('.')
}

@Composable
fun MainScreen(
    rootNavHostController: NavHostController,
    homeNavController: NavHostController = rememberNavController(),
    homeMainViewModel: HomeMainViewModel,
    movieMainViwModel: MovieMainViewModel,
    theaterMainViwModel: TheaterMainViewModel,
) {
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()

//    val currentRoute = navBackStackEntry?.destination?.route?.substringAfterLast('.')
    val currentRoute = navBackStackEntry.simpleRoute() //Con la funcion de extension de UTILS
    val topBarTitle by remember(currentRoute) {
        derivedStateOf {
            bottomNavigationItemList
                .firstOrNull { it.route::class.simpleName == currentRoute }
                ?.title ?: bottomNavigationItemList.first().title
        }
    }
    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = topBarTitle) }
//            )
//        },
        bottomBar = {
            BottomNavigationBar (
                items = bottomNavigationItemList,
                currentRoute = currentRoute,
//                onClick = { item ->
//                    homeNavController.navigate(item.route) {
//                        // Navegación segura
//                        popUpTo(homeNavController.graph.startDestinationRoute ?: "") {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                }
                onClick = { item ->
                    if (currentRoute != item.route::class.simpleName) {
                        homeNavController.navigate(item.route) {
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        MainGraph(
            rootNavController = rootNavHostController,
            homeNavController = homeNavController,
            innerPadding = innerPadding,
            homeMainViewModel = homeMainViewModel,
            movieMainViwModel = movieMainViwModel,
            theaterMainViwModel = theaterMainViwModel,
        )
    }
}