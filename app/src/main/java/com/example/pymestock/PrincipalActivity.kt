package com.example.pymestock

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pymestock.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity() {

    lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Cargar el fragmento de Login como fragmento inicial
        if (savedInstanceState == null) {
            replaceFragment(LoginFragment())  // Inicia con LoginFragment
        }

        // Configurar el listener para el BottomNavigationView
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_warehouses -> {
                    replaceFragment(ControlPanelFragment())  // Reemplazar con ControlPanelFragment
                    true
                }
                R.id.nav_products -> {
                    replaceFragment(ProductsFragment())  // Reemplazar con ProductsFragment
                    true
                }
                R.id.nav_movements -> {
                    replaceFragment(ControlPanelFragment())  // Reemplazar con ControlPanelFragment
                    true
                }
                else -> false
            }
        }

        // Escuchar cambios en el back stack de fragmentos
        supportFragmentManager.addOnBackStackChangedListener {
            handleBottomNavigationVisibility()
        }
    }

    // Método para reemplazar fragmentos
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)  // Agregar a la pila de retroceso para escuchar cambios
            .commit()

        handleBottomNavigationVisibility()
    }

    // Método para manejar la visibilidad del BottomNavigationView y el Toolbar
    private fun handleBottomNavigationVisibility() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        // Ocultar BottomNavigationView y Toolbar en LoginFragment y RegisterFragment
        if (currentFragment is LoginFragment || currentFragment is RegisterFragment) {
            binding.bottomNavigationView.visibility = View.GONE
            binding.toolbar.visibility = View.GONE



        } else {
            binding.bottomNavigationView.visibility = View.VISIBLE
            binding.toolbar.visibility = View.VISIBLE
        }
    }
}
