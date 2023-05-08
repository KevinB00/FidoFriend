package com.kevinbuenano.fidofriend.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kevinbuenano.fidofriend.adapters.GatoAdapter
import com.kevinbuenano.fidofriend.adapters.PerroAdapter
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.viewmodel.MascotaViewModel
import com.kevinbuenano.fidofriend.database.viewmodel.UsuarioViewModel
import com.kevinbuenano.fidofriend.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var recyclerView: RecyclerView
    var mascotas: MutableList<MascotaEntity> = mutableListOf()
    lateinit var adapterPerro: PerroAdapter
    lateinit var adapterGato: GatoAdapter
    private val mascotaViewModel: MascotaViewModel by activityViewModels()
    private lateinit var usuarioViewModel: UsuarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            usuarioViewModel = ViewModelProvider(it)[UsuarioViewModel::class.java]
        }
    }


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
        var idUsuario = requireActivity().intent.getIntExtra("usuarioId",-1)
        usuarioViewModel.getUsuarioById(idUsuario)
        cargarPerros()
        cargarGatos()
        mascotaViewModel.tipoMascotaLD.observe(viewLifecycleOwner){
            mascotas.clear()
            mascotas.addAll(it)

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