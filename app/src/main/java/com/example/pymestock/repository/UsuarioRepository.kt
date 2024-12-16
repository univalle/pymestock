package com.example.pymestock.repository

import com.example.pymestock.models.ModelUsuario

object UsuarioRepository {
    private val usuarios = mutableListOf(
        ModelUsuario(1, "Admin", "admin@admin.com", "admin123", "admin"),
        ModelUsuario(2, "Usuario1", "user1@correo.com", "user123", "usuario")
    )

    fun getUsuario(correo: String, contraseña: String): ModelUsuario? {
        return usuarios.find { it.correo == correo && it.contraseña == contraseña }
    }

    fun agregarUsuario(usuario: ModelUsuario) {
        usuarios.add(usuario)
    }

    fun obtenerUsuarios(): List<ModelUsuario> = usuarios
}