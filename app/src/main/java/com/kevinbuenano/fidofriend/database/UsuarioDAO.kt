package com.kevinbuenano.fidofriend.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity

//Interfaz para declarar métodos y poder acceder a los datos de la base de datos Usuario.
@Dao
interface UsuarioDAO {
    //Añadir un usuario al registrarse
    @Insert
    fun addUsuario(usuarioEntity: UsuarioEntity):Long

    @Query(value = "SELECT * FROM usuario WHERE id LIKE :id")
    fun getUsuarioById(id: Long): UsuarioEntity
}