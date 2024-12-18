package com.example.pymestock.api

import com.example.pymestock.models.ModelUsuario
import com.example.pymestock.models.RegisterResponse
import com.example.pymestock.models.Tienda
import com.example.pymestock.models.UserResponse
import com.example.pymestock.models.Usuario
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
data class LoginRequest(val username: String, val password: String)

interface ApiService {
    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<UserResponse>

    @POST("/usuario")
    fun crearUsuario(@Body usuario: Usuario): Call<RegisterResponse>

    @GET("usuario_tienda_productos/{id_usuario}")
    suspend fun getTiendasConProductos(@Path("id_usuario") idUsuario: Int): Response<List<Tienda>>

    @PATCH("desactivar_tienda/{id_tienda}")
    suspend fun desactivarTienda(@Path("id_tienda") idTienda: String): Response<Void>

    @PATCH("actualizar_tienda/{nombre_tienda}")
    fun actualizarTienda(
        @Path("nombre_tienda") nombreTienda: String,
        @Body nuevoNombre: Map<String, String>
    ): Call<Void> // Aquí usamos Call en lugar de suspend
}