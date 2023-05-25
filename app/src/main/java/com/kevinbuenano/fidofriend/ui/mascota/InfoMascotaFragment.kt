package com.kevinbuenano.fidofriend.ui.mascota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kevinbuenano.fidofriend.R
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.repository.mascotaRepository
import com.kevinbuenano.fidofriend.databinding.FragmentInfoMascotaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [InfoMascotaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoMascotaFragment : Fragment() {
    private lateinit var binding: FragmentInfoMascotaBinding
    private lateinit var db: appDatabase
    private lateinit var repository: mascotaRepository
    var idMascota: Int = 0
    var diferencia: Float = 0.0f
    private lateinit var mascotaEntity: MascotaEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInfoMascotaBinding.inflate(inflater, container, false)
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
        cargar()
    }

    override fun onResume() {
        super.onResume()
        recargar()
    }

    private fun recargar() {
        viewLifecycleOwner.lifecycleScope.launch{
            mascotaEntity = withContext(Dispatchers.IO){
                repository.getMascotaById(idMascota)
            }
            binding.tViewNombreMascota.text = mascotaEntity.nombre
            binding.tViewNumeroPeso.text = "${mascotaEntity.peso} Kg"
            diferencia = mascotaEntity.peso - binding.tViewDifNum.text.toString().toFloat()
            binding.tViewDifNum.text = diferencia.toString()
            binding.tViewEstadoRes.text = mascotaEntity.estado
            binding.tViewEdadNum.text = "${mascotaEntity.edad}"
            cargarActividad()

        }
    }

    private fun cargarActividad() {
        when (mascotaEntity.actividad) {
            "Muy activo" -> binding.imageView.setImageResource(R.drawable.cara_feliz)
            "Sedentario" -> binding.imageView.setImageResource(R.drawable.cara_triste)
            "Moderado" -> binding.imageView.setImageResource(R.drawable.cara_seria)
        }
    }

    private fun cargar() {
        viewLifecycleOwner.lifecycleScope.launch{
            mascotaEntity = withContext(Dispatchers.IO){
                 repository.getMascotaById(idMascota)
            }
            binding.tViewNombreMascota.text = mascotaEntity.nombre
            binding.tViewNumeroPeso.text = "${mascotaEntity.peso} Kg"
            binding.tViewDifNum.text = diferencia.toString()
            binding.tViewEstadoRes.text = mascotaEntity.estado
            binding.tViewEdadNum.text = "${mascotaEntity.edad}"
            cargarActividad()

        }
    }
}