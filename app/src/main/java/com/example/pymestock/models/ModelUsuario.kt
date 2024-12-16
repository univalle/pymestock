package com.example.pymestock.models

data class ModelUsuario(
    val id: Int,
    val nombre: String,
    val correo: String,
    var contraseña: String,
    var rol: String // Valores: "admin" o "usuario"
)