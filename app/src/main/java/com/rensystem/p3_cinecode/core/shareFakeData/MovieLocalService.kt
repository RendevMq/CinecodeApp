package com.rensystem.p3_cinecode.core.shareFakeData

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rensystem.p3_cinecode.R
import com.rensystem.p3_cinecode.core.shareFakeData.model.MovieLocalResponseData
import java.io.InputStreamReader
import javax.inject.Inject

class MovieLocalService @Inject constructor(
    private val context: Context
) {
    suspend fun getAllLocalMovies(): MovieLocalResponseData {
        val inputStream = context.resources.openRawResource(R.raw.movies)
        val reader = InputStreamReader(inputStream)
        val movieResponseType = object : TypeToken<MovieLocalResponseData>() {}.type
        return Gson().fromJson(reader, movieResponseType)
    }
}