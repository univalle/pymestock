package com.example.pymestock.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pymestock.R
import com.example.pymestock.adapter.TiendaAdapter
import com.example.pymestock.models.CurrentUser
import com.example.pymestock.viewmodel.TiendaViewModel

class ProductsFragment : Fragment(R.layout.fragment_products) {

    private lateinit var tiendaViewModel: TiendaViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var tiendaAdapter: TiendaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvProducts)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        // Inicializa el ViewModel
        tiendaViewModel = ViewModelProvider(this).get(TiendaViewModel::class.java)

        // Obtener el ID del usuario actual
        val idUsuario = CurrentUser.getId()

        // Obtener la lista de tiendas activas con productos
        tiendaViewModel.obtenerTiendasConProductos(idUsuario)

        // Observar cambios en la lista de tiendas
        tiendaViewModel.tiendaList.observe(viewLifecycleOwner, { tiendas ->
            tiendaAdapter = TiendaAdapter(tiendas, tiendaViewModel) // Pasar el ViewModel al adaptador
            recyclerView.adapter = tiendaAdapter
        })
    }
}