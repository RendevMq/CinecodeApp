package com.rensystem.p3_cinecode.feature.moviesFt

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.AsyncImage
import com.rensystem.p3_cinecode.feature.homeFt.ui.components.GradientOverlay
import com.rensystem.p3_cinecode.feature.moviesFt.ui.MovieMainViewModel
import com.rensystem.p3_cinecode.feature.moviesFt.ui.MovieUIPagerState
import com.rensystem.p3_cinecode.feature.moviesFt.ui.components.MovieLabelTag
import com.rensystem.p3_cinecode.ui.theme.LocalBrandColors
import kotlinx.coroutines.launch
import androidx.compose.ui.text.style.TextOverflow
import com.rensystem.p3_cinecode.core.utils.MovieMainFilters
import com.rensystem.p3_cinecode.core.shareComponents.FilterOptionsRow
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem

enum class MoviesTabs(
    val text: String
) {
    NowShowing("Estrenos"),
    ComingSoon("Próximamente"),
}

@Composable
fun GradientBackgroundBrush(
    isVerticalGradient: Boolean,
    colors: List<Color>
): Brush {
    val endOffset = if (isVerticalGradient) {
        Offset(x = 0f, Float.POSITIVE_INFINITY)
    } else {
        Offset(Float.POSITIVE_INFINITY, y = 0f)
    }

    return Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = endOffset
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MoviesMainScreen(
    navigateToDetail : (MovieItem) -> Unit,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    moviesMainViewModel: MovieMainViewModel
) {
    val customColors = LocalBrandColors.current
    val pagerState = rememberPagerState(pageCount = { MoviesTabs.entries.size })
//    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()


    val gridState = rememberLazyGridState()
    val stickyOffsetY = remember { mutableStateOf(0f) }

    val density = LocalDensity.current
    val topPaddingPx = with(density) { innerPadding.calculateTopPadding().toPx() }


    val gradientColorList = listOf(
        Color(0xFF2B2B2B), // gris muy oscuro
        Color(0xFF262626), // un poco más oscuro
        Color(0xFF212121), // típico de fondo dark
        Color(0xFF1C1C1C), // más profundo
        Color(0xFF181818)  // sin llegar a negro puro
    )

    //////////ESTADOS VIEWMODEL////////////////
    //Creamos un ciclo de vida de la screen
    //Los state flow son los que van par ala UI, debemos crear un snapshot state
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState: MovieUIPagerState by produceState<MovieUIPagerState>(
        initialValue = MovieUIPagerState.Loading, // Asegúrate de que esto sea de tipo Loading
        key1 = lifecycle,
        key2 = moviesMainViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            // Recolectamos el estado de uiState
            moviesMainViewModel.uiState.collect { value = it }
        }
    }


    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        // Usamos Box para superponer sticky
        Box(
            modifier = Modifier
                .padding(
                    innerPadding
                )
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = gridState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = GradientBackgroundBrush(
                            isVerticalGradient = true,
                            colors = gradientColorList
                        )
                    ),
                contentPadding = PaddingValues(
                    bottom = 16.dp
                )
            ) {

                item(span = { GridItemSpan(2) }) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(customColors.bottomBarBackgroundColor)
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Películas",
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

//                         Línea separadora con efecto de elevación (sombra)
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xff111111)) // color de la línea
                                .shadow(elevation = 4.dp, shape = RectangleShape, clip = false) // Efecto de sombra para "elevación"
                        )
                    }
                }

                // 🧷 Sección Sticky que se mide

                item(span = { GridItemSpan(2) }) {
                    Box(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                stickyOffsetY.value = coordinates.positionInWindow().y
                            }
                    ) {
                        StickySectionContentt(
                            selectedTabIndex = selectedTabIndex.value,
                            onTabSelected = { newIndex ->
                                selectedTabIndex.intValue = newIndex
//                                scope.launch {
//                                    gridState.animateScrollToItem(0) // Para subir al inicio si quieres
//                                }
                            }
                        )
                    }
                }
                when (uiState) {
                    is MovieUIPagerState.Error -> {}
                    is MovieUIPagerState.Loading -> {
                        items(4) { index ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(0.75f)
                                    .padding(8.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Text(
                                    text = "Película $index",
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                    // ✅ Grilla de películas
                    is MovieUIPagerState.Success -> {

                        val nowShowingMovies =
                            (uiState as MovieUIPagerState.Success).nowShowingMovies
                        val comingSoonMovies =
                            (uiState as MovieUIPagerState.Success).comingSoonMovies
//                        comingSoonMovies.forEachIndexed { index, movie ->
//                            Log.i("comingSoonMovies", "[$index] ${movie.title} - IsPresale: ${movie.isPreSale}" )
//                        }

                        val movielist =
                            if (selectedTabIndex.intValue == MoviesTabs.NowShowing.ordinal) {
                                nowShowingMovies
                            } else {
                                comingSoonMovies
                            }

                        items(
                            items = movielist,
                            key = { it.id }
                        ) { index ->
                            AnimatedVisibility(
                                visible = true,
                                enter = fadeIn(animationSpec = tween(300)),
                                exit = fadeOut(animationSpec = tween(300))
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(0.75f)
                                        .clickable {
                                            navigateToDetail(index)
                                        }
                                        .padding(8.dp)
                                        .animateItem(
                                            fadeInSpec = tween(
                                                durationMillis = 300,
                                                delayMillis = 100
                                            ), // <- Esto hace el movimiento fluido
                                            fadeOutSpec = tween(
                                                durationMillis = 300,
                                                delayMillis = 100
                                            )
                                        ), // <- Esto hace el movimiento fluido // <- Esto hace el movimiento fluido
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        AsyncImage(
                                            model = index.posterPath,
                                            contentDescription = "Movie Image",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = Crop
                                        )
                                        GradientOverlay(Modifier.align(Alignment.BottomCenter))

                                        MovieLabelTag(
                                            modifier = Modifier
                                                .align(Alignment.TopStart),
                                            isPreSale = index.isPreSale,
                                            isRePremiere = index.isRePremiere,
                                            isRemake = index.isRemake,
                                            isNewRelease = index.isNewRelease
                                        )

                                        // Título + género + duración
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth(0.8f)
                                                .align(Alignment.BottomStart)
                                                .padding(12.dp)
                                        ) {
                                            Text(
                                                text = index.title,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                fontSize = 16.sp
                                            )
                                            Text(
                                                text = "${index.genre} | ${index.runtime} min",
                                                color = Color.LightGray,
                                                fontSize = 12.sp,
                                                fontStyle = FontStyle.Italic
                                            )
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }

            // Sticky manual sobrepuesto cuando llega al top
            if (stickyOffsetY.value <= topPaddingPx) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .background(customColors.bottomBarBackgroundColor)
                ) {
                    StickySectionContentt(
                        selectedTabIndex = selectedTabIndex.intValue,
                        onTabSelected = { newIndex ->
                            selectedTabIndex.intValue = newIndex
//                            scope.launch {
//                                gridState.animateScrollToItem(0) // Para subir al inicio si quieres
//                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StickySectionContentt(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val customColors = LocalBrandColors.current
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .heightIn(min = 0.dp)
    ) {

//                    CustomTabRow(
//                        selectedTabIndex = selectedTabIndex.value,
//                        onTabSelected = {
//                            scope.launch {
//                                pagerState.animateScrollToPage(it)
//                            }
//                        }
//                    )
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = customColors.bottomBarBackgroundColor,
            divider = {} // 👈 Esto elimina la línea gris/borde inferior
        ) {
            MoviesTabs.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    text = { Text(text = currentTab.text) }
                )
            }
        }
        FilterOptionsRow(
            filters = MovieMainFilters.entries.toList(),
            onClick = { selected -> println(selected.text) }
        )
    }
}
