package com.rensystem.p3_cinecode.core.shareFakeData.model

import com.google.gson.annotations.SerializedName
import com.rensystem.p3_cinecode.feature.homeFt.domain.model.MovieHomeItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.CinemaMovieDetailItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.CinemaMovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.DateMovieDetailItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieDetailItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import com.rensystem.p3_cinecode.feature.theatersFt.data.model.FormatTheaterData
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.CinemaTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.DateTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.FormatsTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.LanguageTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.MovieTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.RatingDescTheaterItem

//MovieWebMockResponse
//🧠 Notas importantes:
//-Se usan orEmpty() y elvis ?: para evitar valores nulos.
//
//-Se hace mapNotNull en las listas para prevenir valores nulos que puedan romper la lógica.
//
//-Los enums están mapeados 1:1, por lo que se pueden transformar directamente.

data class MovieLocalResponseData(
    val idMoviesBookingRestricted: List<String>? = null,
    val movies: List<MovieData>? = null
)

data class MovieData(
    val id: String? = null,
    val cast: CastData? = null,
    val director: DirectorData? = null,
    val formats: List<FormatData?>? = null,
    val gallery: GalleryData? = null,
    val isComingSoon: Boolean? = null,
    @SerializedName("OpeningDate")
    val openingDate: String? = null,
    val restricted: Boolean? = null,
    val genre: String? = null,
    @SerializedName("genreId")
    val genreID: String? = null,
    val isNewRelease: Boolean? = null,
    val isPreSale: Boolean? = null,
    val isRePremiere: Boolean? = null,
    val isRemake: Boolean? = null,
    val festival: FestivalData? = null,
    val languages: List<LanguageData>? = null,
    @SerializedName("posterUrl")
    val posterURL: String? = null,
    val ratingDescription: RatingDescriptionData? = null,
    val runTime: Long? = null,
    val synopsis: String? = null,
    @SerializedName("thumbnailUrl")
    val thumbnailURL: List<String>? = null,
    val title: String? = null,
    val trailer: String? = null,
    val cinemas: List<CinemaData>? = null,
    @SerializedName("movieDetailsUrl")
    val movieDetailsURL: String? = null
) {
    fun toHomeDomain(): MovieHomeItem {
        return MovieHomeItem(
            id = id ?: "",
            title = title ?: "",
            genre = genre ?: "",
            trailer = trailer ?: "",
            format = formats?.filterNotNull()?.map { it.name } ?: emptyList(),
            ratingDescription = ratingDescription?.name ?: "",
            posterPath = posterURL ?: "",
            releaseDate = openingDate ?: "",
            overview = synopsis ?: "",
            runtime = runTime?.toDouble() ?: 0.0
        )
    }

    fun toMovieDomain(): MovieItem {
        return MovieItem(
            id = id ?: "",
            title = title ?: "",
            genre = genre ?: "",
            format = formats?.filterNotNull()?.map { it.name } ?: emptyList(),
            ratingDescription = ratingDescription?.name ?: "",
            posterPath = posterURL ?: "",
            runtime = runTime?.toDouble() ?: 0.0,
            language = languages?.filterNotNull()?.map { it.name } ?: emptyList(),
            isComingSoon = isComingSoon ?: false,
            isPreSale = isPreSale ?: false,
            isRePremiere = isRePremiere ?: false,
            isRemake = isRemake ?: false,
            isNewRelease = isNewRelease ?: false,
            trailer = trailer ?: "",
            overview = synopsis ?: "",
        )
    }

    fun toMovieDetailDomain(): MovieDetailItem {
        return MovieDetailItem(
            id = id ?: "",
            title = title ?: "",
            genre = genre ?: "",
            format = formats?.filterNotNull()?.map { it.name } ?: emptyList(),
            ratingDescription = ratingDescription?.name ?: "",
            posterPath = posterURL ?: "",
            runtime = runTime?.toDouble() ?: 0.0,
            language = languages?.filterNotNull()?.map { it.name } ?: emptyList(),
            isComingSoon = isComingSoon ?: false,
            isPreSale = isPreSale ?: false,
            isRePremiere = isRePremiere ?: false,
            isRemake = isRemake ?: false,
            isNewRelease = isNewRelease ?: false,
            trailer = trailer ?: "",
            overview = synopsis ?: "",
            cinemas = cinemas?.filterNotNull()?.map {it.toMovieItemDomain()} ?: emptyList(),
        )
    }

    fun toTheaterDomain(): MovieTheaterItem {
        return MovieTheaterItem(
            id = id.orEmpty(),
            isComingSoon = isComingSoon ?: false,
            openingDate = openingDate.orEmpty(),
            restricted = restricted ?: false,
            genre = genre.orEmpty(),
            genreID = genreID.orEmpty(),
            isNewRelease = isNewRelease ?: false,
            isPreSale = isPreSale ?: false,
            isRePremiere = isRePremiere ?: false,
            isRemake = isRemake ?: false,
            languages = languages?.mapNotNull { it.toTheaterDomain() } ?: emptyList(),
            posterURL = posterURL.orEmpty(),
            runTime = runTime ?: 0L,
            title = title.orEmpty(),
            trailer = trailer.orEmpty(),
            cinemas = cinemas?.map { it.toTheaterDomain() } ?: emptyList(),
            formats = formats?.mapNotNull { it?.toTheaterDomain() } ?: emptyList(),
            ratingDesc = ratingDescription?.toTheaterDomain() ?: RatingDescTheaterItem.NoRate,
        )
    }

}

