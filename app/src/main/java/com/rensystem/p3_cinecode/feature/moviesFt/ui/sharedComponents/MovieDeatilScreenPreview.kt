package com.rensystem.p3_cinecode.feature.moviesFt.ui.sharedComponents

import android.os.Build
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.rensystem.p3_cinecode.R
import com.rensystem.p3_cinecode.core.shareComponents.FilterOptionsRow
import com.rensystem.p3_cinecode.core.utils.MovieDetailFilters
import com.rensystem.p3_cinecode.core.utils.MovieMainFilters
import com.rensystem.p3_cinecode.core.utils.convertMinutesToHoursMinutes
import com.rensystem.p3_cinecode.feature.homeFt.ui.components.GradientOverlay
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.Actor
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieDetailItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.MovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.getFormatLabels
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.getLanguageLabel
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.getRatingLabel
import com.rensystem.p3_cinecode.feature.theatersFt.ui.TheaterDetailTabs
import com.rensystem.p3_cinecode.ui.theme.CineAppDarkColorScheme
import com.rensystem.p3_cinecode.ui.theme.CineAppLightColorScheme
import com.rensystem.p3_cinecode.ui.theme.CineAppTypography
import com.rensystem.p3_cinecode.ui.theme.DarkBrandColors
import com.rensystem.p3_cinecode.ui.theme.LightBrandColors
import com.rensystem.p3_cinecode.ui.theme.LocalBrandColors
import kotlinx.coroutines.launch

val isDarkTheme = true
val isDynamicColor = true // Cambia esto a false si no quieres colores dinámicos
val dynamicColor = isDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
val myColorScheme = when {
    isDarkTheme -> CineAppDarkColorScheme
    else -> CineAppLightColorScheme
}

val sampleMovie = MovieDetailItem(
    id = "HO00002416",
    title = "Infierno en el pantano",
    posterPath = "https://cdn.apis.cineplanet.com.pe/CDN/media/entity/get/FilmPosterGraphic/HO00002416?referenceScheme=HeadOffice&allowPlaceHolder=true",
    runtime = 88.0, // en minutos
    format = listOf("THE2D", "THE3D", "REGULAR"),
    ratingDescription = "THE14",
    genre = "Aventura",
    language = listOf("DOBLADA", "SUBTITULAD"),
    isComingSoon = false,
    isPreSale = true,
    isRePremiere = false,
    isRemake = true,
    isNewRelease = true,
    trailer = "https://www.youtube.com/watch?v=1ELJ6WTQ0wE",
    overview = "Las vacaciones se convierten en un desastre cuando Kyle, una recién graduada de Houston, y sus amigos sobreviven a un accidente de avión en los desolados pantanos de Luisiana, solo para descubrir que algo mucho más peligroso acecha en las aguas poco profundas.",
    cast = listOf(
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
)

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview(
    modifier: Modifier  = Modifier
) {

//    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val miFutureSwitchState = true //darktheme
    val customColors = if (miFutureSwitchState) DarkBrandColors else LightBrandColors
    val context = LocalContext.current

    val listState1   = rememberLazyListState() // 👈 Controlador del scroll
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { MovieDetailTabs.entries.size })
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }

    // Detectar cambio de página para hacer scroll arriba (Funciona tanto cuando se da click en el tabrow asi como tmb al hacer swipe)
//    LaunchedEffect(pagerState.currentPage) {
//        listState1    .animateScrollToItem(0)
//    }

    CompositionLocalProvider(
        LocalBrandColors provides customColors
    ) {
        MaterialTheme(
            colorScheme = myColorScheme,
            typography = CineAppTypography,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color(0xFF1C1C1C))
            ) {
                Box(
                    modifier = Modifier
                        .height(LocalConfiguration.current.screenHeightDp.dp / (2.5f))
                ) {
                    AsyncImage(
                        model = sampleMovie.posterPath.ifEmpty {
                            R.drawable.no_image
                        },
                        contentDescription = "Poster of ${sampleMovie.title}",
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
                                if (sampleMovie.trailer.isNotEmpty()) {
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
                                text = sampleMovie.title.uppercase(),
                                style = MaterialTheme.typography.headlineLarge,
                                color = customColors.textFixed
                            )
                            Spacer(modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = "${sampleMovie.genre} | ${
                                        convertMinutesToHoursMinutes(
                                            sampleMovie.runtime.toLong()
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
                                        text = sampleMovie.getRatingLabel(),
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
//                                    onBackClick()
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
                                    listState1  .animateScrollToItem(0) // 👈 Scroll al inicio
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
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->

                    when (page) {
                        MovieDetailTabs.Detail.ordinal -> {
                            LazyColumn(
                                state = listState1
                            ) {
                                item {
                                    Column {
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    vertical = 16.dp
                                                )
                                                .fillMaxWidth()
                                        ) {
                                            Box(
                                                Modifier
                                                    .weight(0.65f)
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
                                                        .padding(
                                                            top = 10.dp,
                                                            bottom = 10.dp,
                                                            start = 10.dp,
                                                            end = 20.dp
                                                        ),
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
                                                            text = sampleMovie.genre,
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
                                                    LazyRow(
                                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
                                                        Log.i(
                                                            "Renatoooo",
                                                            sampleMovie.getFormatLabels().forEach {
                                                                Log.i("Renato1", it)
                                                            }.toString()
                                                        )
                                                        sampleMovie.getFormatLabels().forEach { format ->
                                                            item {
                                                                Box(
                                                                    modifier = Modifier
                                                                        .clip(RoundedCornerShape(8.dp))
                                                                        .background(customColors.formatLabelBackground)
                                                                        .padding(
                                                                            vertical = 4.dp,
                                                                            horizontal = 8.dp
                                                                        )
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
                                            }
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
                                                text = sampleMovie.overview,
                                                color = Color(0xFF959595),
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Spacer(Modifier.height(16.dp))

                                            Text(
                                                text = "Reparto",
                                                color = customColors.textFixed,
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                            Spacer(Modifier.height(8.dp))
                                        }
                                        sampleMovie.cast?.let { castList ->
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
                                                            alignment = BiasAlignment(0f, -0.6f),
                                                            modifier = Modifier
                                                                .size(100.dp) // Puedes cambiar el tamaño
                                                                .clip(CircleShape)
                                                                .border(2.dp, Color.Gray, CircleShape),
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
                                                sampleMovie.getLanguageLabel().forEach { format ->
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
                                                sampleMovie.getFormatLabels().forEach { format ->
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
                            }
                        }

                        MovieDetailTabs.Buy.ordinal -> {
                            // Otro contenido para la segunda pestaña
                            LazyColumn(
                                state = listState1
                            )  {
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
                                items(20) { index ->
                                    Card(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            "item $index",
                                            modifier = Modifier.padding(16.dp),
                                            color = Color.White
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
}