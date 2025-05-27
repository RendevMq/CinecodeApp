package com.rensystem.p3_cinecode.feature.theatersFt.domain.usecase

import com.rensystem.p3_cinecode.feature.theatersFt.domain.TheaterRepository
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.MovieTheaterItem
import javax.inject.Inject

class GetMoviesByTheaterIdUseCase @Inject constructor(
    private val repository: TheaterRepository
) {
    suspend operator fun invoke(
        theaterId: String,
        date:String,
        allMovies: List<MovieTheaterItem>
    ): List<MovieTheaterItem> {
        return repository.getMoviesByTheaterId(
            allMovies = allMovies,
            theaterId = theaterId,
            date = date
        )
    }
}
