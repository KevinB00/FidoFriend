package com.kevinbuenano.fidofriend.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "mascota",
        foreignKeys = [ForeignKey(
            entity = UsuarioEntity::class,
            childColumns = ["usuario_id"],
            parentColumns = ["usuario_id"],
            onDelete = CASCADE
        )],
    indices = arrayOf(
        Index(
            value = arrayOf("usuario_id"),
            name = "idx_mascota_usuario"
        )
    )
)
data class MascotaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mascota_id")
    var id_mascota:Int,
    @ColumnInfo(name = "mascota_nombre")
    var nombre: String,
    @ColumnInfo(name = "fecha_nacimiento")
    var fecha_nacimiento: String,
    @ColumnInfo(name = "peso")
    var peso: Float,
    @ColumnInfo(name = "estado")
    var estado: String,
    @ColumnInfo(name = "edad")
    var edad: Int,
    @ColumnInfo(name = "actividad")
    var actividad: String,
    @ColumnInfo(name = "porte")
    var porte: String,
    @ColumnInfo(name = "perroGato")
    var perroGato: Int,
    @ColumnInfo(name = "usuario_id")
    var usuario_id: Int

)
