package com.rensystem.p3_cinecode.feature.homeFt.domain.model

data class MovieHomeItem(
    val id: String, //useful as key
    val title: String,
    val genre: String,
    val trailer : String,
    val format: List<String>,
    val ratingDescription : String,
    val posterPath: String,
    val releaseDate: String,
    val overview: String,
    val runtime: Double,
)

enum class HomeRatingDescription(val label: String) {
    APT("ATP"),
    THE_14("+14"),
    THE_14_DNI("+14 DNI");

    companion object {
        fun from(value: String): HomeRatingDescription {
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
enum class HomeFormatData(val label: String) {
    PRIME("PRIME"),
    REGULAR("REGULAR"),
    THE2D("2D"),
    THE3D("3D"),
    XTREME("XTREME");

    companion object {
        fun from(value: String): HomeFormatData? {
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

// Función para obtener la abreviatura de clasificación
fun MovieHomeItem.getRatingLabel(): String {
    return HomeRatingDescription.from(ratingDescription).label
}

// Función para obtener las abreviaturas de formatos
fun MovieHomeItem.getFormatLabels(): List<String> {
    return format.mapNotNull { HomeFormatData.from(it)?.label }
}