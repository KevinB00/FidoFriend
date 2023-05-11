package com.kevinbuenano.fidofriend.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.kevinbuenano.fidofriend.R
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity
import com.kevinbuenano.fidofriend.database.viewmodel.MascotaViewModel
import com.kevinbuenano.fidofriend.database.viewmodel.UsuarioViewModel
import com.kevinbuenano.fidofriend.databinding.FragmentPerfilBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [PerfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PerfilFragment : Fragment() {
    lateinit var binding: FragmentPerfilBinding
    lateinit var usuarioViewModel: UsuarioViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usuarioViewModel = (requireActivity() as MenuActivity).getUsuarioViewModel()
        usuarioViewModel.updateUsuarioLD.observe(viewLifecycleOwner) {
            if (it == null) {
                Toast.makeText(
                    requireContext(),
                    "El dato introducido no es válido",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                usuarioViewModel.sesionUsuarioLD.value = it
            }
        }

        binding.btnNombre.setOnClickListener {
            try {
                var cambioNombre = binding.editNombre.text.toString()
                usuarioViewModel.sesionUsuarioLD.value?.nombre = cambioNombre
                usuarioViewModel.sesionUsuarioLD.value?.let { it1 ->
                    usuarioViewModel.updateUsuario(
                        it1
                    )
                }
            }catch (e: Exception){
                Toast.makeText(requireContext(), "El dato introducido no es válido", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnContrasenya.setOnClickListener {
            try {
                var cambioContrasenya = binding.editContrasenya.text.toString()
                usuarioViewModel.sesionUsuarioLD.value?.contrasenya = cambioContrasenya

                usuarioViewModel.sesionUsuarioLD.value?.let { it1 ->
                    usuarioViewModel.updateUsuario(
                        it1
                    )
                }
            }catch (e: Exception){
                Toast.makeText(requireContext(), "El dato introducido no es válido", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnEmail.setOnClickListener {
            try {
                var cambioEmail = binding.editEmail.text.toString()
                usuarioViewModel.sesionUsuarioLD.value?.email = cambioEmail

                usuarioViewModel.sesionUsuarioLD.value?.let { it1 ->
                    usuarioViewModel.updateUsuario(
                        it1
                    )
                }
            }catch (e: Exception){
                Toast.makeText(requireContext(), "El dato introducido no es válido", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnLocalidad.setOnClickListener {
            try {
                var cambioLocalidad = binding.editLocalidad.text.toString()
                usuarioViewModel.sesionUsuarioLD.value?.localidad = cambioLocalidad

                usuarioViewModel.sesionUsuarioLD.value?.let { it1 ->
                    usuarioViewModel.updateUsuario(
                        it1
                    )
                }
            }catch (e: Exception){
                Toast.makeText(requireContext(), "El dato introducido no es válido", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnBorrarCuenta.setOnClickListener {
            try {
                usuarioViewModel.sesionUsuarioLD.value?.let { it1 ->
                    usuarioViewModel.deleteUsuario(
                        it1
                    )
                }
            }catch (e: Exception) {
                Toast.makeText(requireContext(), "Pruebe más tarde", Toast.LENGTH_LONG).show()

            }
        }
    }

}