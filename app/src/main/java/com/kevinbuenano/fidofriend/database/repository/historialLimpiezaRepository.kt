package com.kevinbuenano.fidofriend.database.repository

import com.kevinbuenano.fidofriend.database.HistorialLimpiezaDAO
import com.kevinbuenano.fidofriend.database.entities.HistorialLimpiezaEntity

class historialLimpiezaRepository(private val historialLimpiezaDAO: HistorialLimpiezaDAO) {

    suspend fun addHistorialLimpieza(historialLimpiezaEntity: HistorialLimpiezaEntity) = historialLimpiezaDAO.addLimpieza(historialLimpiezaEntity)

    suspend fun updateHistorialLimpieza(historialLimpiezaEntity: HistorialLimpiezaEntity) = historialLimpiezaDAO.updateLimpieza(historialLimpiezaEntity)

    suspend fun removeHistorialLimpieza(historialLimpiezaEntity: HistorialLimpiezaEntity) = historialLimpiezaDAO.deleteLimpieza(historialLimpiezaEntity)

    suspend fun getHistorialLimpiezaById(id: Int) = historialLimpiezaDAO.getLimpieza(id)

}