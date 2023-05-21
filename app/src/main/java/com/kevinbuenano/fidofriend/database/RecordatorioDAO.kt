package com.kevinbuenano.fidofriend.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface RecordatorioDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recordatorio: RecordatorioEntity)
}