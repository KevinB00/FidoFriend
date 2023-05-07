package com.kevinbuenano.fidofriend.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity

//Interfaz para declarar métodos y poder acceder a los datos de la base de datos Usuario.
@Dao
interface UsuarioDAO {
    //Conseguir un listado de todos los usuarios
    @Query("SELECT * FROM usuario")
    fun getAllUsuarios(): MutableList<UsuarioEntity>

    //Añadir un usuario al registrarse
    @Insert
    fun addUsuario(usuarioEntity: UsuarioEntity):Long

    //Encontrar un usuario por el id
    @Query(value = "SELECT * FROM usuario WHERE usuario_id LIKE :id")
    fun getUsuarioById(id: Int): UsuarioEntity

    //Actualiza los datos de un usuario
    @Update
    fun updateUsuario(usuarioEntity: UsuarioEntity): Int

    //Borra a un usuario de la base de datos
    @Delete
    fun deleteUsuario(usuarioEntity: UsuarioEntity): Int

    //Se inicia sesión con un usuario de la base de datos
    @Query("SELECT * FROM usuario WHERE nombre=(:nombre) AND contrasenya=(:contrasenya)")
    fun cargarUsuario(nombre: String, contrasenya: String): UsuarioEntity
}