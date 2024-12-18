package com.example.pymestock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pymestock.models.ModelUbicacion
import com.example.pymestock.utils.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class UbicacionViewModel : ViewModel() {

    private val _ubicaciones = MutableLiveData<List<ModelUbicacion>>()
    val ubicaciones: LiveData<List<ModelUbicacion>> get() = _ubicaciones

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val apiService = RetrofitInstance.getRetrofitInstance().create(conexiondb::class.java)

    // Obtener todas las ubicaciones
    fun obtenerUbicaciones() {
        viewModelScope.launch {
            try {
                val response: Response<List<ModelUbicacion>> = apiService.ConsultarUbicaciones()
                if (response.isSuccessful) {
                    _ubicaciones.value = response.body()
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.localizedMessage}"
            }
        }
    }

    // Insertar una ubicación
    fun insertarUbicacion(ubicacion: ModelUbicacion) {
        viewModelScope.launch {
            try {
                val response: Response<ModelUbicacion> = apiService.InsertarUbicacion(ubicacion)
                if (response.isSuccessful) {
                    // Si la respuesta es exitosa, obtenemos la ubicación insertada
                    val ubicacionInsertada = response.body()

                    // Verifica si la ubicación insertada no es null antes de actualizar la lista
                    if (ubicacionInsertada != null) {
                        // Obtén la lista actual de ubicaciones o crea una lista vacía si no existe
                        val currentList = _ubicaciones.value?.toMutableList() ?: mutableListOf()
                        currentList.add(ubicacionInsertada)
                        _ubicaciones.value = currentList
                    }
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.localizedMessage}"
            }
        }
    }
}