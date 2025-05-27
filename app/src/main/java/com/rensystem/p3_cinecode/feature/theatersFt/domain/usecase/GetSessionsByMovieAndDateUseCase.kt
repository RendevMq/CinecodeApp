package com.rensystem.p3_cinecode.feature.theatersFt.domain.usecase

import com.rensystem.p3_cinecode.feature.theatersFt.domain.TheaterRepository
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.MovieTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.SessionTheaterItem
import javax.inject.Inject

class GetSessionsByMovieAndDateUseCase @Inject constructor(
    private val repository: TheaterRepository
) {
    suspend operator fun invoke(
        movie: MovieTheaterItem,
        theaterId: String,
        date: String,
        allSessions: List<SessionTheaterItem>
    ): List<SessionTheaterItem> {
        val dates = repository.getTheaterDatesFromMovie(movie, theaterId)
        val sessionIds = repository.getSessionsByDate(dates, date)
        return repository.getSessionItemsByIds(sessionIds,allSessions)
    }
}
