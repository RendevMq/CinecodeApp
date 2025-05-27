package com.rensystem.p3_cinecode.feature.moviesFt.ui.sharedComponents

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.rensystem.p3_cinecode.R
import com.rensystem.p3_cinecode.core.shareComponents.FilterOptionsRow
import com.rensystem.p3_cinecode.core.utils.MovieDetailFilters
import com.rensystem.p3_cinecode.core.utils.convertMinutesToHoursMinutes
import com.rensystem.p3_cinecode.feature.homeFt.ui.components.GradientOverlay
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.getFormatLabels
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.getLanguageLabel
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.getRatingLabel
import com.rensystem.p3_cinecode.feature.moviesFt.ui.MovieDetailUIPagerState
import com.rensystem.p3_cinecode.feature.moviesFt.ui.components.MovieDetailShimmer
import com.rensystem.p3_cinecode.feature.moviesFt.ui.components.TheaterOptionItem
import com.rensystem.p3_cinecode.feature.theatersFt.ui.ErrorScreen
import com.rensystem.p3_cinecode.feature.theatersFt.ui.LoadingScreen
import com.rensystem.p3_cinecode.feature.theatersFt.ui.TheaterDetailUIState
import com.rensystem.p3_cinecode.ui.theme.LocalBrandColors
import kotlinx.coroutines.launch


