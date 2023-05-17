package com.kevinbuenano.fidofriend.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevinbuenano.fidofriend.database.entities.HistorialMedicoEntity
import com.kevinbuenano.fidofriend.databinding.ViewHistorialVeterinarioBinding

class HistorialMedicoAdapter(private val historialMedico: List<HistorialMedicoEntity>, private val historialMedicoClickListener: (HistorialMedicoEntity) -> Unit) : RecyclerView.Adapter<HistorialMedicoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistorialMedicoAdapter.ViewHolder {
        val binding = ViewHistorialVeterinarioBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistorialMedicoAdapter.ViewHolder, position: Int) {
        holder.bind(historialMedico[position])
        holder.itemView.setOnClickListener {
            historialMedicoClickListener(historialMedico[position])
        }
    }

    override fun getItemCount(): Int = historialMedico.size

    class ViewHolder(private val binding: ViewHistorialVeterinarioBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(historialMedico: HistorialMedicoEntity) {
            binding.tituloRevision.text = historialMedico.titulo
            binding.fechaRevision.text = historialMedico.fecha
            binding.resumenVisita.text = historialMedico.descripcion
        }
    }

}