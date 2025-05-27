package com.rensystem.p3_cinecode.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class BrandColors(
    val backgroundMainCardColor: Color,
    val titleTextColor: Color,
    val specialButtonColor: Color,

    // Nuevos colores
    val textFixed: Color,             // Siempre blanco
    val textThemed: Color,            // Cambia según el tema
    val textSecondary: Color,
    val topBarTextColor: Color,

    val glassButtonBackground: Color, // Fondo circular semi-transparente
    val tagTrailerBackground: Color,
    val tagReleaseBackground: Color,
    val formatLabelBackground: Color,
    val genreLabelBackground: Color,
    val overlayInfoBackground: Color,
    val cardSecondaryBackground:Color,

    // BottomBar
    val bottomBarBackgroundColor: Color,
    val bottomBarSelectedIconColor: Color,
    val bottomBarUnselectedIconColor: Color,
    val bottomBarSelectedTextColor: Color,
    val bottomBarUnselectedTextColor: Color
)

val LightBrandColors = BrandColors(
    backgroundMainCardColor = Color(0xFFE0E0E0),
    titleTextColor = Color(0xFF000000),
    specialButtonColor = Color(0xFFFA906E),

    textFixed = Color.White,
    textThemed = Color.Black,
    textSecondary = Color(0xFF522B1E),
    topBarTextColor = Color(0xFFCD5F3A),

    glassButtonBackground = Color.White.copy(alpha = 0.2f),
    tagTrailerBackground = Color.Black.copy(alpha = 0.5f),
    tagReleaseBackground = Color.Red.copy(alpha = 0.5f),
    formatLabelBackground = Color(0xC1761C01).copy(alpha = 0.9f),
    genreLabelBackground = Color(0xff2A2829).copy(alpha = 0.5f),
    overlayInfoBackground = Color(0x70030307),
    cardSecondaryBackground = Color(0xFF5C5C5C),

    bottomBarBackgroundColor = Color(0xFFFFFFFF),
    bottomBarSelectedIconColor = Color(0xFF1976D2),
    bottomBarUnselectedIconColor = Color(0xFF9E9E9E),
    bottomBarSelectedTextColor = Color(0xFF1976D2),
    bottomBarUnselectedTextColor = Color(0xFF9E9E9E)
)


val DarkBrandColors = BrandColors(
    backgroundMainCardColor = Color(0xFF2C2C2C),
    titleTextColor = Color(0xFFFFFFFF),
    specialButtonColor = Color(0xFFFF6E40),


    textFixed = Color.White,
    textThemed = Color.White,
    topBarTextColor = Color(0xFFD9856B),
    textSecondary = Color(0xFFFCB299),

    glassButtonBackground = Color.White.copy(alpha = 0.2f),
    tagTrailerBackground = Color.Black.copy(alpha = 0.5f),
    tagReleaseBackground = Color.Red.copy(alpha = 0.5f),
    formatLabelBackground = Color(0xC1761C01).copy(alpha = 0.9f),
    genreLabelBackground = Color(0xff2A2829).copy(alpha = 0.5f),
    overlayInfoBackground = Color(0x70030307),
    cardSecondaryBackground = Color(0xFF181818),

    bottomBarBackgroundColor = Color(0xFF121212),
    bottomBarSelectedIconColor = Color(0xFF90CAF9),
    bottomBarUnselectedIconColor = Color(0xFF757575),
    bottomBarSelectedTextColor = Color(0xFF90CAF9),
    bottomBarUnselectedTextColor = Color(0xFF757575)
)


val LocalBrandColors = staticCompositionLocalOf { LightBrandColors }
