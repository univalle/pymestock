package com.example.pymestock.models

data class Producto(
    val nombre: String,
    val identificador: String,
    val cantidad: Int,
    val fechaVencimiento: String?
)