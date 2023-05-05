package com.kevinbuenano.fidofriend.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.kevinbuenano.fidofriend.adapters.MascotaAdapter
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.viewmodel.MascotaViewModel
import com.kevinbuenano.fidofriend.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var recyclerView: RecyclerView
    var mascotas: MutableList<MascotaEntity> = mutableListOf()
    lateinit var adapterPerro: MascotaAdapter
    private lateinit var mascotaViewModel: MascotaViewModel

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
        mascotaViewModel = ViewModelProvider(this)[MascotaViewModel::class.java]
        mascotaViewModel.getPerroGato(1)

        mascotaViewModel.tipoMascotaLD.observe(viewLifecycleOwner){
            mascotas.clear()
            mascotas.addAll(it)
            recyclerView.adapter?.notifyDataSetChanged()
        }
       // binding.recyclerPerros.adapter = MascotaAdapter()

    }
}