package com.example.pymestock.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.pymestock.R
import com.example.pymestock.databinding.FragmentLoginBinding
import com.example.pymestock.viewmodel.LoginViewModel
import com.example.pymestock.utils.CustomToastUtil
import com.example.pymestock.PrincipalActivity
import com.example.pymestock.models.CurrentUser
import kotlin.math.log

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    // Usar el ViewModel del login
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Acción cuando el usuario hace clic en el botón de login
        binding.buttonLogin.setOnClickListener {
            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()

            // Validar campos de entrada
            if (username.isEmpty() || password.isEmpty()) {
                CustomToastUtil.showCustomToast(requireContext(), "Por favor, complete todos los campos")
                return@setOnClickListener
            }

            // Verificar si el usuario existe
            viewModel.login(username, password)

            // Observar el estado de la respuesta del login
            viewModel.loginResponse.observe(viewLifecycleOwner, Observer { response ->
                if (response.isSuccessful) {
                    // Usuario existe, proceder al siguiente fragmento
                    CustomToastUtil.showCustomToast(requireContext(), "¡Bienvenido!")

                    Log.d("respuesta", response.body().toString())


                    CurrentUser.setId(response.body()?.user?.id_usuario ?: -1) // Acceder a id_usuario dentro de user


                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProductsFragment())  // Redirige al fragmento de productos
                        .addToBackStack(null)
                        .commit()

                    // Llamar a handleBottomNavigationVisibility para actualizar la visibilidad del bottom nav
                    (activity as? PrincipalActivity)?.handleBottomNavigationVisibility()

                } else {
                    // Si la respuesta no fue exitosa, manejar el error
                    Log.d("LoginViewModel", "Error en la respuesta. Código: ${response.code()}, Mensaje: ${response.message()}")

                    // Mostrar el mensaje de error detallado del cuerpo de la respuesta
                    val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                    CustomToastUtil.showCustomToast(requireContext(), "Error: $errorMessage")
                }
            })
        }

        // Acción cuando el usuario hace clic en el botón de registro
        binding.buttonRegister.setOnClickListener {
            // Cambiar al RegisterFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegisterFragment()) // Reemplaza con el id de tu contenedor de fragmentos
                .addToBackStack(null) // Opcional: para poder volver atrás
                .commit()
        }
    }
}
