package com.rensystem.p3_cinecode.feature.theatersFt.domain.model
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import okhttp3.internal.format

data class TheaterItem (
    val id: String, //yes
    val name: String, //yes
    val address: String, //yes
    val city: String, //yes
    val phoneNumber: String, //yes
    val latitude: String,//yes
    val longitude: String,//yes
    val description: String,//yes
    val formats: List<String>, //yes
    val showroomQuantity: Long,
    val img: String, //yes
    var isFavorite: Boolean = false,
)

// Enum para formatos de cine
enum class MovieFormatTheaterData(val label: String) {
    PRIME("PRIME"),
    REGULAR("REGULAR"),
    SCREENX("SCREENX"),
    THE2D("2D"),
    THE3D("3D"),
    VIP("VIP"),
    XTREME("XTREME");

    companion object {
        fun from(value: String): MovieFormatTheaterData? {
            return when (value.uppercase()) {
                "PRIME" -> PRIME
                "REGULAR" -> REGULAR
                "SCREENX" -> SCREENX
                "THE2D" -> THE2D
                "THE3D" -> THE3D
                "VIP" -> VIP
                "XTREME" -> XTREME
                else -> null
            }
        }
    }
}

// Función para obtener las abreviaturas de formatos
fun TheaterItem.getFormatLabels(): List<String> {
    return formats.mapNotNull {MovieFormatTheaterData.from(it)?.label }
}