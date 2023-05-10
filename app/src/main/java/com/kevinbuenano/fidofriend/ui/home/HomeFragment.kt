package com.kevinbuenano.fidofriend.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kevinbuenano.fidofriend.R
import com.kevinbuenano.fidofriend.adapters.GatoAdapter
import com.kevinbuenano.fidofriend.adapters.PerroAdapter
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.viewmodel.MascotaViewModel
import com.kevinbuenano.fidofriend.database.viewmodel.UsuarioViewModel
import com.kevinbuenano.fidofriend.databinding.FragmentHomeBinding
import com.kevinbuenano.fidofriend.ui.newPet.NuevaMascota


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var recyclerView: RecyclerView
    var mascotas: MutableList<MascotaEntity> = mutableListOf()
    lateinit var adapterPerro: PerroAdapter
    lateinit var adapterGato: GatoAdapter
    private lateinit var mascotaViewModel: MascotaViewModel
    private val usuarioViewModel: UsuarioViewModel by navGraphViewModels(R.id.menu_graph_xml)


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
        mascotaViewModel = ViewModelProvider(requireActivity()).get(MascotaViewModel::class.java)

        var nombreUsuario = activity?.intent?.getStringExtra("nomnbreUsuario")
        if (nombreUsuario != null) {
            usuarioViewModel.getUsuarioByName(nombreUsuario)
        }
        cargarPerros()
        cargarGatos()
        mascotaViewModel.tipoMascotaLD.observe(viewLifecycleOwner){
            mascotas.clear()
            mascotas.addAll(it)

        }

        binding.floatingActionButton.setOnClickListener {
            var intent:Intent = Intent(activity, NuevaMascota::class.java)
        }

    }

    override fun onResume() {
        super.onResume()
        requireActivity().title = "Inicio"
    }

    private fun cargarGatos() {
        mascotaViewModel.getPerroGato(2)
        setUpRecyclerViewGato()
    }

    private fun cargarPerros() {
        mascotaViewModel.getPerroGato(1)
        setUpRecyclerViewPerro()
    }

    private fun setUpRecyclerViewGato() {
        if (mascotas.isNotEmpty()){
            binding.recyclerGatos.adapter = PerroAdapter(mascotas)
            recyclerView = binding.recyclerGatos
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
            recyclerView.adapter = adapterGato
        }
    }

    private fun setUpRecyclerViewPerro() {
        if (mascotas.isNotEmpty()){
            binding.recyclerPerros.adapter = PerroAdapter(mascotas)
            recyclerView = binding.recyclerPerros
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
            recyclerView.adapter = adapterPerro
        }
    }
}