package com.example.pymestock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pymestock.models.ModelProducto
import com.example.pymestock.utils.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductoViewModel : ViewModel() {

    private val _productos = MutableLiveData<List<ModelProducto>>()
    val productos: LiveData<List<ModelProducto>> get() = _productos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val apiService = RetrofitInstance.getRetrofitInstance().create(conexiondb::class.java)

    // Obtener todos los productos
    fun obtenerProductos() {
        viewModelScope.launch {
            try {
                val response: Response<List<ModelProducto>> = apiService.ConsultarProductos()
                if (response.isSuccessful) {
                    _productos.value = response.body()
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.localizedMessage}"
            }
        }
    }

    fun insertarProducto(producto: ModelProducto) {
        viewModelScope.launch {
            try {
                val response: Response<ModelProducto> = apiService.InsertarProducto(producto)
                if (response.isSuccessful) {
                    // Si la respuesta es exitosa, obtenemos el producto insertado
                    val productoInsertado = response.body()

                    // Verifica si el producto insertado no es null antes de actualizar la lista
                    if (productoInsertado != null) {
                        val currentList = _productos.value?.toMutableList() ?: mutableListOf()
                        currentList.add(productoInsertado)
                        _productos.value = currentList
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