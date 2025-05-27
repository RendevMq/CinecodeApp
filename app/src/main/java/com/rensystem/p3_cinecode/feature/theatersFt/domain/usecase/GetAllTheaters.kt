package com.rensystem.p3_cinecode.feature.theatersFt.domain.usecase

import com.rensystem.p3_cinecode.feature.theatersFt.domain.TheaterRepository
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.TheaterItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTheaters @Inject constructor(
    private val theaterRepository: TheaterRepository
) {
    suspend operator fun invoke(): Flow<List<TheaterItem>> {
        return theaterRepository.getAllTheater()
    }
}