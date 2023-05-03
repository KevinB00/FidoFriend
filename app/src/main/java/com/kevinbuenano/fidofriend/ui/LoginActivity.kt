package com.kevinbuenano.fidofriend.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity
import com.kevinbuenano.fidofriend.database.viewmodel.UsuarioViewModel
import com.kevinbuenano.fidofriend.databinding.ActivityLoginBinding
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var usuarioViewModel: UsuarioViewModel
    private lateinit var usuarioEntity: UsuarioEntity
    lateinit var nombre: String
    lateinit var contrasenya: String
    var usuarios: MutableList<UsuarioEntity> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityLoginBinding.inflate(layoutInflater).also { binding = it }.root)

        usuarioViewModel = ViewModelProvider(this)[UsuarioViewModel::class.java]

        usuarioViewModel.cargarUsuarioLD.observe(this){
            usuarios.add(it)
        }
        binding.btnRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        binding.btnAcceder.setOnClickListener{
            try {
                nombre = binding.eTextNombre.text.toString()
                contrasenya = binding.eTextContrasenya.text.toString()
                if (nombre.isEmpty() || contrasenya.isEmpty()){
                    Toast.makeText(applicationContext, "Rellene los datos!", Toast.LENGTH_LONG)
                }else {
                    cargarUsuario()
                }
            }catch (e: Exception){
                Toast.makeText(applicationContext, "Compruebe los datos introducidos", Toast.LENGTH_LONG)
            }
        }

    }

    private fun cargarUsuario(){
        usuarioEntity = usuarioViewModel.cargarUsuario(nombre, contrasenya)
    }


}