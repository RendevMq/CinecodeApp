package com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase

import com.rensystem.p3_cinecode.feature.moviesFt.domain.MovieRepository
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieDetailItem
import javax.inject.Inject

class GetTmdbDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: MovieDetailItem): MovieDetailItem {
        // 1. Buscar el ID en TMDB
        val tmdbId = repository.getTmdbMovieIdByName(movie.title)

        // 2. Si no se encuentra ID, retornar el objeto original sin cambios
        if (tmdbId == null) return movie

        // 3. Obtener géneros y cast
        val genres = repository.getGenresFromTmdb(tmdbId)
        val cast = repository.getCastFromTmdb(tmdbId)

        // 4. Retornar nuevo objeto con campos actualizados
        return movie.copy(
            secondaryGenres = genres,
            cast = cast
        )
    }
}
