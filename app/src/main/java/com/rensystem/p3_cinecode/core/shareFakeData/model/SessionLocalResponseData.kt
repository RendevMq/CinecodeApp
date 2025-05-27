package com.rensystem.p3_cinecode.core.shareFakeData.model

import com.google.gson.annotations.SerializedName
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.FormatsSessionMovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.LanguageSessionMovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.SessionMovieItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.SessionTheaterItem

data class SessionLocalResponseData (
    val sessions: List<SessionData>? = null
)

data class SessionData (
    val id: String? = null,
    val showtime: String? = null,
    val screenName: String? = null,
    val formats: List<FormatSessionData>? = null,
    val languages: List<LanguageSessionData>? = null,
    @SerializedName("sessionId")
    val sessionID: String? = null
){
    fun toTheaterDomain(): SessionTheaterItem{
        return SessionTheaterItem(
            id = id ?: "",
            showtime = showtime ?: "No data",
            sessionID = sessionID ?: ""
        )
    }

    fun toMovieDomain(): SessionMovieItem{
        return SessionMovieItem(
            id = id ?: "",
            showtime = showtime ?: "",
            formats = formats?.mapNotNull { it?.toMovieDomain() } ?: emptyList(),
            languages = languages?.mapNotNull { it.toMovieDomain() } ?: emptyList(),
            screenName = screenName ?: "",
            sessionID = sessionID ?: ""
        )
    }
}

enum class FormatSessionData {
    REGULAR,
    @SerializedName("2D")
    THE2D,
    PRIME,
    @SerializedName("3D")
    THE3D,
    XTREME,
    SCREENX,
    VIP
}

fun FormatSessionData.toMovieDomain(): FormatsSessionMovieItem? = when(this){
    FormatSessionData.REGULAR -> FormatsSessionMovieItem.REGULAR
    FormatSessionData.THE2D -> FormatsSessionMovieItem.THE2D
    FormatSessionData.PRIME -> FormatsSessionMovieItem.PRIME
    FormatSessionData.THE3D -> FormatsSessionMovieItem.THE3D
    FormatSessionData.XTREME -> FormatsSessionMovieItem.XTREME
    FormatSessionData.SCREENX -> FormatsSessionMovieItem.SCREENX
    FormatSessionData.VIP -> FormatsSessionMovieItem.VIP
}


enum class LanguageSessionData {
    DOBLADA,
    SUBTITULAD
}

fun LanguageSessionData.toMovieDomain(): LanguageSessionMovieItem?=when(this){
    LanguageSessionData.DOBLADA -> LanguageSessionMovieItem.DOBLADA
    LanguageSessionData.SUBTITULAD -> LanguageSessionMovieItem.SUBTITULADO
}