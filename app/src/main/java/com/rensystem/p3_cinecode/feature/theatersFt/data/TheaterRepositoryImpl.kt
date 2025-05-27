package com.rensystem.p3_cinecode.feature.theatersFt.data

import com.rensystem.p3_cinecode.core.shareFakeData.MovieLocalService
import com.rensystem.p3_cinecode.core.shareFakeData.SessionLocalService
import com.rensystem.p3_cinecode.feature.theatersFt.domain.TheaterRepository
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.DateTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.MovieTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.SessionTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.TheaterItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class TheaterRepositoryImpl @Inject constructor(
    private val movieLocalService: MovieLocalService,
    private val theaterLocalService: TheaterLocalService,
    private val sessionLocalService: SessionLocalService

) : TheaterRepository {
    override suspend fun getAllTheater(): Flow<List<TheaterItem>> {
        val theaterList = theaterLocalService.getAllLocalTheater().cinemas
            ?: emptyList()
        val theaterItemList = theaterList
            .map { it.toTheaterDomain() }
        return flowOf(theaterItemList)
    }

    override suspend fun getAllMovies(): List<MovieTheaterItem>{
        val movieList = movieLocalService.getAllLocalMovies().movies
            ?: emptyList()
        return movieList.map { it.toTheaterDomain() }
    }

    override suspend fun getMoviesByTheaterId(
        allMovies: List<MovieTheaterItem>,
        theaterId: String,
        date: String
    ): List<MovieTheaterItem> {
//        val movieList = movieLocalService.getAllLocalMovies().movies
//            ?: emptyList()

        return allMovies.filter { movie ->
            //No debe ser "coming soon"
            !movie.isComingSoon && movie.cinemas?.any { cinema ->
                // Dete tenr el cine especificado
                cinema.cinemaID == theaterId &&
                        //Debe tener al menos una fecha que empiece igual al parámetro "date"
                        cinema.dates?.any { it.date?.substringBefore("T") == date } == true
            } == true
        }.sortedWith(
            compareByDescending<MovieTheaterItem> { it.isNewRelease }
                .thenByDescending { it.isPreSale }
        )
    }

    override suspend fun getTheaterDatesFromMovie(
        movie: MovieTheaterItem,
        theaterId: String
    ): List<DateTheaterItem> {
        val cinema = movie.cinemas?.find {
            it.cinemaID == theaterId
        }
        return cinema?.dates ?: emptyList()
    }

    override suspend fun getSessionsByDate(
        dates: List<DateTheaterItem>,
        date: String
    ): List<String> {
        return dates.find { it.date?.substringBefore("T") == date }?.sessions ?: emptyList()
    }

    override suspend fun getAllSessions(): List<SessionTheaterItem> {
        val sessionList = sessionLocalService.getAllLocalSession().sessions ?: emptyList()
        return sessionList.map { it.toTheaterDomain() }
    }

    override suspend fun getSessionItemsByIds(
        sessionIds: List<String>,
        allSessions: List<SessionTheaterItem>
    ): List<SessionTheaterItem> {
//        val allSessions = getAllSessions()
        return allSessions
            .filter { it.id in sessionIds }
    }

    override suspend fun getUniqueShowtimeDatesByCinema(
        theaterId: String,
        allSessions: List<SessionTheaterItem>
    ): List<String> {
        val sessions = allSessions
            .filter { session ->
                val sessionCinemaId = session.id?.substringBefore("-")
                sessionCinemaId == theaterId}

        val uniqueDates = mutableSetOf<String>()

        for (session in sessions) {
            val showtime = session.showtime // formato: "2025-05-06T19:20:00-05:00"
            val datePart = showtime?.substringBefore("T")
            if (!datePart.isNullOrBlank()) {
                uniqueDates.add(datePart)
            }
        }
        return uniqueDates.toList().sorted()
    }
}