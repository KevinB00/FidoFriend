package com.kevinbuenano.fidofriend.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kevinbuenano.fidofriend.database.entities.HistorialLimpiezaEntity

@Dao
interface HistorialLimpiezaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLimpieza(historialLimpiezaEntity: HistorialLimpiezaEntity)

    @Delete
    suspend fun deleteLimpieza(historialLimpiezaEntity: HistorialLimpiezaEntity): Int

    @Update
    suspend fun updateLimpieza(historialLimpiezaEntity: HistorialLimpiezaEntity): Int

    @Query(value = "SELECT * FROM historial_limpieza WHERE mascota = :idMascota")
    fun getLimpieza(idMascota: Int): MutableList<HistorialLimpiezaEntity>
}