package com.rensystem.p3_cinecode.feature.theatersFt.ui

import com.rensystem.p3_cinecode.feature.homeFt.domain.model.MovieHomeItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.TheaterItem


sealed interface TheaterUIState {
    object Loading: TheaterUIState
    data class Error(val throwable: Throwable):TheaterUIState
    data class Success(val theaters: List<TheaterItem>) : TheaterUIState
}