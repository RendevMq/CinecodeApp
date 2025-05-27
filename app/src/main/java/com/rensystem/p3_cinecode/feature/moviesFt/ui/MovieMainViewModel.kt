package com.rensystem.p3_cinecode.feature.moviesFt.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase.GetComingSoonMovies
import com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase.GetNowShowingMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieMainViewModel @Inject constructor(
    private val getComingSoonMovies: GetComingSoonMovies,
    private val getNowShowingMovies: GetNowShowingMovies
):ViewModel(){

    private val _uiState = MutableStateFlow<MovieUIPagerState>(MovieUIPagerState.Loading)
    val uiState: StateFlow<MovieUIPagerState> = _uiState

    init {
        loadTasks() // Cargar tareas cuando el ViewModel se crea
    }

    private fun loadTasks() {
        viewModelScope.launch {
            try {
                Log.d("MovieDebug", "Iniciando carga de películas")

                // Combina los dos flows y espera que ambos emitan para continuar
                combine(
                    getNowShowingMovies(), // Flow<List<MovieHomeItem>>
                    getComingSoonMovies()  // Flow<List<MovieHomeItem>>
                ) { nowShowing, onRelease ->
                    MovieUIPagerState.Success(
                        nowShowingMovies = nowShowing,
                        comingSoonMovies = onRelease
                    )
                }.collect { combinedState ->
                    Log.d("MovieDebug", "Películas actuales: ${combinedState.nowShowingMovies.size}")
                    Log.d("MovieDebug", "Próximos estrenos: ${combinedState.comingSoonMovies.size}")
                    _uiState.value = combinedState
                }

            } catch (e: Exception) {
                Log.e("MovieDebug", "Error al cargar películas", e)
                _uiState.value = MovieUIPagerState.Error(e)
            }
        }
    }

}