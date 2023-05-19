package com.kevinbuenano.fidofriend.ui.mascota.nuevaVisita

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kevinbuenano.fidofriend.R
import com.kevinbuenano.fidofriend.database.entities.HistorialMedicoEntity
import com.kevinbuenano.fidofriend.databinding.ActivityNuevaVisitaBinding
import java.time.LocalDate

class NuevaVisitaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNuevaVisitaBinding
    private var fechaVisita: LocalDate? = null
    private var fechaVisitaString: String? = null
    lateinit var historialVeterinario: HistorialMedicoEntity
    var idMascota: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_visita)
        title = "Crear visita"
        idMascota = intent.getIntExtra("idMascota", 0)


    binding.btnAnyadirVisitaNueva.setOnClickListener{
        anyadir()
    }

    }

    private fun anyadir() {
        try {
            historialVeterinario = HistorialMedicoEntity(
                historialId = 0,
                binding.editTextTituloVisita.text.toString(),
                fechaVisitaString!!,
                binding.eTextResumenInput.text.toString(),
                idMascota
            )
        }catch (e: Exception){
            Toast.makeText(this, "Los datos són incorrectos", Toast.LENGTH_SHORT).show()
        }
    }
}