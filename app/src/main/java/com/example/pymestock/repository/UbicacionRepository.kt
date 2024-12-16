package com.example.pymestock.repository

object UbicacionRepository {
    private val ubicaciones = mutableListOf<UbicacionRepository>()

    fun agregarUbicacion(ubicacion: UbicacionRepository) {
        ubicaciones.add(ubicacion)
    }

    fun obtenerUbicaciones(): List<UbicacionRepository> = ubicaciones
}