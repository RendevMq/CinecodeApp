package com.rensystem.p3_cinecode.feature.moviesFt.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.TheaterMovieItem
import com.rensystem.p3_cinecode.feature.moviesFt.domain.model.getLabel
import com.rensystem.p3_cinecode.ui.theme.LocalBrandColors
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TheaterOptionItem(
    theater: TheaterMovieItem,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    // Animar la rotación del icono: 0f cuando cerrado, 180f cuando abierto
    val rotation by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)
    val customColors = LocalBrandColors.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxHeight()) {
                Text(
                    text = theater.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = customColors.textSecondary
                )
                Text(
                    text = theater.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.rotate(rotation)
            )
        }
        // Animar la visibilidad con expand/shrink vertical suave
// Dentro del AnimatedVisibility
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))

                val grouped = theater.showTime
                    ?.groupBy { session ->
                        val formats = session.formats.map { it.getLabel() }.sorted()
                        val languages = session.languages.map { it.name }.sorted()
                        Pair(formats, languages)
                    }
                    ?: emptyMap()

                grouped.forEach { (key, sessions) ->
                    val (formats, languages) = key
                    val formatLabel = formats.joinToString(" - ")
                    val languageLabel = languages.joinToString(" / ")

                    // Encabezado de cada grupo
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = formatLabel,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = languageLabel,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }

                    // Mostrar los horarios en cuadrícula adaptable
                    LazyVerticalGrid (
                        columns = GridCells.Adaptive(minSize = 80.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp) // Ajustable según tu layout
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        userScrollEnabled = false
                    ) {
                        items(sessions) { session ->
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

                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}


// Mostrar todos los showtimes que cumplen con esa combinación
//sessions.forEach { session ->
//    Text(
//        text = "- ${session.showtime}",
//        style = MaterialTheme.typography.bodySmall,
//        modifier = Modifier.padding(start = 16.dp, top = 2.dp),
//        color = Color.DarkGray
//    )
//}