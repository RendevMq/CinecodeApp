package com.rensystem.p3_cinecode.feature.theatersFt.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.DateTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.MovieTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.SessionTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.TheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.getFormatLabels
import com.rensystem.p3_cinecode.feature.theatersFt.domain.usecase.GetAllMovies
import com.rensystem.p3_cinecode.feature.theatersFt.domain.usecase.GetAllSessions
import com.rensystem.p3_cinecode.feature.theatersFt.domain.usecase.GetAllTheaters
import com.rensystem.p3_cinecode.feature.theatersFt.domain.usecase.GetMoviesByTheaterIdUseCase
import com.rensystem.p3_cinecode.feature.theatersFt.domain.usecase.GetSessionsByMovieAndDateUseCase
import com.rensystem.p3_cinecode.feature.theatersFt.domain.usecase.GetUniqueShowtimeDatesByTheater
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TheaterMainViewModel @Inject constructor(
    private val getAllTheaters: GetAllTheaters,
    private val getMoviesByTheaterIdUseCase: GetMoviesByTheaterIdUseCase,
    private val getAllMovies: GetAllMovies,
    private val getSessionsByMovieAndDateUseCase: GetSessionsByMovieAndDateUseCase,
    private val getAllSessions: GetAllSessions,
    private val getUniqueShowtimeDatesByTheater: GetUniqueShowtimeDatesByTheater
) : ViewModel() {


    private val _uiState = MutableStateFlow<TheaterUIState>(TheaterUIState.Loading)
    val uiState: StateFlow<TheaterUIState> = _uiState

    //Temporal
    private val _favoritesTheaters = MutableStateFlow<List<TheaterItem>>(emptyList())
    val favoritesTheaters: StateFlow<List<TheaterItem>> = _favoritesTheaters

    private val _theaterDetailUiState = MutableStateFlow<TheaterDetailUIState>(TheaterDetailUIState.Loading)
    val theaterDetailUiState: StateFlow<TheaterDetailUIState> = _theaterDetailUiState

    private val _dateSelected = MutableStateFlow<String>("2025-05-06")
    val dateSelected: StateFlow<String> = _dateSelected


    private var cachedAllSessions: List<SessionTheaterItem> = emptyList()
    private var cachedAllMovies: List<MovieTheaterItem> = emptyList()
    private var cachedUniqueDates: List<String> = emptyList()
    private var cachedTheaterId: String = ""


    init {
        loadTasks() // Cargar tareas cuando el ViewModel se crea
    }

    private fun loadTasks() {
        viewModelScope.launch {
            // Llamamos a la función suspendida correctamente dentro de una corrutina
            try {
                Log.d("MovieDebug", "Iniciando carga de películas")
                getAllTheaters().collect { theater ->
                    Log.d("MovieDebug", "Cinemas cargadas: ${theater.size}")
                    theater.forEach { theater ->
                        Log.d("MovieDebug", "Película: ${theater.getFormatLabels()}")
                    }
                    _uiState.value =
                        TheaterUIState.Success(theater) // Emitimos el estado de éxito con la lista de tareas
                }

            } catch (e: Exception) {
                Log.e("MovieDebug", "Error al cargar películas", e)
                _uiState.value = TheaterUIState.Error(e)
            }
        }
    }

    fun toggleFavoritesStatus(theaterItem: TheaterItem) {

        val currentState = _uiState.value
        if (currentState is TheaterUIState.Success){
            val updatedList = currentState.theaters.map{
                if(it.id == theaterItem.id) it.copy(isFavorite = !it.isFavorite) else it
            }
            _uiState.value = TheaterUIState.Success(updatedList)
        }

    }

    fun loadMovieSessionsForTheaterDetail(theaterId: String) {
        viewModelScope.launch {
            _theaterDetailUiState.value = TheaterDetailUIState.Loading
            try {
                //                val today = LocalDate.now().toString() // puedes ajustar formato
                val today = _dateSelected.value
                val allSessions = getAllSessions()
                val uniqueDates = getUniqueShowtimeDatesByTheater(theaterId,allSessions)
                val allMovies = getAllMovies()


                val movieList = getMoviesByTheaterIdUseCase(
                    theaterId = theaterId,
                    date = today,
                    allMovies = allMovies
                )

                val enrichedList = movieList.map { movie ->
                    val sessions = getSessionsByMovieAndDateUseCase(movie, theaterId, today,allSessions)
                    movie.copy(showTime = sessions)
                }
                // ⏺ Cacheamos todoo
                cachedTheaterId = theaterId
                cachedAllSessions = allSessions
                cachedAllMovies = allMovies
                cachedUniqueDates = uniqueDates

                _theaterDetailUiState.value = TheaterDetailUIState.Success(
                    moviesWithSessions = enrichedList,
                    availableDates = uniqueDates
                )

            } catch (e: Exception) {
                _theaterDetailUiState.value = TheaterDetailUIState.Error(e)
            }
        }
    }

    fun changueSelectedDate(newDate: String) {
        _dateSelected.value = newDate

        viewModelScope.launch {
            val today = _dateSelected.value

            if (cachedTheaterId.isNotEmpty() &&
                cachedAllMovies.isNotEmpty() &&
                cachedAllSessions.isNotEmpty()
            ) {
                val movieList = getMoviesByTheaterIdUseCase(
                    theaterId = cachedTheaterId,
                    date = today,
                    allMovies = cachedAllMovies
                )

                val enrichedList = movieList.map { movie ->
                    val sessions = getSessionsByMovieAndDateUseCase(movie, cachedTheaterId, today, cachedAllSessions)
                    movie.copy(showTime = sessions)
                }

                _theaterDetailUiState.value = TheaterDetailUIState.Success(
                    moviesWithSessions = enrichedList,
                    availableDates = cachedUniqueDates
                )
            }
        }
    }
}