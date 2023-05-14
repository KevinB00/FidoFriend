package com.kevinbuenano.fidofriend.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "recordatorio",
    foreignKeys = [ForeignKey(
        entity = UsuarioEntity::class,
        childColumns = ["usuario"],
        parentColumns = ["usuario_id"],
        onDelete = CASCADE
    )])
data class RecordatorioEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recordatorio_id")
    var recordatorioId: Int,
    @ColumnInfo(name = "fecha")
    var fecha: String,
    @ColumnInfo(name = "texto")
    var texto: String,
    @ColumnInfo(name = "usuario")
    var usuarioId: Int
)
