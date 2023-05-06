package com.kevinbuenano.fidofriend.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.databinding.ViewGatoItemBinding

class GatoAdapter(private val gatos: List<MascotaEntity>) : RecyclerView.Adapter<GatoAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewGatoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = gatos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gatos[position])
    }
    class ViewHolder(private val binding: ViewGatoItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(gato: MascotaEntity){
            binding.tViewNombreGato.text = gato.nombre
        }
    }
}