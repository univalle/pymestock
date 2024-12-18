package com.example.pymestock

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.example.pymestock.databinding.ActivityPrincipalBinding
import com.example.pymestock.models.CurrentUser
import com.example.pymestock.views.LoginFragment
import com.example.pymestock.utils.CustomToastUtil
import com.example.pymestock.views.AlertFragment
import com.example.pymestock.views.ControlPanelFragment
import com.example.pymestock.views.ProductsFragment
import com.example.pymestock.views.RegisterFragment
import com.example.pymestock.views.ScanFragment

class PrincipalActivity : AppCompatActivity() {

    lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleBottomNavigationVisibility()

        // Configurar el botón de hamburguesa para abrir el DrawerLayout
        binding.hamburgerButton.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        // Configurar listener para los elementos del NavigationView (drawer menu)
        binding.navigationView.setOnClickListener {
            CustomToastUtil.showCustomToast(this, "Elemento del menú del DrawerLayout")
        }

        binding.logoutButton.setOnClickListener {
            CustomToastUtil.showCustomToast(this, "Cerrar sesión")

            CurrentUser.setId(-1)  // Restablecer el ID del usuario actual

            // Reemplazar con LoginFragment
            replaceFragment(LoginFragment())
        }

        // Cargar el fragmento de Login como fragmento inicial
        if (savedInstanceState == null) {
            replaceFragment(LoginFragment())  // Inicia con LoginFragment
        }

        // Configurar el listener para el BottomNavigationView
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_warehouses -> {
                    replaceFragment(ScanFragment())  // Reemplazar con ScanFragment
                    CustomToastUtil.showCustomToast(this, "Escaneo de productos")
                    true
                }
                R.id.nav_products -> {
                    replaceFragment(ProductsFragment())  // Reemplazar con ProductsFragment
                    CustomToastUtil.showCustomToast(this, "Lista de productos")
                    true
                }
                R.id.nav_movements -> {
                    replaceFragment(ControlPanelFragment())  // Reemplazar con ControlPanelFragment
                    CustomToastUtil.showCustomToast(this, "Panel de control")
                    true
                }
                R.id.nav_alerts -> {
                    replaceFragment(AlertFragment())  // Reemplazar con AlertFragment
                    CustomToastUtil.showCustomToast(this, "Alertas")
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

        // Llamar a handleBottomNavigationVisibility en MainActivity
        if (fragment is BottomNavigationVisibilityListener) {
            (fragment as BottomNavigationVisibilityListener).handleBottomNavigationVisibility()
        }
    }

    fun handleBottomNavigationVisibility() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        // Ocultar BottomNavigationView y el header en LoginFragment y RegisterFragment
        if (currentFragment is LoginFragment || currentFragment is RegisterFragment) {
            binding.headerLayout.visibility = View.GONE
            binding.bottomNavigationView.visibility = View.GONE
            binding.fragmentContainer.updatePadding(top = 0)
        } else {
            binding.headerLayout.visibility = View.VISIBLE
            val padding = 160
            binding.fragmentContainer.updatePadding(top = padding)
            binding.bottomNavigationView.visibility = View.VISIBLE
        }
    }
}

// Interfaz para notificar a la actividad que se debe manejar la visibilidad
interface BottomNavigationVisibilityListener {
    fun handleBottomNavigationVisibility()
}
