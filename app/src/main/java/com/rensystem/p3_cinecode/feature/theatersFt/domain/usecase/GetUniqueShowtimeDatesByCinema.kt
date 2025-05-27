package com.rensystem.p3_cinecode.feature.theatersFt.domain.usecase

import com.rensystem.p3_cinecode.feature.theatersFt.domain.TheaterRepository
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.SessionTheaterItem
import javax.inject.Inject

class GetUniqueShowtimeDatesByTheater @Inject constructor(
    private val theaterRepository: TheaterRepository
) {
    suspend operator fun invoke(
        theaterId: String,
        allSessions: List<SessionTheaterItem>
    ) : List<String> {
        return theaterRepository.getUniqueShowtimeDatesByCinema(
            theaterId = theaterId,
            allSessions = allSessions
        )
    }
}
