package com.kevinbuenano.fidofriend.ui.mascota

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.HistorialLimpiezaEntity
import com.kevinbuenano.fidofriend.database.repository.historialLimpiezaRepository
import com.kevinbuenano.fidofriend.databinding.ActivityNuevaLimpiezaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class NuevaLimpiezaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNuevaLimpiezaBinding
    private var fechaLimpieza: LocalDate? = null
    private var  fechaLimpiezaString: String? = null
    private lateinit var repository: historialLimpiezaRepository
    private lateinit var db: appDatabase
    lateinit var historialLimpieza: HistorialLimpiezaEntity
    var idMascota: Int = 0
    private lateinit var tipoLimpiezaRes: String
    val tipoLimpieza = arrayOf("Ducha", "Corte de uñas", "Dientes", "Ojos", "Oídos", "Pelaje")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityNuevaLimpiezaBinding.inflate(layoutInflater).also { binding = it }.root
        )
        title = "Crear limpieza"

        val spinner = binding.spinnerParte
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoLimpieza)
        spinner.adapter = adapter

        idMascota = intent.getIntExtra("idMascota", 0)
        db = appDatabase.getDatabase(applicationContext)
        repository = historialLimpiezaRepository(db.historialLimpiezaDao())

        val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            // Convierte los valores de fecha seleccionados en un objeto LocalDate
            fechaLimpieza = LocalDate.of(year, month + 1, dayOfMonth)

            // Actualiza el texto del EditText con la fecha seleccionada
            val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            fechaLimpiezaString = fechaLimpieza?.format(formato)
            binding.editTextLimpieza.setText(fechaLimpieza?.format(formato))
        }
        binding.spinnerParte.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                tipoLimpiezaRes = tipoLimpieza[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Eliga un tipo de limpieaza", Toast.LENGTH_LONG).show()

            }
        }

            binding.editTextLimpieza.setOnClickListener {
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
        binding.btnAnyadirLimpieazaNueva.setOnClickListener {
            anyadirLimpieza()
        }

    }

    private fun anyadirLimpieza() {
        try {
            historialLimpieza = HistorialLimpiezaEntity(
                historialId = 0,
                tipoLimpiezaRes,
                fechaLimpiezaString!!,
                idMascota
            )
            lifecycleScope.launch(Dispatchers.IO) {
                repository.addHistorialLimpieza(historialLimpieza)
                finish()
            }
        }catch (e: Exception){
            Toast.makeText(this, "Los datos són incorrectos", Toast.LENGTH_SHORT).show()
        }
    }
}