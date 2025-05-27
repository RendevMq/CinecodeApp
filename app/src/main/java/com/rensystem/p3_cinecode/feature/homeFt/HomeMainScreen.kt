package com.rensystem.p3_cinecode.feature.homeFt

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.AsyncImage
import com.rensystem.p3_cinecode.R
import com.rensystem.p3_cinecode.feature.homeFt.core.openYouTubeUrl
import com.rensystem.p3_cinecode.feature.homeFt.domain.model.getFormatLabels
import com.rensystem.p3_cinecode.feature.homeFt.domain.model.getRatingLabel
import com.rensystem.p3_cinecode.feature.homeFt.ui.HomeMainViewModel
import com.rensystem.p3_cinecode.feature.homeFt.ui.HomeUIState
import com.rensystem.p3_cinecode.feature.homeFt.ui.components.GradientOverlay
import com.rensystem.p3_cinecode.ui.theme.LocalBrandColors
import androidx.lifecycle.compose.LocalLifecycleOwner as LocalLifecycleOwner1


@Composable
fun HomeMainScreen(
    innerPadding: PaddingValues,
    homeMainViewModel: HomeMainViewModel
) {
    //Creamos un ciclo de vida de la screen
    //Los state flow son los que van par ala UI, debemos crear un snapshot state
    val lifecycle = LocalLifecycleOwner1.current.lifecycle

    val uiState: HomeUIState by produceState<HomeUIState>(
        initialValue = HomeUIState.Loading, // Asegúrate de que esto sea de tipo Loading
        key1 = lifecycle,
        key2 = homeMainViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            // Recolectamos el estado de uiState
            homeMainViewModel.uiState.collect { value = it }
        }
    }

    Box(
        modifier = Modifier
            .padding(
                bottom = innerPadding.calculateBottomPadding()
            )
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is HomeUIState.Error -> {}
            is HomeUIState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(8.dp),
                    strokeWidth = 4.dp
                )
            }

            is HomeUIState.Success -> {

                val releaseMovies = (uiState as HomeUIState.Success).releaseMovies
                val pagerState = rememberPagerState(
                    pageCount = { releaseMovies.size })
                val context = LocalContext.current

                HorizontalPager(
                    state = pagerState,
                ) {
                    val customColors = LocalBrandColors.current
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        AsyncImage(
                            model = releaseMovies[it].posterPath.ifEmpty {
                                R.drawable.no_image
                            },
                            contentDescription = "Poster of ${releaseMovies[it].title}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillHeight,
                            alignment = Alignment.Center
                        )
                        // Gradiente desde un negro oscuro hacia transparente (mitad del alto)
                        GradientOverlay(Modifier.align(Alignment.BottomCenter))

                        if (releaseMovies[it].trailer.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(50)) // Esquinas redondeadas completamente (círculo)
                                    .background(customColors.glassButtonBackground) // Efecto glass
                                    .clickable {
                                        openYouTubeUrl(context, releaseMovies[it].trailer)
                                    }
                                    .border(
                                        width = 2.dp,
                                        color = customColors.textFixed,
                                        shape = RoundedCornerShape(50)
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = customColors.textFixed,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        Column(
                            Modifier
                                .align(Alignment.BottomStart)
                        ) {
                            Column(
                                Modifier
                                    .padding(
                                        bottom = 20.dp,
                                        start = 8.dp
                                    )
                                    .fillMaxWidth(0.65f)
                            ) {
                                val ratingLabel = releaseMovies[it].getRatingLabel()

                                val annotatedText = buildAnnotatedString {
                                    append(releaseMovies[it].title.uppercase() + " ")
                                    appendInlineContent("ratingTag", "[rating]")
                                }

                                val inlineContent = mapOf(
                                    "ratingTag" to InlineTextContent(
                                        Placeholder(
                                            width = 50.sp, // o cualquier tamaño razonable
                                            height = 25.sp,
                                            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .border(
                                                    1.dp,
                                                    customColors.textFixed,
                                                    RoundedCornerShape(4.dp)
                                                )
                                                .padding(horizontal = 4.dp, vertical = 0.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = ratingLabel,
                                                fontSize = 12.sp,
                                                color = customColors.textFixed
                                            )
                                        }
                                    }
                                )



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
                                    if (releaseMovies[it].trailer.isNotEmpty()) {
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
                                    text = annotatedText,
                                    inlineContent = inlineContent,
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = customColors.textFixed
                                )
                                Text(
                                    text = releaseMovies[it].overview,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = customColors.textFixed,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )

                            }
                            Row(
                                modifier = Modifier
                                    .padding(
                                        bottom = 20.dp,
                                        end = 16.dp
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
                                                .padding(vertical = 4.dp, horizontal = 8.dp)
                                        ) {
                                            Text(
                                                text = releaseMovies[it].genre,
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
                                                releaseMovies[it].getFormatLabels().forEach {
                                                    Log.i("Renato1", it)
                                                }.toString()
                                            )
                                            releaseMovies[it].getFormatLabels().forEach { format ->
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

                                Spacer(Modifier.width(20.dp))
                                Button(
                                    modifier = Modifier
                                        .weight(0.30f),
                                    onClick = {},
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color(0xFFFF6E40)
//                        ),
                                    shape = RoundedCornerShape(
                                        topStart = 12.dp,
                                        topEnd = 12.dp,
                                        bottomStart = 12.dp,
                                        bottomEnd = 12.dp
                                    )

                                ) {
                                    Text(
                                        "Get Ticket",
                                        modifier = Modifier.padding(vertical = 4.dp),
                                        color = customColors.textFixed
                                    )
                                }

                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 60.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    repeat(releaseMovies.size) {
                        val isSelected = it == pagerState.currentPage
                        // Animación de color para todos los puntos
                        val color by animateColorAsState(
                            targetValue = when {
                                isSelected -> Color.Red // Si está seleccionado, rojo
                                else -> Color.Gray.copy(alpha = 0.5f) // Si no está seleccionado, gris
                            },
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
                        )
                        // Animación del ancho
                        val width by animateDpAsState(
                            targetValue = if (isSelected) 35.dp else 12.dp,
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        )
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(height = 12.dp, width = width)
                                .background(color)
                        )
                    }
                }
            }
        }
    }
}