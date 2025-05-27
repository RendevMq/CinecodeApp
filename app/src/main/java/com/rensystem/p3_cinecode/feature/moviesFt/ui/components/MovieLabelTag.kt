package com.rensystem.p3_cinecode.feature.moviesFt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MovieLabelTag(
    modifier: Modifier,
    isPreSale: Boolean,
    isRePremiere: Boolean,
    isRemake: Boolean,
    isNewRelease: Boolean
) {
    val (label, color) = when {
        isPreSale -> "PREVENTA" to Color(0xBC00187F) // Rosado fuerte
        isRePremiere -> "REESTRENO" to Color(0xC3005207) // Verde agua
        isRemake -> "REMAKE" to Color(0xDAAC7700) // Amarillo moderno
        isNewRelease -> "ESTRENO" to Color(0xB2820202) // Azul moderno
        else -> null to null
//        isPreSale -> "PREVENTA" to Color(0xFFEF476F) // Rosado fuerte
//        isRePremiere -> "REESTRENO" to Color(0xFF06D6A0) // Verde agua
//        isRemake -> "REMAKE" to Color(0xFFFFD166) // Amarillo moderno
//        isNewRelease -> "ESTRENO" to Color(0xFF118AB2) // Azul moderno
//        else -> null to null
    }

    if (label != null && color != null) {
        Box(
            modifier = modifier
                .padding(8.dp)
                .background(
                    color.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(bottomEnd = 12.dp, topStart = 12.dp)
                )
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(
                text = label,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        }
    }
}
