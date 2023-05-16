package com.kevinbuenano.fidofriend.ui.mascota

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.repository.mascotaRepository
import com.kevinbuenano.fidofriend.databinding.FragmentModificarMascotaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    val estado = arrayOf("Sano", "Enfermo", "Sin revisión", "Obeso", "Cachorro")
    val actividad = arrayOf("Muy activo", "Sedentario", "Moderado")
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
     return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mascotaActivity = activity as MascotaActivity
        db = mascotaActivity.getDB()
        var idMascota = mascotaActivity.getMascotaId()
        repository = mascotaRepository(db.mascotaDao())
        cargarMascota(idMascota)

        mascotaEntityLiveData.observe(viewLifecycleOwner) { mascota ->
            mascotaEntity = mascota
            binding.seekPeso.progress = mascota.peso.toInt()
        }

        binding.seekPeso.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                peso = progress.toFloat()
                binding.tViewPesoMascota.text = peso.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
           }
        }
        )


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