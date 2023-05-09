package com.kevinbuenano.fidofriend.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
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
    private var usuarioViewModel: UsuarioViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            usuarioViewModel = ViewModelProvider(it)[UsuarioViewModel::class.java]
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usuarioViewModel.updateUsuarioLD.observe(viewLifecycleOwner) {
            if (it == null) {
                Toast.makeText(
                    requireContext(),
                    "El dato introducido no es válido",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                usuarioViewModel.usuario = it
            }
        }

        binding.btnNombre.setOnClickListener {
            try {
                var cambioNombre = binding.editNombre.text.toString()
                usuarioViewModel.usuario.nombre = cambioNombre
                usuarioViewModel.updateUsuario(usuarioViewModel.usuario)
            }catch (e: Exception){
                Toast.makeText(requireContext(), "El dato introducido no es válido", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnContrasenya.setOnClickListener {
            try {
                var cambioContrasenya = binding.editContrasenya.text.toString()
                usuarioViewModel.usuario.contrasenya = cambioContrasenya

                usuarioViewModel.updateUsuario(usuarioViewModel.usuario)
            }catch (e: Exception){
                Toast.makeText(requireContext(), "El dato introducido no es válido", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnEmail.setOnClickListener {
            try {
                var cambioEmail = binding.editEmail.text.toString()
                usuarioViewModel.usuario.contrasenya = cambioEmail

                usuarioViewModel.updateUsuario(usuarioViewModel.usuario)
            }catch (e: Exception){
                Toast.makeText(requireContext(), "El dato introducido no es válido", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnLocalidad.setOnClickListener {
            try {
                var cambioLocalidad = binding.editLocalidad.text.toString()
                usuarioViewModel.usuario.contrasenya = cambioLocalidad

                usuarioViewModel.updateUsuario(usuarioViewModel.usuario)
            }catch (e: Exception){
                Toast.makeText(requireContext(), "El dato introducido no es válido", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnBorrarCuenta.setOnClickListener {
            try {
                usuarioViewModel.deleteUsuario(usuarioViewModel.usuario)
            }catch (e: Exception) {
                Toast.makeText(requireContext(), "Pruebe más tarde", Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onResume() {
        super.onResume()
        var nombreUsuario = usuarioViewModel.usuario.nombre
        requireActivity().title = "$nombreUsuario"
    }

}