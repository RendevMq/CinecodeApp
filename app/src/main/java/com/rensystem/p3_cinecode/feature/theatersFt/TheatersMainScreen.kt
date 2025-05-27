package com.rensystem.p3_cinecode.feature.theatersFt

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.AsyncImage
import com.rensystem.p3_cinecode.core.shareComponents.FilterOptionsRow
import com.rensystem.p3_cinecode.core.utils.TheaterFilters
import com.rensystem.p3_cinecode.feature.homeFt.ui.components.GradientOverlay
import com.rensystem.p3_cinecode.feature.moviesFt.GradientBackgroundBrush
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.TheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.ui.TheaterMainViewModel
import com.rensystem.p3_cinecode.feature.theatersFt.ui.TheaterUIState
import com.rensystem.p3_cinecode.ui.theme.LocalBrandColors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TheatersMainScreen(
    innerPadding: PaddingValues,
    theaterMainViewModel: TheaterMainViewModel,
    navigateToDetail: (TheaterItem) -> Unit
) {
    val customColors = LocalBrandColors.current

        val gradientColorList = listOf(
        Color(0xFF262626), // 38
        Color(0xFF232323), // 38
        Color(0xFF212121), // 33
        Color(0xFF1C1C1C), // 28
        Color(0xFF181818)  // 24
    )


//    val gradientColorList = listOf(
//        Color(0xFF282828), // 43
//        Color(0xFF262626), // 38
//        Color(0xFF212121), // 33
//        Color(0xFF1C1C1C), // 28
//        Color(0xFF181818)  // 24
//    )


//    val gradientColorList = listOf(
//        Color(0xFF2B2B2B), // gris muy oscuro
//        Color(0xFF262626), // un poco más oscuro
//        Color(0xFF212121), // típico de fondo dark
//        Color(0xFF1C1C1C), // más profundo
//        Color(0xFF181818)  // sin llegar a negro puro
//    )

    //Creamos un ciclo de vida de la screen
    //Los state flow son los que van par ala UI, debemos crear un snapshot state
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState: TheaterUIState by produceState<TheaterUIState>(
        initialValue = TheaterUIState.Loading, // Asegúrate de que esto sea de tipo Loading
        key1 = lifecycle,
        key2 = theaterMainViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            // Recolectamos el estado de uiState
            theaterMainViewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is TheaterUIState.Error -> {}
        is TheaterUIState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(70.dp)
                    .padding(8.dp),
                strokeWidth = 2.dp
            )
        }

        is TheaterUIState.Success -> {

            val theaterList = (uiState as TheaterUIState.Success).theaters
            val listState =
                rememberLazyListState() //Esto crea un estado que nos permite observar el desplazamiento (scroll) de la LazyColumn.
            val favoriteTheaters =
                (uiState as TheaterUIState.Success).theaters.filter { it.isFavorite }

            CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(
                            Color(0xF5202020)
                        ),
//                        .background(
//                            brush = GradientBackgroundBrush(
//                                isVerticalGradient = true,
//                                colors = gradientColorList
//                            )
//                        ),
                    horizontalAlignment = Alignment.Start,
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(customColors.bottomBarBackgroundColor)
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Theaters",
                                color = customColors.textFixed,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Icon(
                                modifier = Modifier
                                    .border(2.dp, customColors.textFixed, CircleShape)
                                    .padding(8.dp)
                                    .size(20.dp),
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = customColors.textFixed,
                            )
                        }
                    }

                    item {
                        Text(
                            "My Favorites",
                            style = MaterialTheme.typography.titleMedium,
                            color = customColors.textFixed,
                            modifier = Modifier
                                .padding(top = 20.dp, start = 8.dp, bottom = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        if (favoriteTheaters.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No favorites found",
                                    textAlign = TextAlign.Center,
                                    color = customColors.textSecondary
                                )
                            }
                        } else {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {

                                items(favoriteTheaters) { index ->
                                    Card {
                                        Box(
                                            modifier = Modifier
                                                .width(170.dp)
                                                .height(140.dp)
                                        ) {
                                            AsyncImage(
                                                modifier = Modifier.fillMaxHeight(),
                                                model = index.img,
                                                contentDescription = index.description,
                                                contentScale = ContentScale.FillHeight
                                            )
                                            GradientOverlay(Modifier.align(Alignment.BottomCenter))
                                            Text(
                                                text = index.name,
                                                color = customColors.textFixed,
                                                modifier = Modifier.padding(8.dp).align(Alignment.BottomStart)
                                            )
                                        }
                                    }
                                }
                            }
                        }


                    }

//                    item {
//                        Text(
//                            "CineCode Theaters",
//                            style = MaterialTheme.typography.titleLarge,
//                            color = customColors.textFixed,
//                            modifier = Modifier
//                                .padding(top = 12.dp, start = 8.dp)
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//
//                    }
                    val isSticky = listState.firstVisibleItemIndex > 1

                    stickyHeader {
                        Text(
                            "CineCode Theaters",
                            style = MaterialTheme.typography.titleMedium,
                            color = customColors.textFixed,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (isSticky) customColors.bottomBarBackgroundColor
                                    else Color.Transparent
                                )
                                .padding(vertical = 20.dp, horizontal = 8.dp)
                        )
                        FilterOptionsRow(
                            filters = TheaterFilters.entries.toList(),
                            onClick = { selected -> println(selected.text) }
                        )
                    }
                    // 📄 Cards scrolleables
                    items(theaterList) { index ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    navigateToDetail(index)
                                },
//                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .background(customColors.cardSecondaryBackground),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(0.4f)
                                ) {
                                    AsyncImage(
                                        modifier = Modifier.fillMaxHeight(),
                                        model = index.img,
                                        contentDescription = index.description,
                                        contentScale = ContentScale.FillHeight
                                    )
                                    GradientOverlay(Modifier.align(Alignment.BottomCenter))
                                    if (index.isFavorite) {
                                        Icon(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .size(30.dp)
                                                .align(Alignment.BottomStart)
                                                .clickable {
                                                    theaterMainViewModel.toggleFavoritesStatus(
                                                        index
                                                    )
                                                },
                                            imageVector = Icons.Filled.Favorite,
                                            contentDescription = "Profile",
                                            tint = Color(0xFFFF6E40),
                                        )
                                    } else {
                                        Icon(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .size(30.dp)
                                                .align(Alignment.BottomStart)
                                                .clickable {
                                                    theaterMainViewModel.toggleFavoritesStatus(
                                                        index
                                                    )
                                                },
                                            imageVector = Icons.Outlined.FavoriteBorder,
                                            contentDescription = "Profile",
                                            tint = Color(0xFFD9D9D9),
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(0.6f)
                                        .padding(8.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(
                                            text = index.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = customColors.textSecondary
                                        )
                                        Spacer(Modifier.height(4.dp))
                                        Text(
                                            text = "(Permisos de GPS requerido para conocer la distancia)",
                                            style = MaterialTheme.typography.bodySmall,
                                        )
                                    }
                                    Text(
                                        text = index.address,
                                        style = MaterialTheme.typography.bodyMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Icon(
                                    modifier = Modifier
                                        .padding(
                                            start = 4.dp,
                                            end = 8.dp
                                        )
                                        .size(30.dp)
                                        .align(Alignment.CenterVertically),
                                    imageVector = Icons.AutoMirrored.Filled.NavigateNext,
                                    contentDescription = "Profile",
                                    tint = Color(0xFF4F5976),
                                )
                            }

                        }
                    }

                }
            }
        }
    }
}