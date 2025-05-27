package com.rensystem.p3_cinecode.feature.theatersFt.domain.usecase

import com.rensystem.p3_cinecode.feature.theatersFt.domain.TheaterRepository
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.MovieTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.SessionTheaterItem
import javax.inject.Inject


class GetAllMovies @Inject constructor(
    private val theaterRepository: TheaterRepository
){
    suspend operator fun invoke(): List<MovieTheaterItem>{
        return theaterRepository.getAllMovies()
    }
}

