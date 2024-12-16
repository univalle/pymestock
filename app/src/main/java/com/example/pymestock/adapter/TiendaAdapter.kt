package com.example.pymestock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pymestock.R
import com.example.pymestock.databinding.ItemTiendaBinding
import com.example.pymestock.models.ModelTienda

class TiendaAdapter(
    var tiendasList: List<ModelTienda>,
    private val onViewClick: (ModelTienda, Int) -> Unit
) : RecyclerView.Adapter<TiendaAdapter.TiendaViewHolder>() {

    class TiendaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTiendaBinding.bind(view)

        fun inicializar(tienda: ModelTienda, onViewClick: (ModelTienda, Int) -> Unit) {
            binding.itemTvTiendaNombre.text = tienda.nombre
            itemView.setOnClickListener {
                onViewClick(tienda, layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TiendaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tienda, parent, false)
        return TiendaViewHolder(view)
    }

    override fun getItemCount() = tiendasList.size

    override fun onBindViewHolder(holder: TiendaViewHolder, position: Int) {
        holder.inicializar(tiendasList[position], onViewClick)
    }
}