data class CastData(
    val cast: List<Any?>? = null
)

data class CinemaData(
    @SerializedName("cinemaId")
    val cinemaID: String? = null,
    val dates: List<DateData>? = null
) {
    fun toTheaterDomain(): CinemaTheaterItem {
        return CinemaTheaterItem(
            cinemaID = cinemaID.orEmpty(),
            dates = dates?.mapNotNull { it.toTheaterDomain() } ?: emptyList()
        )
    }

    fun toMovieItemDomain(): CinemaMovieDetailItem {
        return CinemaMovieDetailItem(
            cinemaID = cinemaID.orEmpty(),
            dates = dates?.mapNotNull { it.toMovieItemDomain() } ?: emptyList()
        )
    }
}

data class DateData(
    val date: String? = null,
    val sessions: List<String>? = null
) {
    fun toTheaterDomain(): DateTheaterItem {
        return DateTheaterItem(
            date = date.orEmpty(),
            sessions = sessions ?: emptyList()
        )
    }
    fun toMovieItemDomain(): DateMovieDetailItem {
        return DateMovieDetailItem(
            date = date.orEmpty(),
            sessions = sessions ?: emptyList()
        )
    }
}

enum class DirectorData {
    Null
}

enum class FestivalData {
    Empty,
    FestCineFrances
}


data class GalleryData(
    val images: List<Any?>? = null
)

enum class RatingDescriptionData {
    @SerializedName("APT")
    Apt,

    @SerializedName("+14")
    The14,

    @SerializedName("+14 DNI")
    The14Dni
}

fun RatingDescriptionData.toTheaterDomain(): RatingDescTheaterItem? = when (this) {
    RatingDescriptionData.Apt -> RatingDescTheaterItem.Apt
    RatingDescriptionData.The14 -> RatingDescTheaterItem.The14
    RatingDescriptionData.The14Dni -> RatingDescTheaterItem.The14Dni
    else -> null
}


enum class FormatData {
    REGULAR,
    THE2D,
    PRIME,
    THE3D,
    XTREME
}

fun FormatData.toTheaterDomain(): FormatsTheaterItem? = when (this) {
    FormatData.REGULAR -> FormatsTheaterItem.REGULAR
    FormatData.THE2D -> FormatsTheaterItem.THE2D
    FormatData.PRIME -> FormatsTheaterItem.PRIME
    FormatData.THE3D -> FormatsTheaterItem.THE3D
    FormatData.XTREME -> FormatsTheaterItem.XTREME
    else -> null
}

enum class LanguageData {
    DOBLADA,
    SUBTITULAD
}

fun LanguageData.toTheaterDomain(): LanguageTheaterItem? = when (this) {
    LanguageData.DOBLADA -> LanguageTheaterItem.DOBLADA
    LanguageData.SUBTITULAD -> LanguageTheaterItem.SUBTITULADO
    else -> null
}


