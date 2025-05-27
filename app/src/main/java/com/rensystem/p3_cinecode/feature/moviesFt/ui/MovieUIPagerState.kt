package com.rensystem.p3_cinecode.feature.moviesFt.ui

import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem

interface MovieUIPagerState {
    object Loading: MovieUIPagerState
    data class Error(val throwable: Throwable): MovieUIPagerState
    data class Success(
        val nowShowingMovies: List<MovieItem>,
        val comingSoonMovies: List<MovieItem>

    ) : MovieUIPagerState
}