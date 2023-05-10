package com.kevinbuenano.fidofriend.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity
import com.kevinbuenano.fidofriend.database.repository.usuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UsuarioViewModel(application: Application): AndroidViewModel(application) {
    val repository: usuarioRepository
    val context = application


    init {
        val db= appDatabase.getDatabase(application)
        val usuarioDAO = db.usuarioDao()
        repository = usuarioRepository(usuarioDAO)
    }

     val sesionUsuarioLD: MutableLiveData<UsuarioEntity> = MutableLiveData()
     val updateUsuarioLD: MutableLiveData<UsuarioEntity?> = MutableLiveData()
     val deleteUsuarioLD: MutableLiveData<Int> = MutableLiveData()


    fun iniciarSesion(nombre: String, contrasenya: String){
        viewModelScope.launch(Dispatchers.IO) {

            sesionUsuarioLD.postValue(repository.iniciarSesion(nombre, contrasenya))
        }
    }

    fun getUsuarioByName(nombreUsuario: String){
        viewModelScope.launch(Dispatchers.IO) {
            sesionUsuarioLD.postValue(repository.getUsuarioByName(nombreUsuario))
        }
    }

    fun add(usuario: UsuarioEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUsuario(usuario)
        }
    }

    fun updateUsuario(usuario: UsuarioEntity){
        viewModelScope.launch(Dispatchers.IO){

              val res = repository.updateUsuario(usuario)
               if(res > 0) {
                   updateUsuarioLD.postValue(usuario)
               }else{
                   updateUsuarioLD.postValue(null)
               }

           }
    }

    fun deleteUsuario(usuarioEntity: UsuarioEntity){
        viewModelScope.launch(Dispatchers.IO){
              val res =  repository.removeUsuario(usuarioEntity)
              if (res > 0){
                  deleteUsuarioLD.postValue(usuarioEntity.id)
              }else{
                  deleteUsuarioLD.postValue(-1)
              }
        }
    }

}