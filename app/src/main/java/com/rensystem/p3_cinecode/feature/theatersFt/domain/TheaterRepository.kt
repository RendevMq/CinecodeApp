package com.rensystem.p3_cinecode.feature.theatersFt.domain

import com.rensystem.p3_cinecode.core.shareFakeData.model.SessionData
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.DateTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.MovieTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.SessionTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.TheaterItem
import kotlinx.coroutines.flow.Flow

interface TheaterRepository {
    suspend fun getAllTheater(): Flow<List<TheaterItem>>

    suspend fun getAllMovies(): List<MovieTheaterItem>

    suspend fun getMoviesByTheaterId(
        allMovies: List<MovieTheaterItem>,
        theaterId: String,
        date: String
    ): List<MovieTheaterItem>

    suspend fun getTheaterDatesFromMovie(
        movie: MovieTheaterItem,
        theaterId: String
    ): List<DateTheaterItem>

    suspend fun getSessionsByDate(dates: List<DateTheaterItem>, date: String): List<String>

    suspend fun getAllSessions(): List<SessionTheaterItem>

    suspend fun getSessionItemsByIds(
        sessionIds: List<String>,
        allSessions: List<SessionTheaterItem>
    ): List<SessionTheaterItem>

    suspend fun getUniqueShowtimeDatesByCinema(
        theaterId: String,
        allSessions: List<SessionTheaterItem>
    ): List<String>
}