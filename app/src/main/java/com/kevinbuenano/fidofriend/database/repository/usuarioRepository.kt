package com.kevinbuenano.fidofriend.database.repository

import com.kevinbuenano.fidofriend.database.UsuarioDAO
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity

class usuarioRepository(private val usuarioDAO: UsuarioDAO) {

    suspend fun addUsuario(usuario: UsuarioEntity) = usuarioDAO.addUsuario(usuario)

    suspend fun updateUsuario(usuario: UsuarioEntity) = usuarioDAO.updateUsuario(usuario)

    suspend fun removeUsuario(usuario: UsuarioEntity) = usuarioDAO.deleteUsuario(usuario)

    suspend fun iniciarSesion(nombre: String, contrasenya: String) = usuarioDAO.iniciarSesion(nombre, contrasenya)

    suspend fun getUsuarioByName(nombre: String) = usuarioDAO.getUsuarioByName(nombre)

    suspend fun getUsuarioById(id: Int) = usuarioDAO.getUsuarioById(id)






}