package com.example.pymestock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pymestock.databinding.FragmentRegisterBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Acción cuando el usuario hace clic en buttonLogin
        binding.buttonLogin.setOnClickListener {
            // Cambiar de vuelta al LoginFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment()) // Reemplaza con el id de tu contenedor de fragmentos
                .addToBackStack(null) // Opcional: para poder volver atrás
                .commit()
        }

        binding.buttonRegister.setOnClickListener {
            // Cambiar de vuelta al LoginFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment()) // Reemplaza con el id de tu contenedor de fragmentos
                .addToBackStack(null) // Opcional: para poder volver atrás
                .commit()
        }
    }
}