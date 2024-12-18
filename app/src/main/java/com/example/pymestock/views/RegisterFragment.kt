package com.example.pymestock.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.pymestock.R
import com.example.pymestock.databinding.FragmentRegisterBinding
import com.example.pymestock.viewmodel.RegisterViewModel
import com.example.pymestock.utils.CustomToastUtil

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding

    // Usar el ViewModel del registro
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Acción para ir al LoginFragment
        binding.buttonLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment()) // Cambiar al LoginFragment
                .addToBackStack(null)
                .commit()
        }

        // Acción para registrar usuario
        binding.buttonRegister.setOnClickListener {
            val nombre = binding.inputUsername.text.toString()
            val correo = binding.inputEmail.text.toString()
            val contraseña = binding.inputPassword.text.toString()
            val confirmPassword = binding.inputPassword2.text.toString()

            // Validar los campos
            if (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || confirmPassword.isEmpty()) {
                CustomToastUtil.showCustomToast(requireContext(), "Por favor, complete todos los campos")
                return@setOnClickListener
            }

            if (contraseña != confirmPassword) {
                CustomToastUtil.showCustomToast(requireContext(), "Las contraseñas no coinciden")
                return@setOnClickListener
            }

            // Llamar al ViewModel para registrar el usuario
            viewModel.register(nombre, correo, contraseña, "usuario") // El rol por defecto es "usuario"

            // Observar la respuesta del registro
            viewModel.registerResponse.observe(viewLifecycleOwner, Observer { response ->
                if (response.success) {
                    CustomToastUtil.showCustomToast(requireContext(), "¡Registro exitoso!")
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, LoginFragment()) // Redirigir al LoginFragment
                        .addToBackStack(null)
                        .commit()
                } else {
                    CustomToastUtil.showCustomToast(requireContext(), "${response.error}")
                }
            })
        }
    }
}
