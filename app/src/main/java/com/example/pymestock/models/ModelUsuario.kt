package com.example.pymestock.models

data class ModelUsuario(
    val id: Int,
    val nombre: String,
    val correo: String,
    val contraseña: String,
    val rol: String // Valores: "admin" o "usuario"
)