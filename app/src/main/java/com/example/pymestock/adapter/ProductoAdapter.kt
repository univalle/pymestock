package com.example.pymestock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pymestock.R
import com.example.pymestock.databinding.ItemProductoBinding
import com.example.pymestock.models.ModelProducto

class ProductoAdapter(
    var productosList: List<ModelProducto>,
    private val onViewClick: (ModelProducto, Int) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemProductoBinding.bind(view)

        fun inicializar(producto: ModelProducto, onViewClick: (ModelProducto, Int) -> Unit) {
            binding.itemTvProductoNombre.text = producto.nombre
            binding.itemTvProductoCantidad.text = "Cantidad: ${producto.cantidad}"
            itemView.setOnClickListener {
                onViewClick(producto, layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun getItemCount() = productosList.size

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.inicializar(productosList[position], onViewClick)
    }
}