import com.example.pymestock.models.ModelProducto
import com.example.pymestock.models.ModelTienda
import com.example.pymestock.models.ModelUbicacion
import com.example.pymestock.models.ModelUsuario
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface conexiondb {
    companion object {
        const val url: String = "http://localhost:8080"
    }

//    fun create(): conexiondb {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        return retrofit.create(conexiondb::class.java)
//    }

    // Métodos para ModelUsuario
    @GET("/consultarUsuario")
    suspend fun ConsultarUsuarios(): retrofit2.Response<List<ModelUsuario>>

    @POST("/insertarUsuario")
    suspend fun InsertarUsuario(@Body mUsuario: ModelUsuario): retrofit2.Response<ModelUsuario>

    // Métodos para ModelProducto
    @GET("/consultarProducto")
    suspend fun ConsultarProductos(): retrofit2.Response<List<ModelProducto>>

    @POST("/insertarProducto")
    suspend fun InsertarProducto(@Body mProducto: ModelProducto): retrofit2.Response<ModelProducto>

    // Métodos para ModelTienda
    @GET("/consultarTienda")
    suspend fun ConsultarTiendas(): retrofit2.Response<List<ModelTienda>>

    @POST("/insertarTienda")
    suspend fun InsertarTienda(@Body mTienda: ModelTienda): retrofit2.Response<ModelTienda>

    // Métodos para ModelUbicacion
    @GET("/consultarUbicacion")
    suspend fun ConsultarUbicaciones(): retrofit2.Response<List<ModelUbicacion>>

    @POST("/insertarUbicacion")
    suspend fun InsertarUbicacion(@Body mUbicacion: ModelUbicacion): retrofit2.Response<ModelUbicacion>
}