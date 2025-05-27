package com.rensystem.p3_cinecode.feature.homeFt.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieSummaryUI(
    val name: String,
    val imageUrl: String,
    val duration: String,
    val rating: String,
    val genre: String
)