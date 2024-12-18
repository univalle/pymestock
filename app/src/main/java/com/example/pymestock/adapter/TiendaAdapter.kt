package com.example.pymestock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pymestock.R
import com.example.pymestock.models.Tienda

class TiendaAdapter(private val tiendaList: List<Tienda>) :
    RecyclerView.Adapter<TiendaAdapter.TiendaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TiendaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tienda, parent, false)
        return TiendaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TiendaViewHolder, position: Int) {
        val tienda = tiendaList[position]
        holder.nombreTienda.text = tienda.tienda
        holder.ubicacion.text = "Ubicación: ${tienda.ubicacion}"

        // Mostrar el total de productos
        val totalProductos = tienda.productos.size // Contamos los productos de la tienda
        holder.productos.text = "Total de productos: $totalProductos"
    }

    override fun getItemCount(): Int {
        return tiendaList.size
    }

    class TiendaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTienda: TextView = itemView.findViewById(R.id.itemZone)
        val ubicacion: TextView = itemView.findViewById(R.id.itemQuantity)
        val productos: TextView = itemView.findViewById(R.id.productos) // Este es el TextView que muestra el total de productos
        val selectItem: CheckBox = itemView.findViewById(R.id.selectItem)
        val arrow: ImageView = itemView.findViewById(R.id.arrow)
    }
}
