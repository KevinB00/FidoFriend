package com.kevinbuenano.fidofriend.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity
import com.kevinbuenano.fidofriend.database.repository.usuarioRepository
import com.kevinbuenano.fidofriend.databinding.ActivityRegistroBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private lateinit var usuarioNuevo: UsuarioEntity
    private lateinit var bd: appDatabase
    private lateinit var repository: usuarioRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityRegistroBinding.inflate(layoutInflater).also { binding = it }.root)

        bd = appDatabase.getDatabase(applicationContext)
        repository = usuarioRepository(bd.usuarioDao())
        //Cuando se pulse el botón se realizarán los pasos necesarios para añadir el usuario a la base de datos.
        binding.btnRegistrar.setOnClickListener {
            try {
                usuarioNuevo = UsuarioEntity(
                    id = 0,
                    binding.eTextNombre.text.toString(),
                    binding.eTextContrasenya.text.toString(),
                    binding.eTextEmail.text.toString(),
                    binding.eTextLocalidad.text.toString()
                )
                if (validarInput(usuarioNuevo)) {
                    addUsuario()
                    finish()
                } else {
                    Toast.makeText(this, "Introduzca los datos!", Toast.LENGTH_LONG)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    "Los datos ya están registrados",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    //Registrar Usuario
    private fun addUsuario() {
        lifecycleScope.launch(Dispatchers.IO) {
            repository.addUsuario(usuarioNuevo)
        }
        Toast.makeText(this, "Usuario registrado!", Toast.LENGTH_SHORT).show()

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