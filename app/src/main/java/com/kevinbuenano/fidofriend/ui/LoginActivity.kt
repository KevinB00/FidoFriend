package com.kevinbuenano.fidofriend.ui

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity
import com.kevinbuenano.fidofriend.database.viewmodel.UsuarioViewModel
import com.kevinbuenano.fidofriend.databinding.ActivityLoginBinding
import com.kevinbuenano.fidofriend.ui.home.MenuActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var usuarioViewModel: UsuarioViewModel
    lateinit var nombre: String
    lateinit var contrasenya: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityLoginBinding.inflate(layoutInflater).also { binding = it }.root)
        val application: Application = application as Application
        usuarioViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[UsuarioViewModel::class.java]
        usuarioViewModel.sesionUsuarioLD.observe(this){usuario ->
            val usuario_nombre = usuario.nombre
            val intent = Intent(this, MenuActivity::class.java).putExtra("nombreUsuario", usuario_nombre)
            startActivity(intent)
            finish()
        }
       /* val factory = ViewModelProvider.AndroidViewModelFactory(application)*/
        /*usuarioViewModel = factory.create(UsuarioViewModel::class.java, CreationExtras.Empty)*/

        binding.btnRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        binding.btnAcceder.setOnClickListener{
            try {
                nombre = binding.eTextNombre.text.toString()
                contrasenya = binding.eTextContrasenya.text.toString()
                if (nombre.isEmpty() || contrasenya.isEmpty()){
                    Toast.makeText(this, "Rellene los datos!", Toast.LENGTH_LONG).show()
                }else {
                    usuarioViewModel.iniciarSesion(nombre, contrasenya)
                }
            }catch (e: Exception){
                Toast.makeText(this, "Compruebe los datos introducidos", Toast.LENGTH_LONG).show()
            }
        }

    }
}