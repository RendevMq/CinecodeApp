package com.rensystem.p3_cinecode.feature.moviesFt.domain

import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.Actor
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.CinemaMovieDetailItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.CinemaMovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieDetailItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.SessionMovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.TheaterMovieItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.MovieTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.SessionTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.TheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.usecase.GetAllTheaters
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    //====Main Screen=====//
    suspend fun getNowShowingMovies(): Flow<List<MovieItem>>
    suspend fun getComingSoonMovies(): Flow<List<MovieItem>>

    //===== TMDB API calls =====//

    /**
     * Busca una película en TMDB por su nombre y retorna el ID si la encuentra.
     */
    suspend fun getTmdbMovieIdByName(movieName: String): Long?

    /**
     * Obtiene los géneros (como lista de strings) de una película por su ID en TMDB.
     */
    suspend fun getGenresFromTmdb(movieId: Long): List<String>

    /**
     * Obtiene el cast (actores principales) de una película por su ID en TMDB.
     */
    suspend fun getCastFromTmdb(movieId: Long): List<Actor>


    //====Detail Screen=====//
    suspend fun getAllTheater(): List<TheaterMovieItem>
    suspend fun getAllMovies(): List<MovieDetailItem>
    suspend fun getAllSessions(): List<SessionMovieItem>

    //Busca una movie por su id dentro de la lista proveniente de movies.js
    fun getMovieById(
        movieId: String,
        allMovies: List<MovieDetailItem>
    ) : MovieDetailItem

    /**
     * Obtiene los IDs de sesión de un cine específico en una fecha determinada,
     * a partir del objeto CinemaMovieItem (que viene dentro del MovieItem).
     */
    fun getSessionsIdsByTheaterAndDate(
        cinema: CinemaMovieDetailItem,
        date: String
    ): List<String>

    /**
     * Dado un listado de IDs de sesiones y una lista global de sesiones,
     * devuelve las sesiones correspondientes con toda su información.
     */
    fun mapSessionIdsToSessionItems(
        sessionIds:List<String>,
        allSessions: List<SessionMovieItem>
    ) : List<SessionMovieItem>

    /**
     * Obtiene la información general de un cine (nombre, dirección, ciudad, etc.)
     * desde la lista general de cines (TheaterMovieItem) usando el cinemaID.
     */
    fun getTheaterGeneralInfo(
        theaterId: String,
        allTheaters: List<TheaterMovieItem>
    ): TheaterMovieItem?

}


//interface MovieRepository {
//    //====Main Screen=====//
//    suspend fun getNowShowingMovies(): Flow<List<MovieItem>>
//    suspend fun getComingSoonMovies(): Flow<List<MovieItem>>
//
//    //====Detail Screen=====//
//    suspend fun getAllTheater(): List<TheaterMovieItem>
//    suspend fun getAllMovies(): List<MovieItem>
//    suspend fun getAllSessions(): List<SessionMovieItem>
//
//    //Busca una movie por su id dentro de la lista proveniente de movies.js
//    fun getMovieById(
//        movieId: String,
//        allMovies: List<MovieItem>
//    ) : MovieItem?
//
//    /**
//     * A partir de un MovieItem, una fecha seleccionada y datos globales de cines y sesiones,
//     * construye una lista de TheaterMovieItem con información general del cine
//     * y sus respectivas sesiones para esa fecha.
//     */
//    suspend fun buildTheatersWithSessions(
//        movie: MovieItem,
//        date: String,
//        allTheaters: List<TheaterMovieItem>,
//        allSessions: List<SessionMovieItem>
//    ) : List<TheaterMovieItem>
//
//    /**
//     * Obtiene los IDs de sesión de un cine específico en una fecha determinada,
//     * a partir del objeto CinemaMovieItem (que viene dentro del MovieItem).
//     */
//    fun getSessionsIdsByTheaterAndDate(
//        cinema: CinemaMovieItem,
//        date: String
//    ): List<String>
//
//    /**
//     * Dado un listado de IDs de sesiones y una lista global de sesiones,
//     * devuelve las sesiones correspondientes con toda su información.
//     */
//    fun mapSessionIdsToSessionItems(
//        sessionIds:List<String>,
//        allSessions: List<SessionMovieItem>
//    ) : List<SessionMovieItem>
//
//    /**
//     * Obtiene la información general de un cine (nombre, dirección, ciudad, etc.)
//     * desde la lista general de cines (TheaterMovieItem) usando el cinemaID.
//     */
//    fun getTheaterGeneralInfo(
//        theaterId: String,
//        allTheaters: List<TheaterMovieItem>
//    ): TheaterMovieItem?
//
//}