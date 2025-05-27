package com.rensystem.p3_cinecode.feature.homeFt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun GradientOverlayWithColor(
    baseColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to baseColor.copy(alpha = 0f),
                        0.15f to baseColor.copy(alpha = 0.05f),
                        0.3f to baseColor.copy(alpha = 0.1f),
                        0.45f to baseColor.copy(alpha = 0.2f),
                        0.6f to baseColor.copy(alpha = 0.4f),
                        0.75f to baseColor.copy(alpha = 0.6f),
//                        0.9f to baseColor.copy(alpha = 0.8f),
//                        1.0f to baseColor.copy(alpha = 1f)
                    )
                )
            )
    )
}