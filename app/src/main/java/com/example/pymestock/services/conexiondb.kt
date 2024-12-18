import com.example.pymestock.models.ModelProducto
import com.example.pymestock.models.Tienda
import com.example.pymestock.models.ModelUbicacion
import com.example.pymestock.models.ModelUsuario
import com.example.pymestock.models.RegisterResponse
import com.example.pymestock.models.Usuario
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface conexiondb {

    companion object {
        const val url: String = "http://localhost:8080"  // Ajusta la URL si estás usando otro servidor o IP
    }


    // Métodos para ModelUsuario
    @GET("/usuarios")
    suspend fun ConsultarUsuarios(): retrofit2.Response<List<ModelUsuario>>

    @POST("/usuario")
    fun crearUsuario(@Body usuario: Usuario): Call<RegisterResponse>

    // Métodos para ModelProducto
    @GET("/productos")
    suspend fun ConsultarProductos(): retrofit2.Response<List<ModelProducto>>

    @POST("/producto")
    suspend fun InsertarProducto(@Body mProducto: ModelProducto): retrofit2.Response<ModelProducto>

    // Métodos para ModelTienda
    @GET("/tiendas")
    suspend fun ConsultarTiendas(): retrofit2.Response<List<Tienda>>

    @POST("/tienda")
    suspend fun InsertarTienda(@Body mTienda: Tienda): retrofit2.Response<Tienda>

    // Método para obtener todas las tiendas con sus productos y ubicaciones
    @GET("/tiendasConProductos")
    suspend fun ConsultarTiendasConProductos(): retrofit2.Response<List<Tienda>>

    // Método para obtener solo las tiendas de un usuario con sus ubicaciones y productos
    @GET("/tiendasDeUsuario/{idUsuario}")
    suspend fun ConsultarTiendasDeUsuario(@Path("idUsuario") idUsuario: Int): retrofit2.Response<List<Tienda>>

    // Métodos para ModelUbicacion
    @GET("/ubicaciones")
    suspend fun ConsultarUbicaciones(): retrofit2.Response<List<ModelUbicacion>>

    @POST("/ubicacion")
    suspend fun InsertarUbicacion(@Body mUbicacion: ModelUbicacion): retrofit2.Response<ModelUbicacion>
}
