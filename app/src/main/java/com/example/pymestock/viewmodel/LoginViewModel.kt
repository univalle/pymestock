package com.example.pymestock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pymestock.api.LoginRequest  // Asegúrate de importar la clase LoginRequest desde el paquete correcto
import com.example.pymestock.api.RetrofitInstance
import com.example.pymestock.models.ModelUsuario
import com.example.pymestock.utils.CustomToastUtil
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _loginResponse = MutableLiveData<Response<ModelUsuario>>()
    val loginResponse: LiveData<Response<ModelUsuario>> get() = _loginResponse

    private val apiService = RetrofitInstance.apiService  // Usando RetrofitInstance para el servicio

    // Función para realizar el login
    fun login(username: String, password: String) {
        Log.d("LoginViewModel", "Iniciando proceso de login")
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Realizando llamada a la API con username: $username y password: $password")

                // Crear el objeto LoginRequest con los datos proporcionados
                val loginRequest = LoginRequest(username, password)

                // Realizar la llamada a la API con el cuerpo JSON
                val response = apiService.login(loginRequest)  // Cambié aquí a 'login' en lugar de 'consultarUsuario'

                // Revisar el estado de la respuesta
                if (response.isSuccessful) {
                    Log.d("LoginViewModel", "Login exitoso. Código de respuesta: ${response.code()}")
                    _loginResponse.value = response
                } else {
                    Log.d("LoginViewModel", "Error en la respuesta. Código: ${response.code()}, Mensaje: ${response.message()}")
                    // Si la respuesta no fue exitosa, obtener el cuerpo de error
                    val errorBody = response.errorBody()?.string() ?: "Error desconocido"

                    // Pasar el error al LiveData para que el Fragment lo maneje
                    _loginResponse.value = Response.error(
                        response.code(),
                        ResponseBody.create(
                            MediaType.parse("text/plain"),
                            errorBody
                        )
                    )
                }

            } catch (e: Exception) {
                Log.d("LoginViewModel", "Excepción durante la solicitud: ${e.localizedMessage}")
                // En caso de error de red o algún otro tipo de error
                _loginResponse.value = Response.error(
                    500,
                    ResponseBody.create(
                        MediaType.parse("text/plain"),
                        "Error de red: ${e.localizedMessage}"
                    )
                )
            }
        }
    }
}