enum class MovieDetailTabs(
    val text: String
) {
    Detail("Detalle"),
    Buy("Comprar"),
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
//@Preview(showBackground = true)
@Composable
fun MovieDetailScreen(
    uiState: MovieDetailUIPagerState,
    movieItem: MovieItem,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {

//    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val customColors = LocalBrandColors.current

    val listState = rememberLazyListState() // 👈 Controlador del scroll
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { MovieDetailTabs.entries.size })
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }

    // Detectar cambio de página para hacer scroll arriba (Funciona tanto cuando se da click en el tabrow asi como tmb al hacer swipe)
//    LaunchedEffect(pagerState.currentPage) {
//        listState.animateScrollToItem(0)
//    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(WindowInsets.statusBars.asPaddingValues()) // solo status bar
            .background(Color(0xFF1C1C1C))
    ) {
        Box(
            modifier = Modifier
//                .height(LocalConfiguration.current.screenHeightDp.dp / (2.5f))
                .height(400.dp)
        ) {
            AsyncImage(
                model = movieItem.posterPath.ifEmpty {
                    R.drawable.no_image
                },
                contentDescription = "Poster of ${movieItem.title}",
                modifier = Modifier.fillMaxHeight(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            )
            // Gradiente desde un negro oscuro hacia transparente (mitad del alto)
            GradientOverlay(
                Modifier.align(Alignment.BottomCenter)
            )
            Column(
                Modifier
                    .align(Alignment.BottomStart)
            ) {
                Column(
                    Modifier
                        .padding(
                            bottom = 16.dp,
                            start = 12.dp
                        )
                        .fillMaxWidth(0.65f)
                ) {
                    Row {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(customColors.tagReleaseBackground)
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        ) {
                            Text(
                                text = "Release",
                                color = customColors.textFixed
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        if (movieItem.trailer.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(customColors.tagTrailerBackground)
                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                            ) {
                                Text(
                                    text = "Trailer",
                                    color = customColors.textFixed
                                )
                            }
                        }
                    }
                    Text(
                        text = movieItem.title.uppercase(),
                        style = MaterialTheme.typography.headlineLarge,
                        color = customColors.textFixed
                    )
                    Spacer(modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "${movieItem.genre} | ${
                                convertMinutesToHoursMinutes(
                                    movieItem.runtime.toLong()
                                )
                            }",
                            fontSize = 16.sp,
                            color = customColors.textFixed
                        )
                        Spacer(modifier.width(12.dp))
                        Box(
                            modifier = Modifier
                                .border(
                                    1.dp,
                                    customColors.textFixed,
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 4.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = movieItem.getRatingLabel(),
                                fontSize = 18.sp,
                                color = customColors.textFixed
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {},
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                )

            ) {
                Text(
                    "Ver trailer",
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = customColors.textFixed
                )
            }

            Box(
                modifier
                    .padding(12.dp)
                    .border(
                        width = 2.dp,
                        brush = SolidColor(Color.White),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xAD363030))
            ) {
                Icon(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(20.dp)
                        .clickable {
                            onBackClick()
                        },
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Profile",
                    tint = customColors.textFixed,
                )
            }
        }

        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            containerColor = customColors.bottomBarBackgroundColor,
            divider = {} // 👈 Esto elimina la línea gris/borde inferior
        ) {
            MovieDetailTabs.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
//                                selectedTabIndex.value = index
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                        scope.launch {
                            Log.i("RenatoSCROLL", "AAAAAAA")
                            listState.animateScrollToItem(0) // 👈 Scroll al inicio
                        }
                    },
                    text = {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = currentTab.text
                        )
                    }
                )
            }
        }

        var expandedOptionIds by remember { mutableStateOf(setOf<String>()) }

        when (uiState) {
            is MovieDetailUIPagerState.Loading -> {
                MovieDetailShimmer()
            }

            is MovieDetailUIPagerState.Error -> {
                val error = uiState.throwable
                Log.e("TheaterDetailScreen", "Error al cargar datos", error)
                ErrorScreen(error.message ?: "Error inesperado")
            }

            is MovieDetailUIPagerState.Success -> {

                val fullMovieItem = uiState.selectedMovie
                val theaterList = uiState.theatersWithSessions

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) { page ->
                    LazyColumn(
                        modifier
                            .fillMaxSize()
                            .weight(1f),
                        state = listState
                    ) {
                        when (page) {
                            MovieDetailTabs.Detail.ordinal -> {
                                item {
                                    Column {
                                        Row(
                                            modifier = Modifier
                                                .padding(vertical = 16.dp)
                                                .fillMaxWidth()
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .weight(0.85f) // mantienes 65% del ancho del Row
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .clip(
                                                            RoundedCornerShape(
                                                                topStart = 0.dp,
                                                                topEnd = 24.dp,
                                                                bottomStart = 0.dp,
                                                                bottomEnd = 24.dp
                                                            )
                                                        )
                                                        .background(customColors.overlayInfoBackground)
                                                        .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 20.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .clip(RoundedCornerShape(8.dp))
                                                            .background(customColors.genreLabelBackground)
                                                            .padding(vertical = 8.dp, horizontal = 8.dp)
                                                    ) {
                                                        Text(
                                                            text = fullMovieItem.genre,
                                                            fontSize = 16.sp,
                                                            color = customColors.textFixed
                                                        )
                                                    }

                                                    VerticalDivider(
                                                        modifier = Modifier
                                                            .height(20.dp)
                                                            .width(1.dp)
                                                            .background(Color.White.copy(alpha = 0.5f))
                                                    )

                                                    val itemsInfo = if (!fullMovieItem.secondaryGenres.isNullOrEmpty()) {
                                                        fullMovieItem.secondaryGenres
                                                    } else {
                                                        fullMovieItem.getFormatLabels()
                                                    }

                                                    LazyRow(
                                                        // NO poner fillMaxWidth ni ancho fijo
                                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
                                                        items(itemsInfo) { format ->
                                                            Box(
                                                                modifier = Modifier
                                                                    .clip(RoundedCornerShape(8.dp))
                                                                    .background(customColors.formatLabelBackground)
                                                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                                                            ) {
                                                                Text(
                                                                    text = format,
                                                                    fontSize = 16.sp,
                                                                    color = customColors.textFixed,
                                                                    style = MaterialTheme.typography.bodyLarge
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .weight(0.15f)
                                            )
                                        }


                                        Column(
                                            modifier = Modifier.padding(
                                                start = 12.dp
                                            )
                                        ) {
                                            Text(
                                                text = "Sinopsis",
                                                color = customColors.textFixed,
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                            Spacer(Modifier.height(8.dp))
                                            Text(
                                                text = fullMovieItem.overview,
                                                color = Color(0xFF959595),
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Spacer(Modifier.height(16.dp))

                                            if (fullMovieItem.cast != null) {
                                                Text(
                                                    text = "Reparto",
                                                    color = customColors.textFixed,
                                                    style = MaterialTheme.typography.titleLarge
                                                )
                                                Spacer(Modifier.height(8.dp))
                                            }

                                        }
                                        if (fullMovieItem.cast != null) {
                                            fullMovieItem.cast?.let { castList ->
                                                LazyRow(
                                                    contentPadding = PaddingValues(8.dp),
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                ) {
                                                    items(castList) { actor ->
                                                        val nameWithLineBreak =
                                                            actor.name.replaceFirst(" ", "\n")
                                                        Column(
                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                        ) {
                                                            AsyncImage(
                                                                model = "https://image.tmdb.org/t/p/original${actor.profilePath}",
                                                                contentDescription = "Actor profile image",
                                                                contentScale = ContentScale.Crop, // 👈 ¡clave!
                                                                alignment = BiasAlignment(
                                                                    0f,
                                                                    -0.6f
                                                                ),
                                                                modifier = Modifier
                                                                    .size(100.dp) // Puedes cambiar el tamaño
                                                                    .clip(CircleShape)
                                                                    .border(
                                                                        2.dp,
                                                                        Color.Gray,
                                                                        CircleShape
                                                                    ),
                                                                error = painterResource(R.drawable.noprofile2)
                                                            )
                                                            Text(
                                                                text = nameWithLineBreak,
                                                                color = customColors.textFixed,
                                                                maxLines = 2,
                                                                overflow = TextOverflow.Ellipsis,
                                                                textAlign = TextAlign.Center,
//                                                modifier = Modifier.width(100.dp)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        Spacer(Modifier.height(16.dp))
                                        Column(
                                            modifier = Modifier.padding(
                                                start = 12.dp
                                            )
                                        ) {
                                            Text(
                                                text = "Idioma",
                                                color = customColors.textFixed,
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                            Spacer(Modifier.height(8.dp))
                                            LazyRow(
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                fullMovieItem.getLanguageLabel().forEach { format ->
                                                    item {
                                                        Box(
                                                            modifier = Modifier
//                                                    .background(customColors.formatLabelBackground)
                                                                .border(
                                                                    2.dp,
                                                                    Color(0xBF9C3F20),
                                                                    RoundedCornerShape(8.dp)
                                                                )
                                                                .clip(RoundedCornerShape(8.dp))
                                                                .padding(12.dp)
                                                        ) {
                                                            Text(
                                                                text = format,
                                                                fontSize = 16.sp,
                                                                color = Color(0xFFFC8662),
                                                                fontWeight = FontWeight.Bold,
                                                                style = MaterialTheme.typography.bodyLarge
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                            Spacer(Modifier.height(16.dp))

                                            Text(
                                                text = "Disponible",
                                                color = customColors.textFixed,
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                            Spacer(Modifier.height(8.dp))
                                            LazyRow(
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                fullMovieItem.getFormatLabels().forEach { format ->
                                                    item {

                                                        Box(
                                                            modifier = Modifier
//                                                    .background(customColors.formatLabelBackground)
                                                                .border(
                                                                    2.dp,
                                                                    Color(0xBF9C3F20),
                                                                    RoundedCornerShape(8.dp)
                                                                )
                                                                .clip(RoundedCornerShape(8.dp))
                                                                .padding(12.dp)
                                                        ) {
                                                            Text(
                                                                text = format,
                                                                fontSize = 16.sp,
                                                                color = Color(0xFFFC8662),
                                                                fontWeight = FontWeight.Bold,
                                                                style = MaterialTheme.typography.bodyLarge
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                            Spacer(Modifier.height(12.dp))
                                        }
                                    }
                                }
//                        }
                            }

                            MovieDetailTabs.Buy.ordinal -> {
                                stickyHeader {
                                    FilterOptionsRow(
                                        filters = MovieDetailFilters.entries.toList(),
                                        onClick = { selected -> println(selected.text) }
                                    )
                                    //                         Línea separadora con efecto de elevación (sombra)
                                    Box(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(Color(0xFF282626)) // color de la línea
                                            .shadow(
                                                elevation = 4.dp,
                                                shape = RectangleShape,
                                                clip = false
                                            ) // Efecto de sombra para "elevación"
                                    )
                                }

                                items(theaterList, key = { it.id }) { theater ->
                                    val isExpanded = expandedOptionIds.contains(theater.id)
                                    TheaterOptionItem(
                                        theater = theater,
                                        isExpanded = isExpanded,
                                        onClick = {
                                            expandedOptionIds = if (isExpanded) {
                                                expandedOptionIds - theater.id // quitar si ya está
                                            } else {
                                                expandedOptionIds + theater.id // añadir si no está
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}