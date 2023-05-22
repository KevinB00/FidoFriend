package com.kevinbuenano.fidofriend.ui.mascota

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.repository.mascotaRepository
import com.kevinbuenano.fidofriend.databinding.FragmentMejoraMascotaBinding


/**
 * A simple [Fragment] subclass.
 * Use the [MejoraFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MejoraFragment : Fragment() {
    private lateinit var binding: FragmentMejoraMascotaBinding
    private lateinit var db: appDatabase
    private lateinit var repository: mascotaRepository
    var idMascota: Int = 0
    val tipoMejora = arrayOf("Actividad", "Peso", "Tranquilizar", "Paseo", "Rutina")
    private lateinit var mejora: String
    lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMejoraMascotaBinding.inflate(inflater, container, false)
        val spinner = binding.spinnerTipoMejora
        val adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, tipoMejora)
        spinner.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mascotaActivity = activity as MascotaActivity
        if (mascotaActivity != null) {
            db = mascotaActivity.getDB()
            idMascota = mascotaActivity.getMascotaId()
        }
        repository = mascotaRepository(db.mascotaDao())

        binding.spinnerTipoMejora.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    mejora = tipoMejora[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(requireContext(), "Seleccione una mejora", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        binding.btnMejorar.setOnClickListener {
            cargarMejora()
        }
    }

    private fun cargarMejora() {
        if (binding.eTextComida.text.isNotEmpty() && binding.eTextNumPaseo.text.isNotEmpty()) {
            navController = findNavController()
            var numComida = binding.eTextComida.text.toString().toInt()
            var numPaseo = binding.eTextNumPaseo.text.toString().toInt()
            val action =
                MejoraFragmentDirections.actionMejoraMascotaFragmentToMostrarMejoraFragment(
                    numComida,
                    numPaseo,
                    mejora
                )
            navController.navigate(action)

        }
    }
}