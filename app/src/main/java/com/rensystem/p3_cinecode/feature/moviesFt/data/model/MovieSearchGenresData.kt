package com.rensystem.p3_cinecode.feature.moviesFt.data.model

import com.google.gson.annotations.SerializedName

data class MovieSearchGenreData(
    val id: Long,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}
