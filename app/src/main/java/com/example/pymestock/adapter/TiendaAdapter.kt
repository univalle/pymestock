package com.example.pymestock.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pymestock.R
import com.example.pymestock.models.Tienda
import com.example.pymestock.viewmodel.TiendaViewModel
import com.example.pymestock.views.EditProductFragment

class TiendaAdapter(
    private val tiendaList: List<Tienda>,
    private val viewModel: TiendaViewModel // Pasamos el ViewModel como dependencia
) : RecyclerView.Adapter<TiendaAdapter.TiendaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TiendaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tienda, parent, false)
        return TiendaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TiendaViewHolder, position: Int) {
        val tienda = tiendaList[position]
        holder.nombreTienda.text = tienda.tienda
        holder.ubicacion.text = "Ubicación: ${tienda.ubicacion}"
        val totalProductos = tienda.productos.size
        holder.productos.text = "Total de productos: $totalProductos"

        // Configurar el listener para el botón de eliminar
        holder.deleteButton.setOnClickListener {
            // Llamar al método desactivarTienda del ViewModel con el id de la tienda
            Log.d("TiendaAdapter", "Desactivar tienda ${tienda}")
            val nombre = tienda.tienda
            viewModel.desactivarTienda(nombre) // Suponiendo que Tienda tiene un campo `id`
        }

        holder.edit.setOnClickListener {
            Log.d("TiendaAdapter", "Editar tienda ${tienda}")

            // Obtener el fragment manager y comenzar una transacción para reemplazar el fragmento
            val fragmentTransaction = (holder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()

            // Crear el fragmento EditProductFragment
            val editProductFragment = EditProductFragment()

            // Crear un bundle para pasar datos al fragmento
            val bundle = Bundle()
            bundle.putString("nombre_tienda", tienda.tienda)
            bundle.putParcelableArrayList("productos", ArrayList(tienda.productos)) // Si deseas pasar los productos
            editProductFragment.arguments = bundle

            // Reemplazar el fragmento actual con el nuevo fragmento
            fragmentTransaction.replace(R.id.fragment_container, editProductFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit() // Realiza la transacción
        }


    }

    override fun getItemCount(): Int {
        return tiendaList.size
    }

    class TiendaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTienda: TextView = itemView.findViewById(R.id.itemZone)
        val ubicacion: TextView = itemView.findViewById(R.id.itemQuantity)
        val productos: TextView = itemView.findViewById(R.id.productos)
        val selectItem: CheckBox = itemView.findViewById(R.id.selectItem)
        val arrow: ImageView = itemView.findViewById(R.id.arrow)
        val deleteButton: ImageView = itemView.findViewById(R.id.delete) // Referencia al botón de eliminar
        val edit = itemView.findViewById<ImageView>(
            R.id.edit
        )
    }
}