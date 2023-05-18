package com.kevinbuenano.fidofriend.ui.mascota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kevinbuenano.fidofriend.adapters.HistorialMedicoAdapter
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.HistorialMedicoEntity
import com.kevinbuenano.fidofriend.database.repository.historialMedicoRepository
import com.kevinbuenano.fidofriend.databinding.FragmentHistorialMedicoBinding

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
    private lateinit var historialMedicoEntity: HistorialMedicoEntity



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

    }

}