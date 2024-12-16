package com.example.pymestock.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pymestock.models.ModelUbicacion

class UbicacionViewModel : ViewModel() {
    private val _datalistUbicacion: MutableLiveData<MutableList<ModelUbicacion>> =
        MutableLiveData(mutableListOf())

    val datalistUbicacion: MutableLiveData<MutableList<ModelUbicacion>>
        get() = _datalistUbicacion

    /**
     * Agrega una lista de ubicaciones a la lista existente.
     */
    fun addUbicacionList(mUbicacion: MutableList<ModelUbicacion>) {
        val currentList = datalistUbicacion.value ?: mutableListOf()
        currentList.addAll(mUbicacion)
        datalistUbicacion.postValue(currentList)
    }

    /**
     * Agrega una sola ubicación a la lista existente.
     */
    fun addUbicacion(mUbicacion: ModelUbicacion) {
        val currentList = datalistUbicacion.value ?: mutableListOf()
        currentList.add(mUbicacion)
        datalistUbicacion.postValue(currentList)
    }

    /**
     * Actualiza una ubicación en una posición específica de la lista.
     */
    fun updateUbicacion(position: Int, mUbicacion: ModelUbicacion) {
        val currentList = datalistUbicacion.value ?: mutableListOf()
        currentList[position] = mUbicacion
        datalistUbicacion.postValue(currentList)
    }

    /**
     * Elimina una ubicación en una posición específica de la lista.
     */
    fun removeUbicacion(position: Int) {
        val currentList = datalistUbicacion.value ?: mutableListOf()
        if (position >= 0 && position < currentList.size) {
            currentList.removeAt(position)
            datalistUbicacion.postValue(currentList)
        }
    }

    /**
     * Obtiene una ubicación por su ID.
     */
    fun getUbicacionById(id: Int): ModelUbicacion? {
        return datalistUbicacion.value?.find { it.id == id }
    }

    /**
     * Actualiza el nombre de una ubicación específica.
     */
    fun updateNombreUbicacion(id: Int, nuevoNombre: String) {
        val currentList = datalistUbicacion.value ?: mutableListOf()
        val ubicacion = currentList.find { it.id == id }
        ubicacion?.let {
            it.nombre = nuevoNombre
            datalistUbicacion.postValue(currentList)
        }
    }
}