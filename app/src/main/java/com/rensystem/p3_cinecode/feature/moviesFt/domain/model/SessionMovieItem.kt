package com.rensystem.p3_cinecode.feature.moviesFt.domain.model

import com.google.gson.annotations.SerializedName

data class SessionMovieItem (
    val id: String,
    val showtime: String,
    val formats: List<FormatsSessionMovieItem>,
    val languages: List<LanguageSessionMovieItem>,
    val screenName: String,
    val sessionID: String
)

enum class FormatsSessionMovieItem {
    REGULAR,
    THE2D,
    PRIME,
    THE3D,
    XTREME,
    SCREENX,
    VIP
}

//FUNCTION EXTENSION
fun FormatsSessionMovieItem.getLabel():String{
    return when (this){
        FormatsSessionMovieItem.REGULAR -> "REGULAR"
        FormatsSessionMovieItem.THE2D -> "2D"
        FormatsSessionMovieItem.PRIME -> "PRIME"
        FormatsSessionMovieItem.THE3D -> "3D"
        FormatsSessionMovieItem.XTREME -> "XTREME"
        FormatsSessionMovieItem.SCREENX -> "SCREENX"
        FormatsSessionMovieItem.VIP -> "VIP"
    }
}

enum class LanguageSessionMovieItem {
    DOBLADA,
    SUBTITULADO
}
