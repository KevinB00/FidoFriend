package com.kevinbuenano.fidofriend.ui.mascota

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.repository.mascotaRepository
import com.kevinbuenano.fidofriend.databinding.FragmentModificarMascotaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.Period

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ModificarMascotaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ModificarMascotaFragment : Fragment() {
    private lateinit var binding: FragmentModificarMascotaBinding
    private var peso: Float = 0.0f
    private lateinit var db: appDatabase
    private lateinit var repository: mascotaRepository
    private lateinit var mascotaEntity: MascotaEntity
    private lateinit var estadoMascota: String
    private lateinit var actividadMascota: String
    private lateinit var tamanyoMascota: String
    private  var idMascota: Int = 0
    val estado = arrayOf("Sano", "Enfermo", "Sin revisión", "Obeso", "Cachorro")
    val actividad = arrayOf("Muy activo", "Sedentario", "Moderado")
    val tamanyo = arrayOf("Pequeño", "Mediano", "Grande")
    private val mascotaEntityLiveData = MutableLiveData<MascotaEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentModificarMascotaBinding.inflate(inflater, container, false)
        val spinnerEstado = binding.spinnerEstadoMod
        val adapterEstado = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, estado)
        spinnerEstado.adapter = adapterEstado
        val spinnerActividad = binding.spinnerActividadMod
        val adapterActiviadad = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, actividad)
        spinnerActividad.adapter = adapterActiviadad
        val spinnerTamanyo = binding.spinnerPorte
        val adapterTamanyo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tamanyo)
        spinnerTamanyo.adapter = adapterTamanyo
     return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mascotaActivity = activity as MascotaActivity
        db = mascotaActivity.getDB()
        idMascota = mascotaActivity.getMascotaId()
        repository = mascotaRepository(db.mascotaDao())
        cargarMascota(idMascota)

        mascotaEntityLiveData.observe(viewLifecycleOwner) { mascota ->
            mascotaEntity = mascota
        }

        binding.seekPeso.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val valor = progress - 13
                mascotaEntityLiveData.value?.let { mascota ->
                        peso = mascota.peso + valor
                    binding.tViewPesoMascota.text = peso.toString()
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
           }
        }
        )
        binding.spinnerEstadoMod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                estadoMascota = estado[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.spinnerActividadMod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                actividadMascota = actividad[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        binding.spinnerPorte.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tamanyoMascota = tamanyo[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.btnModificar.setOnClickListener {
            if (peso == 0.0f) {
                peso = mascotaEntity.peso
            }
                modificarMascota(mascotaActivity)

        }

    }

    override fun onResume() {
        super.onResume()
        cargarMascota(idMascota)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun modificarMascota(mascotaActivity: MascotaActivity) {
        try {
            if (mascotaEntity.edad > 4 && estadoMascota == "Cachorro") {
                Toast.makeText(
                    requireContext(),
                    "El cachorro no puede ser mayor de 4 años",
                    Toast.LENGTH_LONG
                ).show()
            }else if(estadoMascota == "Obeso") {
                    when (mascotaEntity.perroGato) {
                        1 -> {
                            when (tamanyoMascota) {
                                "Pequeño" -> {
                                    if (peso.toInt() <= 25) {
                                        Toast.makeText(
                                            requireContext(),
                                            "El perro no puede ser menor de 25 kilos si es obeso",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                                "Mediano" -> {
                                    if (peso.toInt() <= 27) {
                                        Toast.makeText(
                                            requireContext(),
                                            "El perro no puede ser menor de 27 kilos si es obeso",
                                            Toast.LENGTH_LONG
                                        ).show()

                                    }
                                }

                                "Grande" -> {
                                    if (peso.toInt() <= 40) {
                                        Toast.makeText(
                                            requireContext(),
                                            "El perro no puede ser menor de 40 kilos si es obeso",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        }

                        2 -> {
                            if (peso.toInt() <= 5.2f) {
                                Toast.makeText(
                                    requireContext(),
                                    "El gato no puede ser menor de 5 kilos y obeso",
                                    Toast.LENGTH_LONG
                                ).show()

                            }

                    }
                }

            }else {
                val fechaActual = LocalDate.now()
                val fecha = LocalDate.parse(mascotaEntity.fecha_nacimiento)
                val periodo = Period.between(fecha, fechaActual)
                mascotaEntity.edad = periodo.years
                var mascota = MascotaEntity(
                    mascotaEntity.id_mascota,
                    mascotaEntity.nombre,
                    mascotaEntity.fecha_nacimiento,
                    peso,
                    estadoMascota,
                    mascotaEntity.edad,
                    actividadMascota,
                    tamanyoMascota,
                    mascotaEntity.perroGato,
                    mascotaEntity.usuario_id
                )
                viewLifecycleOwner.lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        repository.updateMascota(mascota)
                    }
                    Toast.makeText(requireContext(), "Mascota modificada", Toast.LENGTH_LONG).show()
                    mascotaActivity.onBackPressed()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Valores no válidos", Toast.LENGTH_LONG).show()

        }
    }

    private fun cargarMascota(idMascota: Int) {
      viewLifecycleOwner.lifecycleScope.launch {
         mascotaEntity = withContext(Dispatchers.IO) {
             repository.getMascotaById(idMascota)
         }
          mascotaEntityLiveData.value = mascotaEntity
      }
    }

}