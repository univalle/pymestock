package com.example.pymestock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pymestock.api.RetrofitInstance
import com.example.pymestock.models.Tienda
import kotlinx.coroutines.launch
import retrofit2.Response

class TiendaViewModel : ViewModel() {

    private val _tiendaList = MutableLiveData<List<Tienda>>()
    val tiendaList: LiveData<List<Tienda>> get() = _tiendaList

    // Función para obtener las tiendas con productos desde la API
    fun obtenerTiendasConProductos(idUsuario: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getTiendasConProductos(idUsuario)
                if (response.isSuccessful) {
                    _tiendaList.value = response.body()
                } else {
                    Log.d("TiendaViewModel", "Error en la respuesta. Código: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.d("TiendaViewModel", "Excepción durante la solicitud: ${e.localizedMessage}")
            }
        }
    }
}