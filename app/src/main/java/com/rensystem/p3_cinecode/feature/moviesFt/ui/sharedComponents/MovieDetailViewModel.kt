package com.rensystem.p3_cinecode.feature.moviesFt.ui.sharedComponents

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.Actor
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieDetailItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.SessionMovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.TheaterMovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase.GetAllMovies
import com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase.GetAllSessions
import com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase.GetAllTheaters
import com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase.GetMovieById
import com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase.GetTheatersWithSessionsUseCase
import com.rensystem.p3_cinecode.feature.moviesFt.domain.usecase.GetTmdbDetailsUseCase
import com.rensystem.p3_cinecode.feature.moviesFt.ui.MovieDetailUIPagerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getAllMovies: GetAllMovies,
    private val getAllTheaters: GetAllTheaters,
    private val getAllSessions: GetAllSessions,
    private val getMovieById: GetMovieById,
    private val getTmdbDetailsUseCase: GetTmdbDetailsUseCase,
    private val getTheatersWithSessionsUseCase: GetTheatersWithSessionsUseCase,
) : ViewModel(){
    private val _uiState = MutableStateFlow<MovieDetailUIPagerState>(MovieDetailUIPagerState.Loading)
    val uiState: StateFlow<MovieDetailUIPagerState> = _uiState

    private val _dateSelected = MutableStateFlow<String>("2025-05-07")
    val dateSelected: StateFlow<String> = _dateSelected

    private var cachedAllSessions: List<SessionMovieItem> = emptyList()
    private var cachedAllMovies: List<MovieDetailItem> = emptyList()
    private var cachedAllTheaters: List<TheaterMovieItem> = emptyList()

    private var isDataLoaded = false

    //METODO TEMPORAL
   fun loadMovieDetailData(movieItem: MovieItem) {
       //Si quisieras evitar que se llame a initWithMovieItem() varias veces con la misma película (por ejemplo, cuando el usuario vuelve hacia atrás y vuelve a entrar a la misma película), podrías comparar los objetos:
        val currentState = _uiState.value
        if (currentState is MovieDetailUIPagerState.Success && currentState.selectedMovie.id == movieItem.id) {
            return // Ya está cargado
        }

        viewModelScope.launch {
            _uiState.value = MovieDetailUIPagerState.Loading
            delay(1500)
            try {

                val today = _dateSelected.value
                val allSessions = getAllSessions()
                val allMovies = getAllMovies()
                val allTheaters = getAllTheaters()

                // ⏺ Cacheamos todoo
                cachedAllSessions = allSessions
                cachedAllTheaters= allTheaters
                cachedAllMovies = allMovies

                val movieWithCinemas = getMovieById(
                    movieId = movieItem.id,
                    allMovies = cachedAllMovies
                )

                var movieFull = getTmdbDetailsUseCase(
                    movie = movieWithCinemas!!
                )

                if (movieFull.cast.isNullOrEmpty()) {
                    movieFull = movieFull.copy(
                        cast = fakeCastList()
                    )
                }

//                val movieFull = movieWithCinemas!!.copy(
//                    cast = fakeCastList()
//                )

//                if (!isDataLoaded) {
//                    allSessions = getAllSessions()
//                    allMovies = getAllMovies()
//                    allTheaters = getAllTheaters()
//                    isDataLoaded = true
//                }

                // Lógica de dominio: usamos el caso de uso
                val theatersWithSessions = getTheatersWithSessionsUseCase(
                    movie = movieFull,
                    date = today,
                    allTheaters = cachedAllTheaters,
                    allSessions = cachedAllSessions
                )

                _uiState.value = MovieDetailUIPagerState.Success(
                    movieFull,
                    theatersWithSessions
                )
            } catch (e: Exception) {
                _uiState.value = MovieDetailUIPagerState.Error(e)
            }
        }
   }

    private fun fakeCastList(): List<Actor> {
        return listOf(
            Actor(
                id = 1,
                name = "Anthony Mackie",
                character = "Ty (voice)",
                profilePath = "/9r3bvl2pBZwZMN3KUg43Sp8c7ZU.jpg"
            ),
            Actor(
                id = 2,
                name = "Martin Lawrence",
                character = "J.B. (voice)",
                profilePath = "/y3SQzIPUPJpdueb1DkbTYph68nk.jpg"
            ),
            Actor(
                id = 3,
                name = "Swae Lee",
                character = "Edson (voice)",
                profilePath = "/p7713cOM4HyHNJf8TTkviRj7F0K.jpg"
            ),
            Actor(
                id = 4,
                name = "Chloe Bailey",
                character = "Maxine (voice)",
                profilePath = "/mZxukGTNv2FEZxWvnLGgBRpyMwr.jpg"
            ),
            Actor(
                id = 5,
                name = "Emilia Clarke",
                character = "Daenerys Targaryen",
                profilePath = "/86jeYFV40KctQMDQIWhJ5oviNGj.jpg"
            ),
            Actor(
                id = 6,
                name = "Macy GraY",
                character = "Adriana (voice)",
                profilePath = "/p65H5JEEbBvcKsBt2FVDFOlZj3O.jpg"
            ),
            Actor(
                id = 7,
                name = "Peter Dinklage",
                character = "Tyrion Lannister",
                profilePath = "/7leHwU2cVxE6h4MjKoo3WwYdQZy.jpg"
            ),
            Actor(
                id = 8,
                name = "Ella Mai",
                character = "Britany (voice)",
                profilePath = "/p3VHXnwkVzw2Qnai6YPMpfdzfzi.jpg"
            ),
            Actor(
                id = 9,
                name = "Mustard",
                character = "Marcel, Mustard (voice)",
                profilePath = "/gU9zOQfUbFPoZoHzeDzPueRA1Fo.jpg"
            ),
            Actor(
                id = 10,
                name = "Roddy Ricch",
                character = "The Forger (voice)",
                profilePath = "/ee5lDLhWG5G8Hfi7GUzbtskqKU0.jpg"
            ),
        )
    }

    //METODO CAMBIAR NOMBRE(ESTE ES EL VERDADERO CON LAS LALAMDAS A ALA PI, DE MODMENTO DEJARE CON EL DE ARRIBA):
//    fun initWithMovieItem(movieItem: MovieItem) {
//        _uiState.value = MovieDetailUIPagerState.Success(movieItem)
//            //AQUI LUEGO IRIA LA FUNCION PARA GUARDAR EL  val theatersWithSessions: List<TheaterMovieItem>, DE UISTATE
//    }

}