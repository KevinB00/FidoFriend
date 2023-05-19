package com.kevinbuenano.fidofriend.ui.mascota.nuevaVisita

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.HistorialMedicoEntity
import com.kevinbuenano.fidofriend.database.repository.historialMedicoRepository
import com.kevinbuenano.fidofriend.databinding.ActivityNuevaVisitaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class NuevaVisitaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNuevaVisitaBinding
    private var fechaVisita: LocalDate? = null
    private var fechaVisitaString: String? = null
    private lateinit var repository: historialMedicoRepository
    private lateinit var db: appDatabase
    lateinit var historialVeterinario: HistorialMedicoEntity
    var idMascota: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityNuevaVisitaBinding.inflate(layoutInflater).also { binding = it }.root)
        title = "Crear visita"
        idMascota = intent.getIntExtra("idMascota", 0)
        db = appDatabase.getDatabase(applicationContext)
        repository = historialMedicoRepository(db.historialMedicoDao())
        val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            // Convierte los valores de fecha seleccionados en un objeto LocalDate
            fechaVisita = LocalDate.of(year, month + 1, dayOfMonth)

            // Actualiza el texto del EditText con la fecha seleccionada
            val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            fechaVisitaString = fechaVisita?.format(formato)
            binding.eTextFechaVisita.setText(fechaVisita?.format(formato))
        }

        binding.eTextFechaVisita.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                datePickerListener,
                year,
                month,
                dayOfMonth
            )
            datePickerDialog.show()
        }
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
            lifecycleScope.launch(Dispatchers.IO){
                repository.addHistorialMedico(historialVeterinario)
                finish()

            }

        }catch (e: Exception){
            Toast.makeText(this, "Los datos són incorrectos", Toast.LENGTH_SHORT).show()
        }
    }
}