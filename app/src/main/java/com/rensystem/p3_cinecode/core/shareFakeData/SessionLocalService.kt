package com.rensystem.p3_cinecode.core.shareFakeData

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rensystem.p3_cinecode.R
import com.rensystem.p3_cinecode.core.shareFakeData.model.MovieLocalResponseData
import com.rensystem.p3_cinecode.core.shareFakeData.model.SessionLocalResponseData
import java.io.InputStreamReader
import javax.inject.Inject

class SessionLocalService @Inject constructor(
    private val context: Context
) {
    suspend fun getAllLocalSession(): SessionLocalResponseData {
        val inputStream = context.resources.openRawResource(R.raw.session)
        val reader = InputStreamReader(inputStream)
        val sessionResponseType = object : TypeToken<SessionLocalResponseData>() {}.type
        return Gson().fromJson(reader, sessionResponseType)
    }
}
