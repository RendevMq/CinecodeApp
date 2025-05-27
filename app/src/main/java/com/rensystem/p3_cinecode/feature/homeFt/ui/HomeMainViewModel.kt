package com.rensystem.p3_cinecode.feature.homeFt.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rensystem.p3_cinecode.feature.homeFt.domain.model.getFormatLabels
import com.rensystem.p3_cinecode.feature.homeFt.domain.usecase.GetNewReleaseMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMainViewModel @Inject constructor(
    private val getNewReleaseMovies: GetNewReleaseMovies
) : ViewModel(){

    private val _uiState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        loadTasks() // Cargar tareas cuando el ViewModel se crea
    }

    private fun loadTasks() {
        viewModelScope.launch {
            // Llamamos a la función suspendida correctamente dentro de una corrutina
            try {
                Log.d("MovieDebug", "Iniciando carga de películas")
                getNewReleaseMovies().collect { movie ->
                    Log.d("MovieDebug", "Películas cargadas: ${movie.size}")
                    movie.forEach { movie ->
                        Log.d("MovieDebug", "Película: ${movie.getFormatLabels()}")
                    }
                    _uiState.value = HomeUIState.Success(movie) // Emitimos el estado de éxito con la lista de tareas
                }

            } catch (e: Exception) {
                Log.e("MovieDebug", "Error al cargar películas", e)
                _uiState.value = HomeUIState.Error(e)
            }
        }
    }
}