package com.example.pymestock

import android.os.Bundle
import android.service.controls.Control
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pymestock.databinding.CustomToastBinding
import com.example.pymestock.databinding.FragmentLoginBinding
import com.example.pymestock.utils.CustomToastUtil


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Acción cuando el usuario hace clic en buttonRegister
        binding.buttonRegister.setOnClickListener {
            // Cambiar al RegisterFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegisterFragment()) // Reemplaza con el id de tu contenedor de fragmentos
                .addToBackStack(null) // Opcional: para poder volver atrás
                .commit()
        }

        binding.buttonLogin.setOnClickListener {
            // Cambiar al RegisterFragment
            CustomToastUtil.showCustomToast(requireContext(), "Iniciar sesión")

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ControlPanelFragment()) // Reemplaza con el id de tu contenedor de fragmentos
                .addToBackStack(null) // Opcional: para poder volver atrás
                .commit()
        }
    }
}