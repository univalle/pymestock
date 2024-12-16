package com.example.pymestock.views

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pymestock.R
import com.example.pymestock.databinding.FragmentControlPanelBinding
import com.example.pymestock.utils.CustomToastUtil

class ControlPanelFragment : Fragment() {

    private lateinit var binding: FragmentControlPanelBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        binding = FragmentControlPanelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Deshabilitar el título por defecto del ActionBar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)  // Eliminar título por defecto

        // Configurar el menú en el Toolbar
        setHasOptionsMenu(true)  // Habilita la opción de menú en este fragmento




        // Configurar el BottomNavigationView con los Toast
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_products -> {
                    CustomToastUtil.showCustomToast(requireContext(), "Productos seleccionados")
                    true
                }
                R.id.nav_warehouses -> {
                    CustomToastUtil.showCustomToast(requireContext(), "Almacenes seleccionados")
                    true
                }
                R.id.nav_movements -> {
                    CustomToastUtil.showCustomToast(requireContext(), "Movimientos seleccionados")
                    true
                }
                R.id.nav_alerts -> {
                    CustomToastUtil.showCustomToast(requireContext(), "Alertas seleccionadas")
                    true
                }
                else -> false
            }
        }
    }

    // Inflar el menú del toolbar cuando se crea el menú de opciones
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.drawer_menu, menu)  // Inflar el menú drawer_menu.xml en el toolbar
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Manejar clics en los ítems del menú del toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                CustomToastUtil.showCustomToast(requireContext(), "Home seleccionado")
                return true
            }
            R.id.nav_profile -> {
                CustomToastUtil.showCustomToast(requireContext(), "Perfil seleccionado")
                return true
            }
            R.id.nav_settings -> {
                CustomToastUtil.showCustomToast(requireContext(), "Configuraciones seleccionadas")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
