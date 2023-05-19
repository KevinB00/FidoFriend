package com.kevinbuenano.fidofriend.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity
import com.kevinbuenano.fidofriend.database.repository.usuarioRepository
import com.kevinbuenano.fidofriend.databinding.FragmentPerfilBinding
import com.kevinbuenano.fidofriend.ui.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [PerfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PerfilFragment : Fragment() {
    lateinit var binding: FragmentPerfilBinding
    lateinit var usuarioRepository: usuarioRepository
    lateinit var db: appDatabase
    lateinit var usuarioEntity: UsuarioEntity
    lateinit var nombre: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usuario = activity?.getSharedPreferences("com.kevinbuenano.fidofriend", Context.MODE_PRIVATE)
        nombre = usuario?.getString("nombre", "").toString()
        val menuActivity = activity as? MenuActivity
        if (menuActivity != null) {
            db = menuActivity.getDB()
        }
        usuarioRepository = usuarioRepository(db.usuarioDao())

        obtenerUsuario()

        binding.btnNombre.setOnClickListener {
            try {
                var cambioNombre = binding.editNombre.text.toString()
                usuarioEntity.nombre = cambioNombre
                viewLifecycleOwner.lifecycleScope.launch {
                    val result = withContext(Dispatchers.IO){
                        usuarioRepository.updateUsuario(usuarioEntity)
                    }
                    if (result == null) {
                        Toast.makeText(
                            requireContext(),
                            "El dato introducido no es válido",
                            Toast.LENGTH_LONG
                        ).show()
                        obtenerUsuario()
                    }else{
                            val editor = usuario?.edit()
                            editor?.putString("nombre", usuarioEntity.nombre)
                        }
                }
            }catch (e: Exception){
                Toast.makeText(requireContext(), "El dato introducido no es válido", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnContrasenya.setOnClickListener {
            try {
                var cambioContrasenya = binding.editContrasenya.text.toString()
                usuarioEntity.contrasenya = cambioContrasenya
                viewLifecycleOwner.lifecycleScope.launch {
                    val result = withContext(Dispatchers.IO) {
                        usuarioRepository.updateUsuario(usuarioEntity)
                    }
                    if (result == null) {
                        Toast.makeText(
                            requireContext(),
                            "El dato introducido no es válido",
                            Toast.LENGTH_LONG
                        ).show()
                        obtenerUsuario()
                    }else{
                        val editor = usuario?.edit()
                        editor?.putString("contrasenya", usuarioEntity.contrasenya)
                    }
                }
            }catch (e: Exception){
                Toast.makeText(requireContext(), "El dato introducido no es válido", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnEmail.setOnClickListener {
            try {
                var cambioEmail = binding.editEmail.text.toString()
                usuarioEntity.email = cambioEmail
                resultado()
            }catch (e: Exception){
                Toast.makeText(requireContext(), "El dato introducido no es válido", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnLocalidad.setOnClickListener {
            try {
                var cambioLocalidad = binding.editLocalidad.text.toString()
                usuarioEntity.localidad = cambioLocalidad
                resultado()
            }catch (e: Exception){
                Toast.makeText(requireContext(), "El dato introducido no es válido", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnBorrarCuenta.setOnClickListener {
            try {
                viewLifecycleOwner.lifecycleScope.launch {
                    val result = withContext(Dispatchers.IO){
                        usuarioRepository.removeUsuario(usuarioEntity)
                    }
                }
            }catch (e: Exception) {
                Toast.makeText(requireContext(), "Error borrando tarea", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun obtenerUsuario() {
        if (nombre != null) {
        viewLifecycleOwner.lifecycleScope.launch {
            usuarioEntity = withContext(Dispatchers.IO){
                    usuarioRepository.getUsuarioByName(nombre)
                }
            }
        }
    }
    private fun resultado() {
        viewLifecycleOwner.lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                usuarioRepository.updateUsuario(usuarioEntity)
            }
            if (result == null) {
                Toast.makeText(
                    requireContext(),
                    "El dato introducido no es válido",
                    Toast.LENGTH_LONG
                ).show()
                obtenerUsuario()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

}

