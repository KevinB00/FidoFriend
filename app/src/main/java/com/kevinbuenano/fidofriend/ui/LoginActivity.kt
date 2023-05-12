package com.kevinbuenano.fidofriend.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.repository.usuarioRepository
import com.kevinbuenano.fidofriend.databinding.ActivityLoginBinding
import com.kevinbuenano.fidofriend.ui.home.MenuActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var repository: usuarioRepository
    lateinit var nombre: String
    lateinit var contrasenya: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityLoginBinding.inflate(layoutInflater).also { binding = it }.root)
        val usuario = this.getSharedPreferences("com.kevinbuenano.fidofriend", Context.MODE_PRIVATE)
        val db = appDatabase.getDatabase(applicationContext)
        repository = usuarioRepository(db.usuarioDao())
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
                    lifecycleScope.launch(Dispatchers.IO) {
                        repository.iniciarSesion(nombre, contrasenya)
                        with (usuario.edit()){
                            putString("nombre", nombre)
                            putString("contrasenya", contrasenya)
                            apply()
                        }

                    }

                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            }catch (e: Exception){
                Toast.makeText(this, "Compruebe los datos introducidos", Toast.LENGTH_LONG).show()
            }
        }

    }
}