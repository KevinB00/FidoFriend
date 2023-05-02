package com.kevinbuenano.fidofriend.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity
import com.kevinbuenano.fidofriend.database.viewmodel.UsuarioViewModel
import com.kevinbuenano.fidofriend.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private lateinit var usuarioViewModel: UsuarioViewModel
    private lateinit var usuarioNuevo: UsuarioEntity
    var usuarios: MutableList<UsuarioEntity> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityRegistroBinding.inflate(layoutInflater).also { binding = it }.root)

        usuarioViewModel = ViewModelProvider(this)[UsuarioViewModel::class.java]

        usuarioViewModel.insertUsuarioLD.observe(this) {
            usuarios.add(it)
        }
        binding.btnRegistrar.setOnClickListener {

            usuarioNuevo = UsuarioEntity(
                id = 0,
                binding.eTextNombre.text.toString(),
                binding.eTextContrasenya.text.toString(),
                binding.eTextEmail.text.toString(),
                binding.eTextLocalidad.text.toString()
            )
            if (validarInput(usuarioNuevo)){
                addUsuario()
                finish()
            }else{
                Toast.makeText(applicationContext, "Introduzca los datos!", Toast.LENGTH_LONG).show()
            }
        }
    }

    //Registrar Usuario
    private fun addUsuario() {
        usuarioViewModel.add(usuarioNuevo)
        Toast.makeText(applicationContext, "Usuario registrado!", Toast.LENGTH_SHORT).show()

    }

    private fun validarInput(usuarioNuevo: UsuarioEntity): Boolean {
        if (usuarioNuevo.nombre.isEmpty() ||
            usuarioNuevo.contrasenya.isEmpty() ||
            usuarioNuevo.nombre.isEmpty()
        ) {
            return false
        }
        return true
    }
}