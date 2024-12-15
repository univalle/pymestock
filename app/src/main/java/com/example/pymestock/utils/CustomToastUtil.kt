package com.example.pymestock.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.example.pymestock.databinding.CustomToastBinding

object CustomToastUtil {

    fun showCustomToast(context: Context, message: String) {
        val inflater = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as android.view.LayoutInflater)
        val binding = CustomToastBinding.inflate(inflater)

        // Establecer el texto del mensaje en el Toast
        binding.toastText.text = message

        // Crear y mostrar el Toast personalizado
        val toast = Toast(context).apply {
            duration = Toast.LENGTH_SHORT
            view = binding.root
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100)
        }
        toast.show()
    }
}