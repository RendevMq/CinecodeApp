package com.rensystem.p3_cinecode.feature.moviesFt.domain.model

data class TheaterMovieItem (
    val id: String, //yes
    val name: String, //yes
    val address: String, //yes
    val city: String, //yes
//    val cinemas: List<CinemaTheaterMovieItem>,
    var isFavorite: Boolean = false,
    val showTime: List<SessionMovieItem>?=null,
    )

//data class CinemaTheaterMovieItem (
//    val cinemaID: String,
//    val dates: List<DateTheaterMovieItem>
//)
//
//data class DateTheaterMovieItem (
//    val date: String,
//    val sessions: List<String>
//)
//
//enum class LanguageTheaterMovieItem {
//    DOBLADA,
//    SUBTITULADO
//}