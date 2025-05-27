package com.rensystem.p3_cinecode.feature.homeFt.ui

import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.rensystem.p3_cinecode.R
import com.rensystem.p3_cinecode.feature.homeFt.core.openYouTubeUrl
import com.rensystem.p3_cinecode.feature.homeFt.domain.model.MovieHomeItem
import com.rensystem.p3_cinecode.feature.homeFt.domain.model.getFormatLabels
import com.rensystem.p3_cinecode.feature.homeFt.domain.model.getRatingLabel
import com.rensystem.p3_cinecode.feature.homeFt.ui.components.GradientOverlay
import com.rensystem.p3_cinecode.ui.theme.CineAppDarkColorScheme
import com.rensystem.p3_cinecode.ui.theme.CineAppLightColorScheme
import com.rensystem.p3_cinecode.ui.theme.CineAppTypography
import com.rensystem.p3_cinecode.ui.theme.DarkBrandColors
import com.rensystem.p3_cinecode.ui.theme.LightBrandColors
import com.rensystem.p3_cinecode.ui.theme.LocalBrandColors

@Preview(showBackground = true)
@Composable
fun PreviewHomeMain(modifier: Modifier = Modifier) {

    val isDarkTheme = true
    val isDynamicColor = true // Cambia esto a false si no quieres colores dinámicos
    val dynamicColor = isDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val myColorScheme = when {
        isDarkTheme -> CineAppDarkColorScheme
        else -> CineAppLightColorScheme
    }

    val previewMovie: MovieHomeItem = MovieHomeItem(
        id = "HO00002314",
        title = "Betterman: La Historia de Robbie Williams",
        overview = "Dirigida por Michael Gracey (The Greatest Showman, Rocketman). Cuenta la historia desde la perspectiva de Robbie Williams retratado por un mono. Esta película nos invita a recorrer los altibajos de su historia. Conocemos la vida de esta superestrella pop desde su niñez, su despegue hacia la fama, su estrepitoso descenso y su remarcable vuelta al mundo del espectáculo",
        releaseDate = "2025-05-07T00:00:00",
        posterPath = "https://cdn.apis.cineplanet.com.pe/CDN/media/entity/get/FilmPosterGraphic/HO00002334?referenceScheme=HeadOffice&allowPlaceHolder=true",
        genre = "Accion",
        trailer = "https://youtu.be/OrHkEXCj5sE?si=S-ROa1LfsTXw-G3-",
        runtime = 109.0,
        format = listOf(
            "REGULAR",
            "THE2D",
            "PRIME",
            "THE3D",
            "XTREME"
        ),
        ratingDescription = "ATP",
    )
    val miFutureSwitchState = false //darktheme
    val customColors = if (miFutureSwitchState) DarkBrandColors else LightBrandColors
    val context = LocalContext.current

    CompositionLocalProvider(
        LocalBrandColors provides customColors
    ) {
        MaterialTheme(
            colorScheme = myColorScheme,
            typography = CineAppTypography,
        ) {
            val customColors = LocalBrandColors.current
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage(
                    model = previewMovie.posterPath.ifEmpty {
                        R.drawable.no_image
                    },
                    contentDescription = "Poster of ${previewMovie.title}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillHeight,
                    alignment = Alignment.Center
                )
                // Gradiente desde un negro oscuro hacia transparente (mitad del alto)
                GradientOverlay(Modifier.align(Alignment.BottomCenter))

                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(64.dp)
                        .clip(RoundedCornerShape(50)) // Esquinas redondeadas completamente (círculo)
                        .clickable {
                            openYouTubeUrl(context, previewMovie.trailer)
                        }
                        .background(customColors.glassButtonBackground) // Efecto glass
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
                        val ratingLabel = previewMovie.getRatingLabel()

                        val annotatedText = buildAnnotatedString {
                            append(previewMovie.title.uppercase() + " ")
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
                                        .border(1.dp, customColors.textFixed, RoundedCornerShape(4.dp))
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
                        Text(
                            text = annotatedText,
                            inlineContent = inlineContent,
                            style = MaterialTheme.typography.headlineLarge,
                            color = customColors.textFixed
                        )
                        Text(
                            text = previewMovie.overview,
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
                                        text = previewMovie.genre,
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
                                    Log.i("Renato", previewMovie.getFormatLabels().forEach {
                                        Log.i("Renato", it)
                                    }.toString())
                                    previewMovie.getFormatLabels().forEach { format ->
                                        item {
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
                            Text("Get Ticket",
                                modifier = Modifier.padding(vertical = 4.dp),
                                color = customColors.textFixed)
                        }

                    }
                }
            }
        }
    }
}
