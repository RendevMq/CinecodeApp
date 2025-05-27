package com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase

import com.rensystem.p3_cinecode.feature.moviesFt.domain.MovieRepository
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.SessionMovieItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.TheaterRepository
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.SessionTheaterItem
import javax.inject.Inject

class GetAllSessions @Inject constructor(
    private val movieRepository: MovieRepository
){
    suspend operator fun invoke(): List<SessionMovieItem>{
        return movieRepository.getAllSessions()
    }
}

