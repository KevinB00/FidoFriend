package com.kevinbuenano.fidofriend.ui.mascota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.repository.mascotaRepository
import com.kevinbuenano.fidofriend.databinding.FragmentEliminarMascotaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass.
 * Use the [EliminarMascotaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EliminarMascotaFragment : Fragment() {
    private lateinit var binding: FragmentEliminarMascotaBinding
    private lateinit var db: appDatabase
    private lateinit var repository: mascotaRepository
    var idMascota: Int = 0
    private lateinit var mascotaEntity: MascotaEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEliminarMascotaBinding.inflate(inflater, container, false)
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
        binding.btnCancelarMascota.setOnClickListener {
            mascotaActivity.onBackPressed()
        }
        binding.btnBorrarMascota.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
               withContext(Dispatchers.IO) {
                    mascotaEntity = repository.getMascotaById(idMascota)
                    repository.removeMascota(mascotaEntity)
                }

                requireActivity().finish()
            }
        }
    }

}
