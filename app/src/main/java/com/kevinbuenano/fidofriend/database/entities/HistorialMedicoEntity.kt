package com.kevinbuenano.fidofriend.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "historial_mascota",
    foreignKeys = [ForeignKey(
        entity = MascotaEntity::class,
        childColumns = ["mascota"],
        parentColumns = ["mascota_id"],
        onDelete = CASCADE
    )],
    indices = arrayOf(
        Index(
            value = arrayOf("mascota"),
            name = "idx_historial_mascota_mascota"
        )
    ))
data class HistorialMedicoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "historial_mascota_id")
    var historialId: Int,
    @ColumnInfo(name = "titulo")
    var titulo: String,
    @ColumnInfo(name = "fecha")
    var fecha: String,
    @ColumnInfo(name = "descripcion")
    var descripcion: String,
    @ColumnInfo(name = "mascota")
    var mascotaId: Int
)
