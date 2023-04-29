package com.kevinbuenano.fidofriend.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//Creación de la tabla Usuario
@Entity(tableName = "usuario")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var nombre:String = "",
    var contrasenya:String = "",
    var email:String = "",
    var localidad:String = ""
)
