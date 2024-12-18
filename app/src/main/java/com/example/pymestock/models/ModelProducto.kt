package com.example.pymestock.models

data class ModelProducto(
    val idProducto: Int,
    val idTienda: Int,
    val nombre: String,
    val identificador: String,
    val fechaVencimiento: String,
    var cantidad: Int,
    val qrCode: String
)