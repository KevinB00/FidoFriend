package com.kevinbuenano.fidofriend.database.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsuarioViewModel(private val bd: appDatabase): ViewModel() {

     val sesionUsuarioLD: MutableLiveData<UsuarioEntity> = MutableLiveData()
     val insertUsuarioLD: MutableLiveData<UsuarioEntity> = MutableLiveData()
     val updateUsuarioLD: MutableLiveData<UsuarioEntity?> = MutableLiveData()
     val deleteUsuarioLD: MutableLiveData<Int> = MutableLiveData()


    fun iniciarSesion(nombre: String, contrasenya: String){
        viewModelScope.launch(Dispatchers.IO) {
            sesionUsuarioLD.postValue(bd.usuarioDao().iniciarSesion(nombre, contrasenya))
        }
    }

    fun getUsuarioById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            sesionUsuarioLD.postValue(bd.usuarioDao().getUsuarioById(id))
        }
    }

    fun getUsuarioByName(name: String){
        viewModelScope.launch(Dispatchers.IO){
            sesionUsuarioLD.postValue(bd.usuarioDao().getUsuarioByName(name))
        }
    }
    fun add(usuario: UsuarioEntity){
        viewModelScope.launch(Dispatchers.IO){
            bd.usuarioDao().addUsuario(usuario)
        }
    }

    fun updateUsuario(usuario: UsuarioEntity){
        viewModelScope.launch(Dispatchers.IO){

              val res = bd.usuarioDao().updateUsuario(usuario)
               if(res > 0) {
                   updateUsuarioLD.postValue(usuario)
               }else{
                   updateUsuarioLD.postValue(null)
               }

           }
    }

    fun deleteUsuario(usuarioEntity: UsuarioEntity){
        viewModelScope.launch(Dispatchers.IO){
              val res =  bd.usuarioDao().deleteUsuario(usuarioEntity)
              if (res > 0){
                  deleteUsuarioLD.postValue(usuarioEntity.id)
              }else{
                  deleteUsuarioLD.postValue(-1)
              }
        }
    }

}