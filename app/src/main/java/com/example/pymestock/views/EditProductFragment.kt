package com.example.pymestock.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pymestock.R
import com.example.pymestock.api.ApiService
import com.example.pymestock.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProductFragment : Fragment() {

    private var nombreTienda: String? = null
    private var ubicacion: String? = null

    private val apiService = RetrofitInstance.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("EditProductFragment", arguments.toString())

        // Recuperar los datos del bundle
        arguments?.let {
            nombreTienda = it.getString("nombre_tienda")
        }

        // Log para verificar los datos recibidos
        Log.d("EditProductFragment", "Nombre Tienda: $nombreTienda")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        return inflater.inflate(R.layout.fragment_edit_product, container, false)
    }

    // Función para manejar el clic en el botón Save
    private fun setupSaveButton() {
        // Buscar el botón de guardar en la vista
        val saveButton = view?.findViewById<View>(R.id.save)

        // Configurar el clic en el botón de guardar
        saveButton?.setOnClickListener {
            // Obtener el nuevo nombre de la tienda desde el EditText
            val newNameEditText: EditText = view?.findViewById(R.id.name) ?: return@setOnClickListener
            val nuevoNombre = newNameEditText.text.toString()

            if (nuevoNombre.isNotEmpty()) {
                // Llamar a la API para actualizar el nombre de la tienda
                actualizarTienda(nombreTienda, nuevoNombre)
            } else {
                Toast.makeText(context, "El nuevo nombre de la tienda no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para actualizar el nombre de la tienda usando Retrofit
    private fun actualizarTienda(nombreTienda: String?, nuevoNombre: String) {
        // Verificar si el nombre de la tienda original es nulo
        if (nombreTienda == null) {
            Log.e("EditProductFragment", "El nombre de la tienda original es nulo.")
            return
        }



        // Enviar la solicitud para actualizar el nombre de la tienda
        apiService.actualizarTienda(nombreTienda, mapOf("nuevo_nombre" to nuevoNombre))
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("EditProductFragment", "Tienda actualizada correctamente.")
                        // Retroceder al fragmento anterior después de la actualización exitosa
                        activity?.onBackPressed()
                    } else {
                        Log.e("EditProductFragment", "Error al actualizar la tienda: ${response.errorBody()?.string()}")
                        Toast.makeText(context, "Error al actualizar la tienda", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("EditProductFragment", "Fallo al llamar a la API: ${t.message}")
                    Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Asignar los valores a los EditText
        val nameEditText: EditText = view.findViewById(R.id.name)

        // Asignar los valores del Bundle a los EditText
        nameEditText.setText(nombreTienda)

        // Configurar el botón de guardar
        setupSaveButton()
    }
}
