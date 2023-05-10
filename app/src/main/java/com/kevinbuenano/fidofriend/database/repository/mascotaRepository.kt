package com.kevinbuenano.fidofriend.database.repository

import com.kevinbuenano.fidofriend.database.MascotaDAO
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity

class mascotaRepository(private val mascotaDAO: MascotaDAO){
    suspend fun addMascota(mascota: MascotaEntity) = mascotaDAO.addMascota(mascota)

    suspend fun updateMascota(mascota: MascotaEntity) = mascotaDAO.updateMascota(mascota)

    suspend fun removeMascota(mascota: MascotaEntity) = mascotaDAO.deleteMascota(mascota)
    suspend fun getPerroGato(tipoMascota: Int) = mascotaDAO.getPerroGato(tipoMascota)
}