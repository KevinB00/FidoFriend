package com.kevinbuenano.fidofriend.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kevinbuenano.fidofriend.database.entities.HistorialMedicoEntity

@Dao
interface HistorialMedicoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistorialMedico(historialMedicoEntity: HistorialMedicoEntity)

    @Delete
    suspend fun deleteHistorialMedico(historialMedicoEntity: HistorialMedicoEntity): Int

    @Update
    suspend fun updateHistorialMedico(historialMedicoEntity: HistorialMedicoEntity): Int

    @Query(value = "SELECT * FROM historial_mascota WHERE mascota = :idMascota")
    fun getHistorialMedico(idMascota: Int): MutableList<HistorialMedicoEntity>
}