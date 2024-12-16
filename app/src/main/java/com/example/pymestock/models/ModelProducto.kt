package com.example.pymestock.models

data class ModelProducto(
    val id: Int,
    val idTienda: Int,
    val nombre: String,
    val identificador: String,
    val fechaVencimiento: String,
    val cantidad: Int,
    val imagenUrl: String,
    val qrCode: String
)