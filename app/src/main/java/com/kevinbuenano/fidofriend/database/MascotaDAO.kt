package com.kevinbuenano.fidofriend.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity

@Dao
interface MascotaDAO {
    //Obtener listado de mascotas
    @Query("SELECT * FROM mascota")
    fun getAllMascotas(): MutableList<MascotaEntity>

    //Añadir mascota
    @Insert
    fun addMascota(mascotaEntity: MascotaEntity)

    //Obtener mascota con el id
    @Query(value = "SELECT * FROM mascota WHERE mascota_id LIKE :id")
    fun getMascotaById(id: Int): MascotaEntity

    //Obtener listado de perros/gatos
    @Query(value = "SELECT * FROM mascota WHERE perroGato LIKE :tipoMascota")
    fun getPerroGato(tipoMascota: Int): MutableList<MascotaEntity>
}