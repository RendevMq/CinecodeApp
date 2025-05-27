package com.rensystem.p3_cinecode.feature.moviesFt.data.model

import com.google.gson.annotations.SerializedName
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.Actor
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.CinemaMovieDetailItem

data class MovieSearchCastData (
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("character")
    val character: String,
    @SerializedName("profile_path")
    val profilePath: String? = null
) {
    fun toMovieItemDomain(): Actor {
        return Actor(
            id = id,
            name = name ?: "",
            character = character ?: "",
            profilePath = profilePath ?: ""
        )
    }
}