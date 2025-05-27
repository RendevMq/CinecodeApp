package com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase

import android.util.Log
import com.rensystem.p3_cinecode.feature.moviesFt.domain.MovieRepository
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieDetailItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.SessionMovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.TheaterMovieItem
import javax.inject.Inject
/**
 * A partir de un MovieItem, una fecha seleccionada y datos globales de cines y sesiones,
 * construye una lista de TheaterMovieItem con información general del cine
 * y sus respectivas sesiones para esa fecha.
 */
class GetTheatersWithSessionsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        movie: MovieDetailItem,
        date: String,
        allTheaters: List<TheaterMovieItem>,
        allSessions: List<SessionMovieItem>
    ) : List<TheaterMovieItem>{
        val TAG = "GetTheatersWithSessions"
        Log.i(TAG, "Iniciando useCase con película: ${movie.title}, fecha: $date")

        //Si la pelicula no tiene cines asociados. retornamos vacio
        if (movie.cinemas == null) {
            Log.i(TAG, "❌ movie.cinemas está VACÍO. No hay cines para esta película.")
        } else {
            Log.i(TAG, "❌❌❌ movie.cinemas está VACÍO. No hay cines para esta película.")
            movie.cinemas?.forEachIndexed { index, cinema ->
                Log.i(TAG, "🎬 Cine #$index: ID=${cinema.cinemaID}, nombre=${cinema.cinemaID}")
            }
        }

        val cinemaList = movie.cinemas ?: return emptyList()

        Log.i(TAG, "Cines obtenidos: $cinemaList")
        if (cinemaList.isEmpty()) {
            Log.d(TAG, "La lista de cines está vacía o es nula.")
            return emptyList()
        }

        Log.d(TAG, "Cines asociados: ${cinemaList.size}")

        Log.d(TAG, "📋 Mostrando los 10 primeros elementos de allSessions:")
        allSessions.take(10).forEachIndexed { index, session ->
            Log.d(TAG, "🔹 [$index] sessionID=${session.sessionID}, movieID=${session.id}, theaterID=${session.sessionID}")
        }


        return cinemaList.mapNotNull { cinemaMovieItem ->

            Log.d(TAG, "Procesando cine: ${cinemaMovieItem.cinemaID} (ID: ${cinemaMovieItem.cinemaID})")

            //Buscamos los IDs de sesiones en la fecha dada
            val sessionsIds = movieRepository.getSessionsIdsByTheaterAndDate(cinemaMovieItem, date)
            Log.d(TAG, "IDs de sesiones encontradas: $sessionsIds")
            if (sessionsIds.isEmpty()) {
                Log.d(TAG, "No hay sesiones para este cine en la fecha $date")
                return@mapNotNull null
            }

            //Mapeamos los IDs a objetos SessionMovieItem
            val sessionsItems = movieRepository.mapSessionIdsToSessionItems(sessionsIds, allSessions)
            Log.d(TAG, "Sesiones mapeadas: ${sessionsItems.size}")
            if (sessionsItems.isEmpty()) {
                Log.d(TAG, "No se pudo mapear ninguna sesión para este cine.")
                return@mapNotNull null
            }

            //Obtenemos la informacion general del cine
            val thaterInfo = movieRepository.getTheaterGeneralInfo(cinemaMovieItem.cinemaID, allTheaters)
            if (thaterInfo == null) {
                Log.d(TAG, "No se encontró información general del cine con ID: ${cinemaMovieItem.cinemaID}")
                return@mapNotNull null
            }

            Log.d(TAG, "Información del cine obtenida: ${thaterInfo.name}")
            //Devolvemos un nuevo objeto TheaterMovieItem con los showtimes rellenados
            thaterInfo.copy(showTime = sessionsItems)
        }.also {
            Log.d(TAG, "Total de cines con sesiones válidas: ${it.size}")
        }
    }
}