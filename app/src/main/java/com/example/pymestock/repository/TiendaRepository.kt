package com.example.pymestock.repository

object TiendaRepository {
    private val tiendas = mutableListOf<TiendaRepository>()

    fun agregarTienda(tienda: TiendaRepository) {
        tiendas.add(tienda)
    }


    }