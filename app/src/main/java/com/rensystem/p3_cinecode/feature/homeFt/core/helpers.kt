package com.rensystem.p3_cinecode.feature.homeFt.core

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

fun openYouTubeUrl(context: Context, url: String) {
    // Detectar si la URL es del tipo https://youtu.be o https://www.youtube.com/watch
    val videoId = when {
        url.contains("youtu.be") -> url.substringAfter("https://youtu.be/").substringBefore("?")
        url.contains("youtube.com/watch") -> url.substringAfter("v=").substringBefore("&")
        else -> ""
    }

    if (videoId.isNotEmpty()) {
        // Intent para abrir la aplicación YouTube
        val uri = Uri.parse("vnd.youtube:$videoId")
        val appIntent = Intent(Intent.ACTION_VIEW, uri)

        // Intent de respaldo para abrir en el navegador si no hay app de YouTube
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        try {
            context.startActivity(appIntent)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }
    }
}
