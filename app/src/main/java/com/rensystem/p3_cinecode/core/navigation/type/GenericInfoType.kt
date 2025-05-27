package com.rensystem.p3_cinecode.core.navigation.type


import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T : Parcelable> createNavType():NavType<T>{
    return object : NavType<T>(isNullableAllowed = true){

        override fun get(bundle: Bundle, key: String): T? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, T::class.java)
            } else {
                bundle.getParcelable(key) //sino lo hacemos de la forma antigua
            }
        }

        override fun parseValue(value: String): T {
            //de json a objeto T
            return Json.decodeFromString<T>(value)
        }

        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putParcelable(key, value)
        }

        //Un utlimo metodo , que por defecto no lo pone
        override fun serializeAsValue(value: T): String {
            //Cogemos el objeto SettingsInfo y lo convertimos en json
            return Uri.encode(Json.encodeToString(value))
        }

    }
}