package com.rensystem.p3_cinecode.feature.homeFt.domain

import com.rensystem.p3_cinecode.feature.homeFt.domain.model.MovieHomeItem
import kotlinx.coroutines.flow.Flow

interface MovieHomeRepository{
    suspend fun getNewReleaseMovies(): Flow<List<MovieHomeItem>>
}