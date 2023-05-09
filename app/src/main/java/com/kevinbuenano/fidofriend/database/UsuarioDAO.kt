package com.kevinbuenano.fidofriend.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsuario(usuarioEntity: UsuarioEntity)

    //Encontrar un usuario por el nombre
    @Query("SELECT * FROM usuario WHERE nombre = (:nombreUsuariod)")
    suspend fun getUsuarioByName(nombreUsuariod: String): UsuarioEntity

    @Query("SELECT * FROM usuario WHERE usuario_id = (:id)")
    suspend fun getUsuarioById(id: Int): UsuarioEntity

    //Actualiza los datos de un usuario
    @Update
    suspend fun updateUsuario(usuarioEntity: UsuarioEntity): Int

    //Borra a un usuario de la base de datos
    @Delete
    suspend fun deleteUsuario(usuarioEntity: UsuarioEntity): Int

    //Se inicia sesión con un usuario de la base de datos
    @Query("SELECT * FROM usuario WHERE nombre=(:nombre) AND contrasenya=(:contrasenya)")
    suspend fun iniciarSesion(nombre: String, contrasenya: String): UsuarioEntity
}