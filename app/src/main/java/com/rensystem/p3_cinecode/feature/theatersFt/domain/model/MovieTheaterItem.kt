package com.rensystem.p3_cinecode.feature.theatersFt.domain.model

import javax.annotation.meta.When

data class MovieTheaterItem(
    val id: String,
    val isComingSoon: Boolean,
    val openingDate: String,
    val restricted: Boolean,
    val genre: String,
    val genreID: String,
    val isNewRelease: Boolean,
    val isPreSale: Boolean,
    val isRePremiere: Boolean,
    val isRemake: Boolean,
    val languages: List<LanguageTheaterItem>,
    val posterURL: String,
    val runTime: Long,
    val title: String,
    val trailer: String,
    val cinemas: List<CinemaTheaterItem>,
    val formats: List<FormatsTheaterItem>,
    val ratingDesc: RatingDescTheaterItem,
    val showTime: List<SessionTheaterItem>?=null,
)

enum class FormatsTheaterItem {
    REGULAR,
    THE2D,
    PRIME,
    THE3D,
    XTREME
}

//FUNCTION EXTENSION
fun FormatsTheaterItem.getLabel():String{
    return when (this){
        FormatsTheaterItem.REGULAR -> "REGULAR"
        FormatsTheaterItem.THE2D -> "2D"
        FormatsTheaterItem.PRIME -> "PRIME"
        FormatsTheaterItem.THE3D -> "3D"
        FormatsTheaterItem.XTREME -> "XTREME"
    }
}

enum class RatingDescTheaterItem{
    Apt,
    The14,
    The14Dni,
    NoRate
}

fun RatingDescTheaterItem.getLabel():String{
    return when (this){
        RatingDescTheaterItem.Apt -> "APT"
        RatingDescTheaterItem.The14 -> "+14"
        RatingDescTheaterItem.The14Dni -> "+14 DNI"
        RatingDescTheaterItem.NoRate -> " - "
    }
}

data class CinemaTheaterItem (
    val cinemaID: String,
    val dates: List<DateTheaterItem>
)

data class DateTheaterItem (
    val date: String,
    val sessions: List<String>
)

enum class LanguageTheaterItem {
    DOBLADA,
    SUBTITULADO
}
