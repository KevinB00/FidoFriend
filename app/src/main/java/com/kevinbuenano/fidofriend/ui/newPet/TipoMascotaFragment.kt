package com.kevinbuenano.fidofriend.ui.newPet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kevinbuenano.fidofriend.databinding.FragmentTipoMascotaBinding


/**
 * A simple [Fragment] subclass.
 * Use the [TipoMascotaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TipoMascotaFragment : Fragment() {
    lateinit var binding: FragmentTipoMascotaBinding
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTipoMascotaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.btnTipoPerro.setOnClickListener {
            var tipoAnimal = 1
            val action = TipoMascotaFragmentDirections.actionTipoMascotaFragmentToDatosMascotaFragment(tipoAnimal)
            navController.navigate(action)
        }
        binding.btnTipoGato.setOnClickListener {
            var tipoAnimal = 2
            val action = TipoMascotaFragmentDirections.actionTipoMascotaFragmentToDatosMascotaFragment(tipoAnimal)
            navController.navigate(action)
        }
    }


}