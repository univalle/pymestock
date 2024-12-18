package com.example.pymestock.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pymestock.R
import com.example.pymestock.adapter.TiendaAdapter
import com.example.pymestock.viewmodel.TiendaViewModel

class ProductsFragment : Fragment(R.layout.fragment_products) {

    private lateinit var tiendaViewModel: TiendaViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var tiendaAdapter: TiendaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvProducts)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        tiendaAdapter = TiendaAdapter(emptyList())
        recyclerView.adapter = tiendaAdapter

        tiendaViewModel = ViewModelProvider(this).get(TiendaViewModel::class.java)

        // Suponiendo que el ID de usuario se obtiene de alguna parte, como en este caso, se puede pasar de esta manera
        val idUsuario = 1 // Cambiar según el caso

        tiendaViewModel.obtenerTiendasConProductos(idUsuario)

        // Observar cambios en la lista de tiendas
        tiendaViewModel.tiendaList.observe(viewLifecycleOwner, { tiendas ->
            tiendaAdapter = TiendaAdapter(tiendas)
            recyclerView.adapter = tiendaAdapter
        })
    }
}