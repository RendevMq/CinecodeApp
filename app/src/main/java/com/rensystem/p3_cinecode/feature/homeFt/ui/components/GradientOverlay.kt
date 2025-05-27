package com.rensystem.p3_cinecode.feature.homeFt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun GradientOverlay(modifier: Modifier = Modifier) {
    // Gradiente desde un negro oscuro hacia transparente (mitad del alto)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f) // Utiliza la mitad de la altura total
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent, // Parte superior completamente transparente
                        Color.Black.copy(alpha = 0.1f),
                        Color.Black.copy(alpha = 0.3f),
                        Color.Black.copy(alpha = 0.5f),
                        Color.Black.copy(alpha = 0.7f),
                        Color.Black.copy(alpha = 0.8f),
                        Color.Black.copy(alpha = 0.9f) // Parte inferior negra con algo de transparencia
                    )
                )
            )
//            .align(Alignment.BottomCenter) // Alineamos al centro
    )
}