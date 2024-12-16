package com.example.pymestock.repository

import com.example.pymestock.models.ModelProducto

object ProductoRepository {
    private val productos = mutableListOf<ModelProducto>()

    fun agregarProducto(producto: ModelProducto) {
        productos.add(producto)
    }

    fun obtenerProductosPorTienda(idTienda: Int): List<ModelProducto> {
        return productos.filter { it.idTienda == idTienda }
    }

    fun actualizarCantidad(idProducto: Int, cantidad: Int) {
        val producto = productos.find { it.id == idProducto }
        producto?.let {
            it.cantidad = cantidad
        }
    }
}