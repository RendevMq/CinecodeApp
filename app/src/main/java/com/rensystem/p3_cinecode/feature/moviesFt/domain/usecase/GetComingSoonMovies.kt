package com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase


import com.rensystem.p3_cinecode.feature.moviesFt.domain.MovieRepository
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetComingSoonMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<List<MovieItem>> {
        return movieRepository.getComingSoonMovies()
    }
}