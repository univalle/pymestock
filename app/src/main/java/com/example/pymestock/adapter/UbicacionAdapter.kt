package com.example.pymestock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pymestock.R
import com.example.pymestock.databinding.ItemUbicacionBinding
import com.example.pymestock.models.ModelUbicacion

class UbicacionAdapter(
    var ubicacionesList: List<ModelUbicacion>,
    private val onViewClick: (ModelUbicacion, Int) -> Unit
) : RecyclerView.Adapter<UbicacionAdapter.UbicacionViewHolder>() {

    class UbicacionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemUbicacionBinding.bind(view)

        fun inicializar(ubicacion: ModelUbicacion, onViewClick: (ModelUbicacion, Int) -> Unit) {
            binding.itemTvUbicacionNombre.text = ubicacion.nombre
            itemView.setOnClickListener {
                onViewClick(ubicacion, layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UbicacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ubicacion, parent, false)
        return UbicacionViewHolder(view)
    }

    override fun getItemCount() = ubicacionesList.size

    override fun onBindViewHolder(holder: UbicacionViewHolder, position: Int) {
        holder.inicializar(ubicacionesList[position], onViewClick)
    }
}