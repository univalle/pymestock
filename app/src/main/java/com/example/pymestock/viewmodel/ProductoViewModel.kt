package com.example.pymestock.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pymestock.models.ModelProducto


class ProductoViewModel : ViewModel() {

    private val _datalistProducto: MutableLiveData<MutableList<ModelProducto>> =
    MutableLiveData(mutableListOf())


 
     // Agrega un solo producto a la lista existente.
  
    fun addProducto(mProd: ModelProducto) {
        val currentList = datalistProducto.value ?: mutableListOf()
        currentList.add(mProd)
        datalistProducto.postValue(currentList)
    }

  
     // Actualiza un producto en una posición específica de la lista.
     
    fun updateProducto(position: Int, mProd: ModelProducto) {
        val currentList = datalistProducto.value ?: mutableListOf()
        currentList[position] = mProd
        datalistProducto.postValue(currentList)
    }

 
     // Elimina un producto en una posición específica de la lista.

    fun removeProducto(position: Int) {
        val currentList = datalistProducto.value ?: mutableListOf()
        if (position >= 0 && position < currentList.size) {
            currentList.removeAt(position)
            datalistProducto.postValue(currentList)
        }
    }

  
     // Obtiene un producto por su ID.
   
    fun getProductoById(id: Int): ModelProducto? {
        return datalistProducto.value?.find { it.id == id }
    }


    // Actualiza la cantidad de un producto específico.
  
    fun updateCantidadProducto(id: Int, nuevaCantidad: Int) {
        val currentList = datalistProducto.value ?: mutableListOf()
        val producto = currentList.find { it.id == id }
        producto?.let {
            it.cantidad = nuevaCantidad
            datalistProducto.postValue(currentList)
        }
    }


    val datalistProducto: MutableLiveData<MutableList<ModelProducto>>
        get() = _datalistProducto

    fun addProductoList(mProd: MutableList<ModelProducto>) {
        val currentList = datalistProducto.value ?: mutableListOf()
        currentList.addAll(mProd)
        datalistProducto.postValue(currentList)
    }

//    suspend fun fetchProductosFromApi(): Response<List<ModelProducto>> {
//        return withContext(Dispatchers.IO) {
//            conexiondb.create().ConsultarProductos()
//        }
//    }
}