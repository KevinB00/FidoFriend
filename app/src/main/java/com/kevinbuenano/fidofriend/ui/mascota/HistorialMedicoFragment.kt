package com.kevinbuenano.fidofriend.ui.mascota

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kevinbuenano.fidofriend.adapters.HistorialMedicoAdapter
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.HistorialMedicoEntity
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.repository.historialMedicoRepository
import com.kevinbuenano.fidofriend.databinding.FragmentHistorialMedicoBinding
import com.kevinbuenano.fidofriend.ui.mascota.nuevaVisita.NuevaVisitaActivity
import kotlinx.coroutines.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [HistorialMedicoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistorialMedicoFragment : Fragment() {
    lateinit var binding: FragmentHistorialMedicoBinding
    lateinit var recyclerView: RecyclerView
    var historial: MutableList<HistorialMedicoEntity> = mutableListOf()
    lateinit var adapter: HistorialMedicoAdapter
    private lateinit var historialMedicoRepository: historialMedicoRepository
    private lateinit var db: appDatabase
    var idMascota: Int = 0
    private lateinit var mascotaEntity: MascotaEntity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistorialMedicoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mascotaActivity = activity as? MascotaActivity
        if (mascotaActivity != null) {
            db = mascotaActivity.getDB()
            idMascota = mascotaActivity.getMascotaId()
        }
        historialMedicoRepository = historialMedicoRepository(db.historialMedicoDao())
        cargar()

        binding.btnAnyadirVisita.setOnClickListener {
            val intent = Intent(activity, NuevaVisitaActivity::class.java).putExtra("idMascota", idMascota)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        cargar()
    }

    private fun cargar() {
        viewLifecycleOwner.lifecycleScope.launch {
             historial = withContext(Dispatchers.IO) {
                 historialMedicoRepository.getHistorialMedicoById(idMascota)
            }
            setUpRecyclerViewHistorialMedico()
        }
    }

    private fun setUpRecyclerViewHistorialMedico() {
         if(historial.isNotEmpty()){
             adapter = HistorialMedicoAdapter(historial)
             recyclerView = binding.medicoRecycler
             recyclerView.setHasFixedSize(true)
             recyclerView.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
             recyclerView.adapter = adapter
         }
    }

}