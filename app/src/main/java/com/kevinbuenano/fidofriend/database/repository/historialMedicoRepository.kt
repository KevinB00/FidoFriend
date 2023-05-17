package com.kevinbuenano.fidofriend.database.repository

import com.kevinbuenano.fidofriend.database.HistorialMedicoDAO
import com.kevinbuenano.fidofriend.database.entities.HistorialMedicoEntity

class historialMedicoRepository(private val historialMedicoDAO: HistorialMedicoDAO) {

    suspend fun addHistorialMedico(historialMedico: HistorialMedicoEntity) = historialMedicoDAO.addHistorialMedico(historialMedico)

    suspend fun updateHistorialMedico(historialMedico: HistorialMedicoEntity) = historialMedicoDAO.updateHistorialMedico(historialMedico)

    suspend fun removeHistorialMedico(historialMedico: HistorialMedicoEntity) = historialMedicoDAO.deleteHistorialMedico(historialMedico)

    suspend fun getHistorialMedicoById(id: Int) = historialMedicoDAO.getHistorialMedico(id)
}