package com.rensystem.p3_cinecode.feature.moviesFt.domain.model

import android.os.Parcelable
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.CinemaTheaterItem
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import org.intellij.lang.annotations.Language

@Serializable
@Parcelize
data class MovieItem(
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

) : Parcelable

@Serializable
@Parcelize
data class CinemaMovieItem (
    val cinemaID: String,
    val dates: List<DateMovieItem>
):Parcelable

@Serializable
@Parcelize
data class DateMovieItem (
    val date: String,
    val sessions: List<String>
):Parcelable

enum class MovieRatingDescription(val label: String) {
    APT("ATP"),
    THE_14("+14"),
    THE_14_DNI("+14 DNI");

    companion object {
        fun from(value: String): MovieRatingDescription {
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
enum class MovieFormatData(val label: String) {
    PRIME("PRIME"),
    REGULAR("REGULAR"),
    THE2D("2D"),
    THE3D("3D"),
    XTREME("XTREME");

    companion object {
        fun from(value: String): MovieFormatData? {
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
enum class MovieLanguageData(val label: String) {
    DOBLADA("DOBLADA"),
    SUBTITULAD("SUBTITULADA");

    companion object {
        fun from(value: String): MovieLanguageData? {
            return when (value.uppercase()) {
                "DOBLADA" -> DOBLADA
                "SUBTITULAD" -> SUBTITULAD
                else -> null
            }
        }
    }
}

// Función para obtener la abreviatura de clasificación
fun MovieItem.getRatingLabel(): String {
    return MovieRatingDescription.from(ratingDescription).label
}

// Función para obtener las abreviaturas de formatos
fun MovieItem.getFormatLabels(): List<String> {
    return format.mapNotNull { MovieFormatData.from(it)?.label }
}

// Función para obtener la abreviatura de LENGUAGE
fun MovieItem.getLanguageLabel(): List<String>{
    return language.mapNotNull { MovieLanguageData.from(it)?.label }
}


