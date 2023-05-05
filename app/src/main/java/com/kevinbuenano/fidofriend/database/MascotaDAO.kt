package com.kevinbuenano.fidofriend.database

import androidx.room.Dao
import androidx.room.Insert
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity

@Dao
interface MascotaDAO {
    //Obtener listado de mascotas
    fun getAllMascotas(): MutableList<MascotaEntity>

    //Añadir mascota
    @Insert
    fun addMascota(mascotaEntity: MascotaEntity)

    //Obtener mascota con el id
    fun getMascotaById(id: Int): MascotaEntity
}