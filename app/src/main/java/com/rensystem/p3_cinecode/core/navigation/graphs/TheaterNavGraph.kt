package com.rensystem.p3_cinecode.core.navigation.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.rensystem.p3_cinecode.core.navigation.Graph
import com.rensystem.p3_cinecode.core.navigation.TheatersRouteScreen
import com.rensystem.p3_cinecode.feature.theatersFt.ui.TheaterDetailScreen
import com.rensystem.p3_cinecode.feature.theatersFt.ui.TheaterMainViewModel
import com.rensystem.p3_cinecode.feature.theatersFt.ui.TheaterUIState
import kotlinx.coroutines.flow.map

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.theaterNavGraph(
    rootNavController: NavHostController,
    theaterMainViwModel: TheaterMainViewModel
) {
    composable<TheatersRouteScreen.TheaterDetail>() { backStackEntry ->
        val args = backStackEntry.toRoute<TheatersRouteScreen.TheaterDetail>()
        val theaterId = args.theaterId

        val theaterItem by theaterMainViwModel.uiState
            .map { state ->
                if (state is TheaterUIState.Success) {
                    state.theaters.find { it.id == theaterId }
                } else null
            }
            .collectAsState(initial = null)

        val theaterDetailUiState by theaterMainViwModel.theaterDetailUiState.collectAsState()
        val dateSelected by theaterMainViwModel.dateSelected.collectAsState()

        LaunchedEffect(theaterId) {
            theaterMainViwModel.loadMovieSessionsForTheaterDetail(theaterId)
        }

        theaterItem?.let {
            TheaterDetailScreen(
                uistate = theaterDetailUiState, // 👈 PASAS SOLO EL UISTATE
                theaterItem = it,
                onToggleFavoriteIcon = {
                    theaterMainViwModel.toggleFavoritesStatus(it)
                },
                onBackClick = {
                    rootNavController.navigateUp()
                },
                dateSelected = dateSelected,
                onChangueDate = {
                    theaterMainViwModel.changueSelectedDate(it)
                },
            )
        }
    }
}
