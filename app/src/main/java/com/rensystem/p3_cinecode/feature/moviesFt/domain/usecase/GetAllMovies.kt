package com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase

import com.rensystem.p3_cinecode.feature.moviesFt.domain.MovieRepository
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieDetailItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import javax.inject.Inject


class GetAllMovies @Inject constructor(
    private val movieRepository: MovieRepository
){
    suspend operator fun invoke(): List<MovieDetailItem>{
        return movieRepository.getAllMovies()
    }
}

