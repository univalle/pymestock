package com.example.pymestock.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pymestock.models.ModelTienda

class TiendaViewModel : ViewModel() {
    private val _datalistTienda: MutableLiveData<MutableList<ModelTienda>> =
        MutableLiveData(mutableListOf())

    val datalistTienda: MutableLiveData<MutableList<ModelTienda>>
        get() = _datalistTienda


     // Agrega una lista de tiendas a la lista existente.

    fun addTiendaList(mTienda: MutableList<ModelTienda>) {
        val currentList = datalistTienda.value ?: mutableListOf()
        currentList.addAll(mTienda)
        datalistTienda.postValue(currentList)
    }


     // Agrega una sola tienda a la lista existente.

    fun addTienda(mTienda: ModelTienda) {
        val currentList = datalistTienda.value ?: mutableListOf()
        currentList.add(mTienda)
        datalistTienda.postValue(currentList)
    }


     //Actualiza una tienda en una posición específica de la lista.

    fun updateTienda(position: Int, mTienda: ModelTienda) {
        val currentList = datalistTienda.value ?: mutableListOf()
        currentList[position] = mTienda
        datalistTienda.postValue(currentList)
    }


     //Elimina una tienda en una posición específica de la lista.

    fun removeTienda(position: Int) {
        val currentList = datalistTienda.value ?: mutableListOf()
        if (position >= 0 && position < currentList.size) {
            currentList.removeAt(position)
            datalistTienda.postValue(currentList)
        }
    }


     // Obtiene una tienda por su ID.

    fun getTiendaById(id: Int): ModelTienda? {
        return datalistTienda.value?.find { it.id == id }
    }


     //Actualiza el nombre de una tienda específica.

    fun updateNombreTienda(id: Int, nuevoNombre: String) {
        val currentList = datalistTienda.value ?: mutableListOf()
        val tienda = currentList.find { it.id == id }
        tienda?.let {
            it.nombre = nuevoNombre
            datalistTienda.postValue(currentList)
        }
    }
}