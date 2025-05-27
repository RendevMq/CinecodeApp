package com.rensystem.p3_cinecode.feature.theatersFt.ui

import android.os.Build
import android.util.Log
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.rensystem.p3_cinecode.core.utils.convertMinutesToHoursMinutes
import com.rensystem.p3_cinecode.core.utils.getAbbreviatedMonthEs
import com.rensystem.p3_cinecode.core.utils.getDayOfMonth
import com.rensystem.p3_cinecode.core.utils.getFullDayOfWeekEs
import com.rensystem.p3_cinecode.feature.homeFt.ui.components.DinamicGradientOverlay
import com.rensystem.p3_cinecode.feature.moviesFt.ui.components.MovieLabelTag
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.MovieTheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.TheaterItem
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.getFormatLabels
import com.rensystem.p3_cinecode.feature.theatersFt.domain.model.getLabel
import com.rensystem.p3_cinecode.ui.theme.LocalBrandColors
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

enum class TheaterDetailTabs(
    val text: String
) {
    NowShowing("Cartelera"),
    Detail("Detalle"),
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TheaterDetailScreen(
    uistate: TheaterDetailUIState,
    theaterItem: TheaterItem,
    onToggleFavoriteIcon: (TheaterItem) -> Unit,
    onBackClick: () -> Unit,
    dateSelected: String,
    onChangueDate: (String) -> Unit
) {

    val customColors = LocalBrandColors.current
    val gradientColorList = listOf(
//        Color(0xFF282828), // 43
//        Color(0xFF262626), // 38
        Color(0xFF1C1C1C), // 33
        Color(0xFF1B1B1B), // 33
        Color(0xFF1A1A1A), // 33
        Color(0xFF191919), // 28
        Color(0xFF181818)  // 24
    )

    when (uistate) {
        is TheaterDetailUIState.Loading -> {
            LoadingScreen()
        }

        is TheaterDetailUIState.Error -> {
            val error = uistate.throwable
            Log.e("TheaterDetailScreen", "Error al cargar datos", error)
            ErrorScreen(error.message ?: "Error inesperado")
        }

        is TheaterDetailUIState.Success -> {
            Log.i("Renato", uistate.availableDates.toString())
            val movies = uistate.moviesWithSessions

            val selectedTabIndex = remember { mutableIntStateOf(0) }
            val listState = rememberLazyListState() // 👈 Controlador del scroll
            val coroutineScope = rememberCoroutineScope() // 👈 Necesario para el scroll animado


            val availableDates = uistate.availableDates

            var imageHeightPx by remember { mutableFloatStateOf(0f) }
            LazyColumn(
                state = listState, // 👈
                modifier = Modifier
                    .fillMaxSize()
                    .padding(WindowInsets.statusBars.asPaddingValues()) // solo status bar
                    .background(
                        Color(0xFF191919)
                    )
//                    .background(
//                        brush = GradientBackgroundBrush(
//                            isVerticalGradient = true,
//                            colors = gradientColorList
//                        )
//                    )
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(customColors.bottomBarBackgroundColor)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        onBackClick()
                                    },
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Profile",
                                tint = customColors.textFixed,
                            )
                            Text(
                                text = "Theaters",
                                modifier = Modifier.padding(start = 12.dp),
                                color = customColors.textFixed,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                imageHeightPx = coordinates.size.height.toFloat()
                            }
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth(),
                            model = theaterItem.img,
                            contentDescription = "Theater image",
                            contentScale = ContentScale.FillWidth // FillWidth si prefieres, pero Crop se ve mejor
                        )

// Dibujamos el gradiente desde abajo hasta la mitad
                        if (imageHeightPx > 0f) {
                            DinamicGradientOverlay(
                                halfHeightDp = with(LocalDensity.current) { (imageHeightPx / 2).toDp() },
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }

                        Text(
                            text = theaterItem.address,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp),
                            color = Color.White
                        )
                    }
                }


                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(customColors.bottomBarBackgroundColor)
                            .padding(horizontal = 8.dp, vertical = 16.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    onToggleFavoriteIcon(theaterItem)
                                },
                            imageVector = if (theaterItem.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                        Text(
                            modifier = Modifier.padding(start = 12.dp),
                            text = theaterItem.name,
                            style = MaterialTheme.typography.titleLarge,
                            color = customColors.textSecondary
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .height(0.5.dp)
                            .padding(horizontal = 12.dp)
                    )
                    TabRow(
                        selectedTabIndex = selectedTabIndex.value,
                        containerColor = customColors.bottomBarBackgroundColor,
                        divider = {} // 👈 Esto elimina la línea gris/borde inferior
                    ) {
                        TheaterDetailTabs.entries.forEachIndexed { index, currentTab ->
                            Tab(
                                selected = selectedTabIndex.intValue == index,
                                onClick = {
                                    selectedTabIndex.intValue = index
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(  0) // 👈 Scroll al inicio
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
                }

                if (selectedTabIndex.intValue == TheaterDetailTabs.Detail.ordinal) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Salas (título principal)
                            Text(
                                text = "${theaterItem.showroomQuantity} Salas",
                                style = MaterialTheme.typography.headlineSmall,
                                color = customColors.textThemed,
                            )

                            // Teléfono
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Teléfono:",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = customColors.textSecondary
                                )
                                Text(
                                    text = "+${theaterItem.phoneNumber}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = customColors.textThemed
                                )
                            }

                            // Formatos
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Formatos:",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = customColors.textSecondary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    items(theaterItem.getFormatLabels()) { format ->
                                        Text(
                                            text = format,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(customColors.specialButtonColor)
                                                .padding(horizontal = 10.dp, vertical = 6.dp),
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelMedium
                                        )
                                    }
                                }
                            }

                            // Ubicación
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "Ubicación:",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = customColors.textSecondary
                                )
                                Text(
                                    text = theaterItem.address,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = customColors.textThemed,
                                    modifier = Modifier.weight(1f) // permite salto de línea si es largo
                                )
                            }
                        }
                    }

                    item {
                        TheaterGoogleMap(
                            latitude = theaterItem.latitude,
                            longitude = theaterItem.longitude
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
//                    items(10) { index ->
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp)
//                        ) {
//                            Text("Item $index", color = Color.White)
//                        }
//                    }
                } else {
                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(availableDates) { date ->
                                Box(
                                    modifier = Modifier
                                        .width(70.dp)
                                        .border(
                                            width = 1.dp,
                                            brush = if (dateSelected == date) SolidColor(
                                                customColors.specialButtonColor
                                            ) else SolidColor(Color.Gray), // Color del borde
                                            shape = RoundedCornerShape(8.dp) // Esquinas redondeadas
                                        )
                                        .padding(
                                            vertical = 8.dp, horizontal = 4.dp
                                        )
                                        .clickable {
                                            onChangueDate(date)
                                        }
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            getFullDayOfWeekEs(date),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                        Text(
                                            getDayOfMonth(date),
                                            style = MaterialTheme.typography.headlineMedium
                                        )
                                        Text(
                                            getAbbreviatedMonthEs(date),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                    items(movies) { item ->
                        MovieCard(item)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieCard(movie: MovieTheaterItem) {

    val customColors = LocalBrandColors.current

    Row(
        modifier = Modifier.padding(12.dp)
    ) {
        Box {
            AsyncImage(
                model = movie.posterURL,
                contentDescription = movie.title
            )
            MovieLabelTag(
                modifier = Modifier
                    .align(Alignment.TopStart),
                isPreSale = movie.isPreSale,
                isRePremiere = movie.isRePremiere,
                isRemake = movie.isRemake,
                isNewRelease = movie.isNewRelease
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${movie.genre} , ${convertMinutesToHoursMinutes(movie.runTime)} ,  ${movie.ratingDesc.getLabel()}",
                color = Color.Gray
            )
            Row {
                Text(
                    text = movie.formats.map { it.getLabel() }.joinToString(", "),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = " - ",
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = movie.languages.map { it.name }.joinToString(", "),
                    style = MaterialTheme.typography.labelMedium,
                    color = customColors.specialButtonColor
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Showtimes:", style = MaterialTheme.typography.labelMedium)
            movie.showTime?.takeIf { it.isNotEmpty() }?.let { showTimes ->
                LazyVerticalGrid (
                    columns = GridCells.Adaptive(minSize = 60.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp) // Ajustable según tu layout
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    userScrollEnabled = false
                ) {
                    items(showTimes) { session ->
                        val time = try {
                            OffsetDateTime.parse(session.showtime)
                                .format(DateTimeFormatter.ofPattern("HH:mm"))
                        } catch (e: Exception) {
                            "??:??"
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray)
                                .padding(vertical = 12.dp, horizontal = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = time,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                    }
                }
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    showTimes.forEach { session ->
//                        Text(
//                            text = session.showtime,
//                            style = MaterialTheme.typography.bodySmall,
//                            modifier = Modifier
//                                .padding(4.dp)
//                                .clickable { /* Puedes manejar la selección aquí */ }
//                        )
//                    }
//                }
            } ?: Text(
                text = "No showtimes available",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, color = Color.Red)
    }
}

@Composable
fun TheaterGoogleMap(
    latitude: String,
    longitude: String
) {
    // Convertimos los Strings a Double con seguridad
    val lat = latitude.toDoubleOrNull() ?: -12.0464
    val lng = longitude.toDoubleOrNull() ?: -77.0428
    val posicion = LatLng(lat, lng)

    // Estado de la cámara centrado en la posición dada
    val estadoCamara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(posicion, 14f)
    }

    // Propiedades del mapa: MapType.NORMAL + modo nocturno
    val properties = MapProperties(
        mapType = MapType.NORMAL,
        isBuildingEnabled = true,
        isTrafficEnabled = false,
        isIndoorEnabled = false,
        isMyLocationEnabled = false,
    )
    // Cuadrado: usamos aspectRatio(1f) para hacerlo proporcional
    GoogleMap(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .aspectRatio(1f), // Esto hace que el alto = ancho
        cameraPositionState = estadoCamara,
        properties = properties
    ) {
        Marker(
            state = MarkerState(position = posicion),
            title = "Ubicación del Cine",
            snippet = "Lat: $lat, Lng: $lng"
        )
    }
}

