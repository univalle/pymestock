package com.example.pymestock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pymestock.api.ApiService
import com.example.pymestock.api.RetrofitInstance
import com.example.pymestock.models.RegisterResponse
import com.example.pymestock.models.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterViewModel : ViewModel() {

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val apiService = RetrofitInstance.apiService

    fun register(nombre: String, correo: String, contraseña: String, rol: String) {
        val usuario = Usuario(nombre, correo, contraseña, rol)
        val call = apiService.crearUsuario(usuario)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    _registerResponse.postValue(response.body())
                } else {
                    _registerResponse.postValue(RegisterResponse(success = false, error = "Error en la respuesta"))
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _registerResponse.postValue(RegisterResponse(success = false, error = t.message ?: "Error desconocido"))
            }
        })
    }
}