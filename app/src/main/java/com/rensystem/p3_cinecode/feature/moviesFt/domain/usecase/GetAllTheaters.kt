package com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase

import com.rensystem.p3_cinecode.feature.moviesFt.domain.MovieRepository
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.TheaterMovieItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.TheaterRepository
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.TheaterItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTheaters @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<TheaterMovieItem> {
        return movieRepository.getAllTheater()
    }
}