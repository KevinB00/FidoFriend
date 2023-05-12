package com.kevinbuenano.fidofriend.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kevinbuenano.fidofriend.adapters.GatoAdapter
import com.kevinbuenano.fidofriend.adapters.PerroAdapter
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.repository.mascotaRepository
import com.kevinbuenano.fidofriend.databinding.FragmentHomeBinding
import com.kevinbuenano.fidofriend.ui.newPet.NuevaMascota
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var recyclerView: RecyclerView
    var perros: MutableList<MascotaEntity> = mutableListOf()
    var gatos: MutableList<MascotaEntity> = mutableListOf()
    lateinit var adapterPerro: PerroAdapter
    lateinit var adapterGato: GatoAdapter
    private var nombreUsuario: String? = null
    private lateinit var mascotaRepository: mascotaRepository
    private lateinit var db: appDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuActivity = activity as? MenuActivity

        if (menuActivity != null) {
            db = menuActivity.getDB()
        }

        mascotaRepository = mascotaRepository(db.mascotaDao())

        cargarPerros()
        cargarGatos()
        mascotaRepository = mascotaRepository(db.mascotaDao())

        binding.floatingActionButton.setOnClickListener {
            var intent:Intent = Intent(activity, NuevaMascota::class.java)
        }

    }

    override fun onResume() {
        super.onResume()
        requireActivity().title = "Inicio"
    }

    private fun cargarGatos() {
        viewLifecycleOwner.lifecycleScope.launch {
            gatos = withContext(Dispatchers.IO) {
                mascotaRepository.getPerroGato(2)
            }
        }
        setUpRecyclerViewGato()
    }

    private fun cargarPerros() {
        viewLifecycleOwner.lifecycleScope.launch {
           perros = withContext(Dispatchers.IO) {
               mascotaRepository.getPerroGato(1)
           }
        }
        setUpRecyclerViewPerro()
    }

    private fun setUpRecyclerViewGato() {
        if (gatos.isNotEmpty()){
            binding.recyclerGatos.adapter = GatoAdapter(gatos)
            recyclerView = binding.recyclerGatos
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
            recyclerView.adapter = adapterGato
        }
    }

    private fun setUpRecyclerViewPerro() {
        if (perros.isNotEmpty()){
            binding.recyclerPerros.adapter = PerroAdapter(perros)
            recyclerView = binding.recyclerPerros
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
            recyclerView.adapter = adapterPerro
        }
    }
}