package com.example.pymestock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pymestock.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity() {

    lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar el binding
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Cargar el LoginFragment como fragmento por defecto
        if (savedInstanceState == null) {
            replaceFragment(LoginFragment())  // Inicia con el fragmento de Login
        }
    }

    // Método para reemplazar fragmentos
    private fun replaceFragment(fragment: Fragment) {
        // Realizamos el reemplazo del fragmento
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)  // R.id.fragment_container debe coincidir con el ID del contenedor en activity_principal.xml
            .commit()
    }

}
