package com.example.pymestock.models

data class Usuario(
    val nombre: String,
    val correo: String,
    val contraseña: String,
    val rol: String = "usuario"  // Rol por defecto
)