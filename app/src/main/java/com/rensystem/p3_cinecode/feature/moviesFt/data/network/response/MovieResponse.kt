package com.rensystem.p3_cinecode.feature.moviesFt.data.network.response

import com.google.gson.annotations.SerializedName
import com.rensystem.p3_cinecode.feature.moviesFt.data.model.MovieSearchCastData
import com.rensystem.p3_cinecode.feature.moviesFt.data.model.MovieSearchData
import com.rensystem.p3_cinecode.feature.moviesFt.data.model.MovieSearchGenreData

data class MovieSearchResponse (
    @SerializedName("results")
    val movieResults: List<MovieSearchData>,
)

data class MovieSearchCastResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("cast")
    val cast: List<MovieSearchCastData> = emptyList() // valor por defecto
)

data class MovieSearchGenreResponse(
    @SerializedName("genres")
    val genres: List<MovieSearchGenreData>
)
