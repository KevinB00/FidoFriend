package com.kevinbuenano.fidofriend.ui.home

import android.content.Context
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
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity
import com.kevinbuenano.fidofriend.database.repository.mascotaRepository
import com.kevinbuenano.fidofriend.database.repository.usuarioRepository
import com.kevinbuenano.fidofriend.databinding.FragmentHomeBinding
import com.kevinbuenano.fidofriend.ui.mascota.MascotaActivity
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
    private lateinit var mascotaRepository: mascotaRepository
    private lateinit var usuarioRepository: usuarioRepository
    private lateinit var db: appDatabase
    private lateinit var nombre: String
    private lateinit var usuarioEntity: UsuarioEntity

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
        val usuario = activity?.getSharedPreferences("com.kevinbuenano.fidofriend", Context.MODE_PRIVATE)
        nombre = usuario?.getString("nombre", "").toString()
        val menuActivity = activity as? MenuActivity

        if (menuActivity != null) {
            db = menuActivity.getDB()
        }
        usuarioRepository = usuarioRepository(db.usuarioDao())
        mascotaRepository = mascotaRepository(db.mascotaDao())
        cargar(nombre)

        binding.floatingActionButton.setOnClickListener {
            var intent:Intent = Intent(activity, NuevaMascota::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        cargar(nombre)
    }

    private fun cargar(nombre: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                usuarioEntity = usuarioRepository.getUsuarioByName(nombre)
                gatos = mascotaRepository.getPerroGato(2, usuarioEntity.id)
                perros = mascotaRepository.getPerroGato(1, usuarioEntity.id)
            }
            setUpRecyclerViewGato()
            setUpRecyclerViewPerro()
        }
    }

    private fun setUpRecyclerViewGato() {
        if (gatos.isNotEmpty()){
            adapterGato = GatoAdapter(gatos,
            ) {
                var intent = Intent(activity, MascotaActivity::class.java).putExtra("idMascota", it.id_mascota)
                startActivity(intent)
            }
            recyclerView = binding.recyclerGatos
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapterGato
        }
    }

    private fun setUpRecyclerViewPerro() {
        if (perros.isNotEmpty()){
            adapterPerro = PerroAdapter(perros
            ) {
                var intent = Intent(activity, MascotaActivity::class.java).putExtra("idMascota", it.id_mascota)
                startActivity(intent)
            }
            recyclerView = binding.recyclerPerros
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapterPerro
        }
    }
}