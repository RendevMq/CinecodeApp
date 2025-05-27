package com.rensystem.p3_cinecode.feature.theatersFt.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rensystem.p3_cinecode.R
import com.rensystem.p3_cinecode.feature.theatersFt.data.model.TheaterLocalResponseData
import java.io.InputStreamReader
import javax.inject.Inject

class TheaterLocalService @Inject constructor(
    private val context: Context
) {
    suspend fun getAllLocalTheater(): TheaterLocalResponseData {
        val inputStream = context.resources.openRawResource(R.raw.cinemas)
        val reader = InputStreamReader(inputStream)
        val theaterResponseType = object : TypeToken<TheaterLocalResponseData>() {}.type
        return Gson().fromJson(reader, theaterResponseType)
    }
}