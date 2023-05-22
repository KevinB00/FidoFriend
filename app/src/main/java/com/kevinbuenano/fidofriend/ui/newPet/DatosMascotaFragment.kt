package com.kevinbuenano.fidofriend.ui.newPet

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity
import com.kevinbuenano.fidofriend.database.repository.mascotaRepository
import com.kevinbuenano.fidofriend.database.repository.usuarioRepository
import com.kevinbuenano.fidofriend.databinding.FragmentDatosMascotaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Calendar


/**
 * A simple [Fragment] subclass.
 * Use the [DatosMascotaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DatosMascotaFragment : Fragment() {
    private lateinit var binding: FragmentDatosMascotaBinding
    private val args: DatosMascotaFragmentArgs by navArgs()
    lateinit var navController: NavController
    private var fechaNacimiento: LocalDate? = null
    private var tipoAnimal: Int = 0
    private var fechaNacimientoString: String? = null
    val opciones = arrayOf("Sano", "Enfermo", "Sin revisión", "Obeso", "Cachorro")
    val actividad = arrayOf("Muy activo", "Sedentario", "Moderado")
    val tamanyo = arrayOf("Pequeño", "Mediano", "Grande")
    var actividadMascota: String? = null
    private var estado: String? = null
    var tamanyoMascota: String? = null
    var edad:Int = 0
    private lateinit var db: appDatabase
    lateinit var usuarioEntity: UsuarioEntity
    lateinit var usuarioRepository: usuarioRepository
    lateinit var mascotaRepository: mascotaRepository
    lateinit var nombre: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDatosMascotaBinding.inflate(inflater, container, false)
        val spinner = binding.spinnerEstado
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, opciones)
        spinner.adapter = adapter

        val spinnerActividad = binding.spinnerActividad
        val adapterActividad = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, actividad)
        spinnerActividad.adapter = adapterActividad

        val spinnerTamanyo = binding.spinnerTamanyo
        val adapterTamanyo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, tamanyo)
        spinnerTamanyo.adapter = adapterTamanyo

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val usuario = activity?.getSharedPreferences("com.kevinbuenano.fidofriend", Context.MODE_PRIVATE)
        nombre = usuario?.getString("nombre", "").toString()
        db = appDatabase.getDatabase(requireContext().applicationContext)
        usuarioRepository = usuarioRepository(db.usuarioDao())
        mascotaRepository = mascotaRepository(db.mascotaDao())
        obtenerUsuario()

        navController = findNavController()
        tipoAnimal = args.tipoAnimal

        val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            // Convierte los valores de fecha seleccionados en un objeto LocalDate
            fechaNacimiento = LocalDate.of(year, month + 1, dayOfMonth)

            // Actualiza el texto del EditText con la fecha seleccionada
            val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            fechaNacimientoString = fechaNacimiento?.format(formato)
            binding.editTextFecha.setText(fechaNacimiento?.format(formato))

            // Calcula la edad a partir de la fecha de nacimiento seleccionada
            val fechaActual = LocalDate.now()
            val periodo = Period.between(fechaNacimiento, fechaActual)
            edad = periodo.years
        }
        binding.editTextFecha.setOnClickListener {
            // Obtiene la fecha actual
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            // Crea un nuevo DatePickerDialog con la fecha actual
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                datePickerListener,
                year,
                month,
                dayOfMonth
            )
            datePickerDialog.show()
        }
        binding.spinnerEstado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                estado = opciones[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireContext(), "Eliga un estado", Toast.LENGTH_LONG).show()
            }

        }
        binding.spinnerActividad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                actividadMascota = actividad[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireContext(), "Eliga la actividad", Toast.LENGTH_LONG).show()
            }

        }
        binding.spinnerTamanyo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ){
                tamanyoMascota = tamanyo[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireContext(), "Eliga el tamaño de su mascota", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnAnyadir.setOnClickListener {
            anyadirMascota()
        }
        binding.btnCancelar.setOnClickListener {
            requireActivity().finish()
        }

    }

    private fun anyadirMascota() {
        try {
            if (fechaNacimientoString != null || binding.edTextPeso.text.isNotEmpty() || estado != null || actividadMascota != null || tamanyoMascota != null) {
                if (edad > 4 && estado == "Cachorro"){
                    Toast.makeText(requireContext(), "El cachorro no puede ser mayor de 4 años", Toast.LENGTH_LONG).show()
                }else if(estado == "Obeso") {
                    when (tipoAnimal) {
                        1 -> {
                            when (tamanyoMascota) {
                                "Pequeño" -> {
                                    if (binding.edTextPeso.text.toString().toInt() <= 25) {
                                        Toast.makeText(
                                            requireContext(),
                                            "El perro no puede ser menor de 25 kilos si es obeso",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                                "Mediano" -> {
                                    if (binding.edTextPeso.text.toString().toInt() <= 27) {
                                        Toast.makeText(
                                            requireContext(),
                                            "El perro no puede ser menor de 27 kilos si es obeso",
                                            Toast.LENGTH_LONG
                                        ).show()

                                    }
                                }

                                "Grande" -> {
                                    if (binding.edTextPeso.text.toString().toInt() <= 40) {
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
                            if (binding.edTextPeso.text.toString().toFloat() <= 5.2f) {
                                Toast.makeText(
                                    requireContext(),
                                    "El gato no puede ser menor de 5 kilos y obeso",
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        }
                    }
                }else{
                    val mascota = MascotaEntity(
                        id_mascota = 0,
                        binding.edTextNombre.text.toString(),
                        fechaNacimientoString!!,
                        binding.edTextPeso.text.toString().toFloatOrNull()!!,
                        estado!!,
                        edad,
                        actividadMascota!!,
                        tamanyoMascota!!,
                        tipoAnimal,
                        usuarioEntity.id
                    )
                    viewLifecycleOwner.lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            mascotaRepository.addMascota(mascota)
                        }
                        requireActivity().finish()
                    }
                }
            }else{
                Toast.makeText(requireContext(), "Rellene los campos para añadir", Toast.LENGTH_LONG).show()

            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error al añadir, compruebe los datos", Toast.LENGTH_LONG).show()
        }
    }

    private fun obtenerUsuario() {
        viewLifecycleOwner.lifecycleScope.launch {
            usuarioEntity = withContext(Dispatchers.IO){
                usuarioRepository.getUsuarioByName(nombre)
            }
        }
    }

}

