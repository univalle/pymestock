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



}
