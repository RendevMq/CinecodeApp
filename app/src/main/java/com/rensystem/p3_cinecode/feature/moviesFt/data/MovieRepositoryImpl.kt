package com.rensystem.p3_cinecode.feature.moviesFt.data

import com.rensystem.p3_cinecode.core.shareFakeData.MovieLocalService
import com.rensystem.p3_cinecode.core.shareFakeData.SessionLocalService
import com.rensystem.p3_cinecode.feature.moviesFt.data.network.MovieService
import com.rensystem.p3_cinecode.feature.moviesFt.domain.MovieRepository
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.Actor
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.CinemaMovieDetailItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.CinemaMovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieDetailItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.SessionMovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.TheaterMovieItem
import com.rensystem.p3_cinecode.feature.theatersFt.data.TheaterLocalService
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.MovieTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.SessionTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.TheaterItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieLocalService: MovieLocalService,
    private val theaterLocalService: TheaterLocalService,
    private val sessionLocalService: SessionLocalService,
    private val api: MovieService
) : MovieRepository {
    override suspend fun getNowShowingMovies(): Flow<List<MovieItem>> {
        val movieList = movieLocalService.getAllLocalMovies().movies
            ?: emptyList()

        val nowShowingItems = movieList
            .filter { it.isComingSoon == false && it.isPreSale == false }
            .map { it.toMovieDomain() }

        return flowOf(nowShowingItems)
    }

    override suspend fun getComingSoonMovies(): Flow<List<MovieItem>> {

        val movieList = movieLocalService.getAllLocalMovies().movies
            ?: emptyList()

        val comingsoonItems = movieList
            .filter { it.isComingSoon == true || it.isPreSale == true }
            .map { it.toMovieDomain() }

        return flowOf(comingsoonItems)
    }

    //METODOS PARA MAPEAR LAS FUNCIONES //
    override suspend fun getAllTheater(): List<TheaterMovieItem> {
        val theaterList = theaterLocalService.getAllLocalTheater().cinemas
            ?: emptyList()
        return theaterList.map { it.toMovieDomain() }
    }

    override suspend fun getAllMovies(): List<MovieDetailItem> {
        val movieList = movieLocalService.getAllLocalMovies().movies
            ?: emptyList()
        return movieList.map { it.toMovieDetailDomain() }
    }

    override suspend fun getAllSessions(): List<SessionMovieItem> {
        val sessionList = sessionLocalService.getAllLocalSession().sessions ?: emptyList()
        return sessionList.map { it.toMovieDomain() }
    }

    override fun getMovieById(movieId: String, allMovies: List<MovieDetailItem>): MovieDetailItem {
        return allMovies.find { movieId == it.id }!!
    }

    override fun getSessionsIdsByTheaterAndDate(
        cinema: CinemaMovieDetailItem,
        date: String
    ): List<String> {
        return cinema.dates.firstOrNull { it.date?.substringBefore("T") == date }?.sessions
            ?: emptyList()
    }

    override fun mapSessionIdsToSessionItems(
        sessionIds: List<String>,
        allSessions: List<SessionMovieItem>
    ): List<SessionMovieItem> {
        return sessionIds.mapNotNull { sessioIds ->
            allSessions.find { it.id == sessioIds }
        }
    }

    override fun getTheaterGeneralInfo(
        theaterId: String,
        allTheaters: List<TheaterMovieItem>
    ): TheaterMovieItem? {
        return allTheaters.find { it.id == theaterId }
    }

    override suspend fun getTmdbMovieIdByName(movieName: String): Long? {
        return api.searchMovieByName(movieName)
    }

    override suspend fun getGenresFromTmdb(movieId: Long): List<String> {
        return api.getGenresByMovieId(movieId)
    }

    override suspend fun getCastFromTmdb(movieId: Long): List<Actor> {
        return api.getCastByMovieId(movieId).map {
            it.toMovieItemDomain()
        }
    }
}