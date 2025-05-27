package com.rensystem.p3_cinecode.feature.moviesFt.data.network

import android.util.Log
import com.rensystem.p3_cinecode.feature.moviesFt.data.model.MovieSearchCastData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieService @Inject constructor(
    private val movieApiClient: MovieApiClient
) {

    suspend fun searchMovieByName(movieName: String): Long? = withContext(Dispatchers.IO) {
        try {
            val response = movieApiClient.getMovie(query = movieName)
            if (response.isSuccessful) {
                val results = response.body()?.movieResults.orEmpty()
                results.firstOrNull()?.id
                //results.firstOrNull()?.id - same  - if (results.isNotEmpty()) results[0].id else null
            } else {
                throw Exception("Error ${response.code()} al buscar película: ${response.message()}")
            }
        } catch (e: Exception) {
             Log.e("MovieService", "Exception al buscar película", e)
            null // o podrías relanzar la excepción si prefieres manejarla en otro nivel
        }
    }

    suspend fun getGenresByMovieId(movieId: Long): List<String> = withContext(Dispatchers.IO) {
        try {
            val response = movieApiClient.getGenres(movieId)
            if (response.isSuccessful) {
                response.body()?.genres?.map { it.name }.orEmpty()
            } else {
                throw Exception("Error ${response.code()} al obtener géneros: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("MovieService", "Exception al obtener géneros", e)
            emptyList()
        }
    }


    suspend fun getCastByMovieId(movieId: Long): List<MovieSearchCastData> = withContext(Dispatchers.IO) {
        try {
            val response = movieApiClient.getCast(movieId)
            if (response.isSuccessful) {
                response.body()?.cast.orEmpty()
            } else {
                throw Exception("Error ${response.code()} al obtener cast: ${response.message()}")
            }
        } catch (e: Exception) {
             Log.e("MovieService", "Exception al obtener cast", e)
            emptyList()
        }
    }
}