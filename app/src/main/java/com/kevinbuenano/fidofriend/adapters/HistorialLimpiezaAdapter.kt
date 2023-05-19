package com.kevinbuenano.fidofriend.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevinbuenano.fidofriend.database.entities.HistorialLimpiezaEntity
import com.kevinbuenano.fidofriend.databinding.ViewHistorialLimpiezaBinding

class HistorialLimpiezaAdapter(private val historialLimpieza: List<HistorialLimpiezaEntity>) : RecyclerView.Adapter<HistorialLimpiezaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ViewHistorialLimpiezaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistorialLimpiezaAdapter.ViewHolder, position: Int) {
        holder.bind(historialLimpieza[position])
    }

    override fun getItemCount(): Int = historialLimpieza.size

    class ViewHolder(private val binding: ViewHistorialLimpiezaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(historialLimpieza: HistorialLimpiezaEntity) {
            binding.tipoLimpieza.text = historialLimpieza.parteLimpieza
            binding.fechaLimpieza.text = historialLimpieza.fecha
        }
    }

}