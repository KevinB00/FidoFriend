package com.kevinbuenano.fidofriend.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

//Creación de la tabla Usuario
@Entity(tableName = "usuario",
        indices = arrayOf(
            Index(
                value = arrayOf("nombre","email"),
                name = "idx_nombre_email",
                unique = true
            )
        )
)
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "usuario_id")
    var id:Int = 0,
    @ColumnInfo(name = "nombre")
    var nombre:String ="",
    @ColumnInfo(name = "contrasenya")
    var contrasenya:String ="",
    @ColumnInfo(name = "email")
    var email:String ="",
    @ColumnInfo(name = "localidad")
    var localidad:String =""
)