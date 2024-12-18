package com.example.pymestock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pymestock.R
import com.example.pymestock.models.Producto

class ProductoAdapter(private val productoList: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product, parent, false) // Utiliza el layout de cada producto
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productoList[position]
        holder.itemName.text = producto.nombre // Asegúrate de tener una propiedad "nombre" en Producto
    }

    override fun getItemCount(): Int {
        return productoList.size
    }

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName) // Referencia al TextView del nombre del producto
    }
}