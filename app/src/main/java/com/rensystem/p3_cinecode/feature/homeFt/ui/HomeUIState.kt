package com.rensystem.p3_cinecode.feature.homeFt.ui

import com.rensystem.p3_cinecode.feature.homeFt.domain.model.MovieHomeItem

sealed interface HomeUIState {
    object Loading: HomeUIState
    data class Error(val throwable: Throwable):HomeUIState
    data class Success(val releaseMovies: List<MovieHomeItem>) : HomeUIState
}