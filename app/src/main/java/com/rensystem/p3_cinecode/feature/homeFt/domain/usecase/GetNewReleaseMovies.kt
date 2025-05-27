package com.rensystem.p3_cinecode.feature.homeFt.domain.usecase

import com.rensystem.p3_cinecode.feature.homeFt.domain.MovieHomeRepository
import com.rensystem.p3_cinecode.feature.homeFt.domain.model.MovieHomeItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class  GetNewReleaseMovies @Inject constructor(
    private val movieRepository: MovieHomeRepository
) {
    suspend operator fun invoke(): Flow<List<MovieHomeItem>> {
        return movieRepository.getNewReleaseMovies().map{
            movies ->
            movies.sortedByDescending { it.trailer != "" }
        }
    }
}
