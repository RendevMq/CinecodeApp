package com.rensystem.p3_cinecode.core.navigation

import com.rensystem.p3_cinecode.feature.homeFt.ui.model.MovieSummaryUI
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import kotlinx.serialization.Serializable

//Routes.kt
// This file defines the navigation structure for the app, including the main route screen and the navigation items.
@Serializable
sealed interface Graph {
    @Serializable
    data object RootGraph : Graph

    @Serializable
    data object MainGraph : Graph

    @Serializable
    data object HomeGraph : Graph

    @Serializable
    data object MoviesGraph : Graph

    @Serializable
    data object Theater : Graph
}

// This interface defines the routes for the main bottom bar navigation.
// Each route corresponds to a main screen in the app.
@Serializable
sealed interface MainRouteScreen {
    @Serializable
    data object Home : MainRouteScreen

    @Serializable
    data object Movies : MainRouteScreen

    @Serializable
    data object Theaters : MainRouteScreen

    @Serializable
    data object Concessions : MainRouteScreen

    @Serializable
    data object More : MainRouteScreen
}

// This interface defines the routes for the home screen.
@Serializable
sealed interface HomeRouteScreen {
    @Serializable
    data class MovieDetail(val movieName: String) : HomeRouteScreen
    // Using name as parameter instead of ID since initial data comes from JSON and detail data from API
    data class BuyTickets(val movieResume: MovieSummaryUI) : HomeRouteScreen
}

// This interface defines the routes for the movies screen.
@Serializable
sealed interface MoviesRouteScreen {
    @Serializable
    data class MovieDetail(val movieItem: MovieItem) : MoviesRouteScreen

    @Serializable
    data class BuyTickets(val movieSummary: MovieSummaryUI) : MoviesRouteScreen
}

// This interface defines the routes for the movies screen.
@Serializable
sealed interface TheatersRouteScreen {
    @Serializable
    data class TheaterDetail(val theaterId: String) : TheatersRouteScreen

    @Serializable
    data class BuyTickets(val movieSummary: MovieSummaryUI) : TheatersRouteScreen
}


