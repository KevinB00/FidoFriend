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
import com.kevinbuenano.fidofriend.adapters.HistorialLimpiezaAdapter
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.HistorialLimpiezaEntity
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.repository.historialLimpiezaRepository
import com.kevinbuenano.fidofriend.databinding.FragmentHistorialLimpiezaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [HistorialLimpiezaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistorialLimpiezaFragment : Fragment() {
    lateinit var binding: FragmentHistorialLimpiezaBinding
    lateinit var recyclerView: RecyclerView
    var historial: MutableList<HistorialLimpiezaEntity> = mutableListOf()
    lateinit var adapter: HistorialLimpiezaAdapter
    private lateinit var historialLimpiezaRepository: historialLimpiezaRepository
    private lateinit var db: appDatabase
    var idMascota: Int = 0
    private lateinit var mascotaEntity: MascotaEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistorialLimpiezaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mascotaActivity = activity as? MascotaActivity
        if (mascotaActivity != null) {
            db = mascotaActivity.getDB()
            idMascota = mascotaActivity.getMascotaId()
        }
        historialLimpiezaRepository = historialLimpiezaRepository(db.historialLimpiezaDao())
        cargar()

        binding.btnAnyadirLimpieza.setOnClickListener {
            val intent = Intent(activity, NuevaLimpiezaActivity::class.java).putExtra("idMascota", idMascota)
        }

    }

    override fun onResume() {
        super.onResume()
        cargar()
    }

    private fun cargar() {
        viewLifecycleOwner.lifecycleScope.launch {
            historial = withContext(Dispatchers.IO) {
                historialLimpiezaRepository.getHistorialLimpiezaById(idMascota)
            }
            setUpRecyclerViewHistorialLimpieza()
        }
    }

    private fun setUpRecyclerViewHistorialLimpieza() {
        if (historial.isNotEmpty()){
            adapter = HistorialLimpiezaAdapter(historial)
            recyclerView = binding.limpiezaRecycler
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
            recyclerView.adapter = adapter
        }
    }
}
