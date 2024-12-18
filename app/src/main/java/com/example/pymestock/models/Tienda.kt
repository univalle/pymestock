package com.example.pymestock.models

data class Tienda(
    val tienda: String,
    val ubicacion: String,
    val productos: List<Producto>
)