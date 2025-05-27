package com.rensystem.p3_cinecode.core.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.ui.graphics.vector.ImageVector

interface FilterOption {
    val text: String
    val icon: ImageVector
}

enum class MovieMainFilters(
    override val text: String,
    override val icon: ImageVector
) : FilterOption {
    City("Ciudad", Icons.Default.LocationOn),
    Date("Fecha", Icons.Default.CalendarMonth),
    Filter("Filtro", Icons.Outlined.FilterAlt)
}

enum class TheaterFilters(
    override val text: String,
    override val icon: ImageVector
) : FilterOption {
    City("Ciudad", Icons.Default.LocationOn),
    Room("Sala", Icons.Outlined.Tv),
}

enum class MovieDetailFilters(
    override val text: String,
    override val icon: ImageVector
) : FilterOption {
    City("Ciudad", Icons.Default.LocationOn),
    Theater("Cine", Icons.Filled.Videocam),
    Date("Fecha", Icons.Default.CalendarMonth)
}
