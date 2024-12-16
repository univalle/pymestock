import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pymestock.R
import com.example.pymestock.adapter.ProductoAdapter
import com.example.pymestock.databinding.FragmentProductsBinding
import com.example.pymestock.models.ModelProducto
import com.example.pymestock.viewmodel.ProductoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit


class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val productoViewModel: ProductoViewModel by viewModels()
    private lateinit var productoAdapter: ProductoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observerViewModel()
//        listarProductos()
    }

//    private fun listarProductos() {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val call = getRetrofit().create(conexiondb::class.java).ConsultarProductos()
//                if (call.isSuccessful && call.body() != null) {
//                    withContext(Dispatchers.Main) {
//                        productoViewModel.addProductoList(call.body()!!.toMutableList())
//                    }
//                } else {
//                    Log.e("ProductsFragment", "Error: No se encontró la información")
//                }
//            } catch (e: Exception) {
//                Log.e("ProductsFragment", "No se pudo conectar a la base de datos", e)
//            }
//        }
//    }

//    private fun getRetrofit(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(conexiondb.url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }

    private fun setupRecyclerView() {
        productoAdapter = ProductoAdapter(emptyList()) { producto, position ->
            // Maneja el clic en el producto
        }
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productoAdapter
        }
    }

    private fun observerViewModel() {
        productoViewModel.datalistProducto.observe(viewLifecycleOwner, Observer { productos ->
            productoAdapter.productosList = productos
            productoAdapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}