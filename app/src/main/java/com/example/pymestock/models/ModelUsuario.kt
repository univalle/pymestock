package com.example.pymestock.models

data class ModelUsuario(
    val idUsuario: Int,
    val nombre: String,
    val correo: String,
    val contraseña: String,
    val rol: String
)