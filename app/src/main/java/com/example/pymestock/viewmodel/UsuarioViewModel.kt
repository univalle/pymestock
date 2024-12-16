package com.example.pymestock.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pymestock.models.ModelUsuario


class UsuarioViewModel : ViewModel() {
    private val _datalistUsuario: MutableLiveData<MutableList<ModelUsuario>> =
        MutableLiveData(mutableListOf())

    val datalistUsuario: MutableLiveData<MutableList<ModelUsuario>>
        get() = _datalistUsuario


     // Agrega una lista de usuarios a la lista existente.

    fun addUsuarioList(mUsuario: MutableList<ModelUsuario>) {
        val currentList = datalistUsuario.value ?: mutableListOf()
        currentList.addAll(mUsuario)
        datalistUsuario.postValue(currentList)
    }


     // Agrega un solo usuario a la lista existente.

    fun addUsuario(mUsuario: ModelUsuario) {
        val currentList = datalistUsuario.value ?: mutableListOf()
        currentList.add(mUsuario)
        datalistUsuario.postValue(currentList)
    }


     // Actualiza un usuario en una posición específica de la lista.

    fun updateUsuario(position: Int, mUsuario: ModelUsuario) {
        val currentList = datalistUsuario.value ?: mutableListOf()
        currentList[position] = mUsuario
        datalistUsuario.postValue(currentList)
    }


     // Elimina un usuario en una posición específica de la lista.

    fun removeUsuario(position: Int) {
        val currentList = datalistUsuario.value ?: mutableListOf()
        if (position >= 0 && position < currentList.size) {
            currentList.removeAt(position)
            datalistUsuario.postValue(currentList)
        }
    }


     // Obtiene un usuario por su ID.

    fun getUsuarioById(id: Int): ModelUsuario? {
        return datalistUsuario.value?.find { it.id == id }
    }


     // Actualiza el rol de un usuario específico.

    fun updateRolUsuario(id: Int, nuevoRol: String) {
        val currentList = datalistUsuario.value ?: mutableListOf()
        val usuario = currentList.find { it.id == id }
        usuario?.let {
            it.rol = nuevoRol
            datalistUsuario.postValue(currentList)
        }
    }


     // Actualiza la contraseña de un usuario específico.

    fun updateContraseñaUsuario(id: Int, nuevaContraseña: String) {
        val currentList = datalistUsuario.value ?: mutableListOf()
        val usuario = currentList.find { it.id == id }
        usuario?.let {
            it.contraseña = nuevaContraseña
            datalistUsuario.postValue(currentList)
        }
    }
}