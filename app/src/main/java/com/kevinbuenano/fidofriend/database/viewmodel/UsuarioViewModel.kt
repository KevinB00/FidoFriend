package com.kevinbuenano.fidofriend.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevinbuenano.fidofriend.database.UsuarioDAO
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application): AndroidViewModel(application) {
    lateinit var usuarioEntity: UsuarioEntity
    val context = application
    var usuarioDAO: UsuarioDAO = appDatabase.getInstance(context)


    val insertUsuarioLD: MutableLiveData<UsuarioEntity> = MutableLiveData()
    val usuarioListLD: MutableLiveData<MutableList<UsuarioEntity>> = MutableLiveData()
    val cargarUsuarioLD: MutableLiveData<UsuarioEntity> = MutableLiveData()

    fun cargarUsuario(nombre: String, contrasenya: String): UsuarioEntity{
        viewModelScope.launch(Dispatchers.IO) {
             usuarioEntity =  usuarioDAO.cargarUsuario(nombre, contrasenya)
        }
        return usuarioEntity
    }
    fun getAllUsuarios(){
        viewModelScope.launch(Dispatchers.IO){
            usuarioListLD.postValue(usuarioDAO.getAllUsuarios())
        }
    }
    fun add(usuario: UsuarioEntity){
        viewModelScope.launch(Dispatchers.IO){
            val id = usuarioDAO.addUsuario(usuario)
            val recoveryUsuario = usuarioDAO.getUsuarioById(id)
            insertUsuarioLD.postValue(recoveryUsuario)
        }
    }


}