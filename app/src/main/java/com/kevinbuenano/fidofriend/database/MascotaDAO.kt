package com.kevinbuenano.fidofriend.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity

@Dao
interface MascotaDAO {
    //Obtener listado de mascotas
    @Query("SELECT * FROM mascota")
    fun getAllMascotas(): MutableList<MascotaEntity>

    //Obtener mascotas por id del usuario
    @Query("SELECT * FROM mascota WHERE usuario_id = :usuarioId")
    fun getMascotasByIdUser(usuarioId: Int): MutableList<MascotaEntity>

    //Añadir mascota
    @Insert
    fun addMascota(mascotaEntity: MascotaEntity)

    //Obtener mascota con el id
    @Query(value = "SELECT * FROM mascota WHERE mascota_id LIKE :id")
    fun getMascotaById(id: Int): MascotaEntity

    //Obtener listado de perros/gatos
    @Query(value = "SELECT * FROM mascota WHERE perroGato LIKE :tipoMascota")
    fun getPerroGato(tipoMascota: Int): MutableList<MascotaEntity>

    @Update
    fun updateMascota(mascota: MascotaEntity): Int

    @Delete
    fun deleteMascota(mascota: MascotaEntity): Int
}