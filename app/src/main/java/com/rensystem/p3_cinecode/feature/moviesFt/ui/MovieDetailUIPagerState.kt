package com.rensystem.p3_cinecode.feature.moviesFt.ui

import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieDetailItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.TheaterMovieItem

interface MovieDetailUIPagerState{
    object Loading: MovieDetailUIPagerState
    data class Error(val throwable: Throwable): MovieDetailUIPagerState
    data class Success(
        val selectedMovie: MovieDetailItem,
        val theatersWithSessions: List<TheaterMovieItem>
    ) : MovieDetailUIPagerState
}