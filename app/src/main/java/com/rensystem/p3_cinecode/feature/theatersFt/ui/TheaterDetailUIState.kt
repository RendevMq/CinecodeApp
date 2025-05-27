package com.rensystem.p3_cinecode.feature.theatersFt.ui

import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.MovieTheaterItem

sealed interface TheaterDetailUIState {
    object Loading : TheaterDetailUIState
    data class Success(
        val moviesWithSessions: List<MovieTheaterItem>,
        val availableDates: List<String>
    ) : TheaterDetailUIState

    data class Error(val throwable: Throwable) : TheaterDetailUIState
}
