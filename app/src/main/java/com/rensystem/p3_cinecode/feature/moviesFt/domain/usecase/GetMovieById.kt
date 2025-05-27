package com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase

import com.rensystem.p3_cinecode.feature.moviesFt.domain.MovieRepository
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieDetailItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import javax.inject.Inject

class GetMovieById @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        movieId: String,
        allMovies: List<MovieDetailItem>
    ) : MovieDetailItem?{
        return movieRepository.getMovieById(movieId,allMovies)
    }
}