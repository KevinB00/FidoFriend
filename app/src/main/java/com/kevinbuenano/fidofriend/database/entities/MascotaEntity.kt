package com.kevinbuenano.fidofriend.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "mascota",
        foreignKeys = [ForeignKey(
            entity = UsuarioEntity::class,
            childColumns = ["usuario__id"],
            parentColumns = ["usuario_id"],
            onDelete = CASCADE
        )]
)
data class MascotaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mascota_id")
    var id_mascota:Int,
    @ColumnInfo(name = "mascota_nombre")
    var nombre: String,
    @ColumnInfo(name = "fecha_nacimiento")
    var fecha_nacimiento: Date,
    @ColumnInfo(name = "peso")
    var peso: Float,
    @ColumnInfo(name = "estado")
    var estado: String,
    @ColumnInfo(name = "edad")
    var edad: Int,
    @ColumnInfo(name = "actividad")
    var actividad: String,
    @ColumnInfo(name = "raza")
    var raza: String,
    @ColumnInfo(name = "usuario_id")
    var usuario_id: Int

)
