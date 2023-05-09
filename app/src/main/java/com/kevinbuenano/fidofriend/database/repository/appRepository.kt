package com.kevinbuenano.fidofriend.database.repository

import androidx.lifecycle.MutableLiveData
import com.kevinbuenano.fidofriend.database.MascotaDAO
import com.kevinbuenano.fidofriend.database.UsuarioDAO
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity

class appRepository(private val usuarioDAO: UsuarioDAO, private val mascotaDAO: MascotaDAO) {
    fun getMascotasByIdUser(usuarioId: Int): MutableLiveData<MutableList<MascotaEntity>> = MutableLiveData(mascotaDAO.getMascotasByIdUser(usuarioId))

    suspend fun addUsuario(usuario: UsuarioEntity) = usuarioDAO.addUsuario(usuario)

    suspend fun updateUsuario(usuario: UsuarioEntity) = usuarioDAO.updateUsuario(usuario)

    suspend fun removeUsuario(usuario: UsuarioEntity) = usuarioDAO.deleteUsuario(usuario)

    suspend fun iniciarSesion(nombre: String, contrasenya: String) = usuarioDAO.iniciarSesion(nombre, contrasenya)

    suspend fun getUsuarioByName(nombre: String) = usuarioDAO.getUsuarioByName(nombre)

    suspend fun getUsuarioById(id: Int) = usuarioDAO.getUsuarioById(id)


    suspend fun addMascota(mascota: MascotaEntity) = mascotaDAO.addMascota(mascota)

    suspend fun updateMascota(mascota: MascotaEntity) = mascotaDAO.updateMascota(mascota)

    suspend fun removeMascota(mascota: MascotaEntity) = mascotaDAO.deleteMascota(mascota)



}