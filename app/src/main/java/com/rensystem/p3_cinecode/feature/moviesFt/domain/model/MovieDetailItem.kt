package com.rensystem.p3_cinecode.feature.moviesFt.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

data class MovieDetailItem(
    val id: String,
    val title: String,
    val posterPath: String,
    val runtime: Double,

    val format: List<String>,
    val ratingDescription : String,
    val genre : String,
    val language: List<String>,
    val trailer : String,
    val overview : String,

    val isComingSoon: Boolean,
    val isPreSale: Boolean,
    val isRePremiere: Boolean,
    val isRemake: Boolean,
    val isNewRelease: Boolean,

    val cast: List<Actor> ?= null,
    val secondaryGenres: List<String> ?= null,
    val cinemas: List<CinemaMovieDetailItem>?= null

)

data class CinemaMovieDetailItem (
    val cinemaID: String,
    val dates: List<DateMovieDetailItem>
)

data class DateMovieDetailItem (
    val date: String,
    val sessions: List<String>
)

data class Actor(
    val id: Long,
    val name: String,
    val character: String,
    val profilePath: String?,
)

enum class MovieDetailRatingDescription(val label: String) {
    APT("ATP"),
    THE_14("+14"),
    THE_14_DNI("+14 DNI");

    companion object {
        fun from(value: String): MovieDetailRatingDescription {
            return when (value.uppercase()) {
                "APT" -> APT
                "THE14" -> THE_14
                "THE14DNI" -> THE_14_DNI
                else -> APT
            }
        }
    }
}

// Enum para formatos de cine
enum class MovieDetailFormatData(val label: String) {
    PRIME("PRIME"),
    REGULAR("REGULAR"),
    THE2D("2D"),
    THE3D("3D"),
    XTREME("XTREME");

    companion object {
        fun from(value: String): MovieDetailFormatData? {
            return when (value.uppercase()) {
                "PRIME" -> PRIME
                "REGULAR" -> REGULAR
                "THE2D" -> THE2D
                "THE3D" -> THE3D
                "XTREME" -> XTREME
                else -> null
            }
        }
    }
}

// Enum para languages de cine
enum class MovieDetailLanguageData(val label: String) {
    DOBLADA("DOBLADA"),
    SUBTITULAD("SUBTITULADA");

    companion object {
        fun from(value: String): MovieDetailLanguageData? {
            return when (value.uppercase()) {
                "DOBLADA" -> DOBLADA
                "SUBTITULAD" -> SUBTITULAD
                else -> null
            }
        }
    }
}

// Función para obtener la abreviatura de clasificación
fun MovieDetailItem.getRatingLabel(): String {
    return MovieRatingDescription.from(ratingDescription).label
}

// Función para obtener las abreviaturas de formatos
fun MovieDetailItem.getFormatLabels(): List<String> {
    return format.mapNotNull { MovieFormatData.from(it)?.label }
}

// Función para obtener la abreviatura de LENGUAGE
fun MovieDetailItem.getLanguageLabel(): List<String>{
    return language.mapNotNull { MovieLanguageData.from(it)?.label }
}


