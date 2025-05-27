package com.rensystem.p3_cinecode.feature.moviesFt.data.network

import com.rensystem.p3_cinecode.feature.moviesFt.data.network.response.MovieSearchCastResponse
import com.rensystem.p3_cinecode.feature.moviesFt.data.network.response.MovieSearchGenreResponse
import com.rensystem.p3_cinecode.feature.moviesFt.data.network.response.MovieSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiClient {

    @GET("/3/search/movie")
    suspend fun getMovie(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<MovieSearchResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getGenres(
        @Path("movie_id") movieId: Long
    ): Response<MovieSearchGenreResponse>

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getCast(
        @Path("movie_id") movieId: Long
    ): Response<MovieSearchCastResponse>
}