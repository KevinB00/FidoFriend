package com.kevinbuenano.fidofriend.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "historial_limpieza",
    foreignKeys = [ForeignKey(
        entity = MascotaEntity::class,
        childColumns = ["mascota"],
        parentColumns = ["mascota_id"],
        onDelete = CASCADE
    )],
    indices = arrayOf(
        Index(
            value = arrayOf("mascota"),
            name = "idx_historial_limpieza_mascota"
        )
    ))
data class HistorialLimpiezaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "historial_limpieza_id")
    var historialId: Int,
    @ColumnInfo(name = "parte_limpieza")
    var parteLimpieza: String,
    @ColumnInfo(name = "fecha")
    var fecha: String,
    @ColumnInfo(name = "mascota")
    var mascotaId: Int
)
