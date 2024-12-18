package com.example.pymestock.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.pymestock.databinding.FragmentScanBinding
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.pymestock.R
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanFragment : Fragment() {

    private lateinit var previewView: PreviewView
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_scan, container, false)

        previewView = binding.findViewById(R.id.previewView)

        // Iniciar la cámara cuando el fragmento esté listo
        cameraExecutor = Executors.newSingleThreadExecutor()

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            // Solicitar permiso si no está otorgado
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                0
            )
        }

        return binding
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            // Usamos la cámara cuando esté disponible
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Configurar el selector para la cámara trasera
            val cameraSelector = CameraSelector.Builder()
                .build()

            // Configurar la vista previa
            val preview = Preview.Builder().build()

            // Establecer la vista de la cámara en el PreviewView
            preview.setSurfaceProvider(previewView.surfaceProvider)

            try {
                // Un vinculo de ciclo de vida para asociar la cámara con la vista
                cameraProvider.bindToLifecycle(
                    this as LifecycleOwner,
                    cameraSelector,
                    preview
                )
            } catch (e: Exception) {
                Log.e("ScanFragment", "Error al iniciar la cámara: ${e.message}")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onDestroy() {
        super.onDestroy()
        // Liberar el ejecutor cuando ya no se necesite
        cameraExecutor.shutdown()
    }
}