package com.example.pymestock.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Cambia la URL por la url de servidor
    private const val BASE_URL = "http://192.168.1.13:8080"

    private var retrofit: Retrofit? = null

    // Función para obtener la instancia de Retrofit
    fun getRetrofitInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL) // Define la URL base de tu API
                .addConverterFactory(GsonConverterFactory.create()) // Usa Gson para convertir las respuestas
                .build()
        }
        return retrofit!!
    }
}
