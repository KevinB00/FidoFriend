package com.kevinbuenano.fidofriend.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.databinding.ViewPerroItemBinding

class PerroAdapter(private val perros: List<MascotaEntity>): RecyclerView.Adapter<PerroAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewPerroItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = perros.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(perros[position])
    }
    class ViewHolder(private val binding: ViewPerroItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(perro: MascotaEntity){
            binding.tViewNombrePerro.text = perro.nombre
        }
    }

}