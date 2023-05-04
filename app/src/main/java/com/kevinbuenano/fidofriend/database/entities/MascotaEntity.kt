package com.kevinbuenano.fidofriend.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mascota")
data class MascotaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mascota_id")
    var id_mascota:Int
)
