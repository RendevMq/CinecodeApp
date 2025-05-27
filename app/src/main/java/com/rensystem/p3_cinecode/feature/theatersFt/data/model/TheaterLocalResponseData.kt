package com.rensystem.p3_cinecode.feature.theatersFt.data.model

import com.google.gson.annotations.SerializedName
import com.rensystem.p3_cinecode.feature.homeFt.domain.model.MovieHomeItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.TheaterMovieItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.TheaterItem

data class TheaterLocalResponseData (
    val cinemas: List<CinemaData>? = null
)

data class CinemaData (
    @SerializedName("ID")
    val id: String? = null, //yes
    val name: String? = null, //yes
    val phoneNumber: String? = null,
    val emailAddress: String? = null,
    val address: String? = null, //yes
    val secondAddress: String? = null,
    val city: String? = null, //yes
    val latitude: String? = null,//yes
    val longitude: String? = null,//yes
    val description: String? = null,//yes
    val showroomQuantity: Long? = null,
    val formattedCinemaName: String? = null,
    val services: List<ServiceTheaterData>? = null,
    val formatsRate: List<Any?>? = null,
    val formats: List<FormatTheaterData>? = null, //yes
    val img: String? = null, //yes
    val loyaltyCode: String? = null,
    val showTabsPrime: Boolean? = null
) {
    fun toTheaterDomain(): TheaterItem {
        return TheaterItem(
            id = id ?: "",
            name = name ?: "",
            address = address ?: "",
            city = city ?: "",
            latitude = latitude ?: "",
            longitude = longitude ?: "",
            description = description ?: "",
            formats = formats?.filterNotNull()?.map { it.name } ?: emptyList(),
            img = img ?: "",
            showroomQuantity = showroomQuantity ?: 0,
            phoneNumber = phoneNumber ?: "",
        )
    }
    fun toMovieDomain(): TheaterMovieItem {
        return TheaterMovieItem(
            id = id ?: "",
            name = name ?: "",
            address = address ?: "",
            city = city ?: "",
        )
    }
}

enum class FormatTheaterData {
    PRIME,
    REGULAR,
    SCREENX,
    @SerializedName("2D")
    THE2D,
    @SerializedName("3D")
    THE3D,
    VIP,
    XTREME
}

data class ServiceTheaterData (
    val name: String? = null,
    val icon: String? = null,
    val description: String? = null
)
