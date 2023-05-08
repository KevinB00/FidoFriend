package com.kevinbuenano.fidofriend.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.UsuarioEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application): AndroidViewModel(application) {
    val context = application
    var db: appDatabase = appDatabase.getInstance(context)
    lateinit var usuario: UsuarioEntity


     val insertUsuarioLD: MutableLiveData<UsuarioEntity> = MutableLiveData()
     val usuarioListLD: MutableLiveData<MutableList<UsuarioEntity>> = MutableLiveData()
     val cargarUsuarioLD: MutableLiveData<UsuarioEntity> = MutableLiveData()
     val updateUsuarioLD: MutableLiveData<UsuarioEntity?> = MutableLiveData()
     val deleteUsuarioLD: MutableLiveData<Int> = MutableLiveData()


    fun cargarUsuario(nombre: String, contrasenya: String){
        viewModelScope.launch(Dispatchers.IO) {
            cargarUsuarioLD.postValue(db.usuarioDao().cargarUsuario(nombre, contrasenya))
        }
    }
    fun getAllUsuarios(){
        viewModelScope.launch(Dispatchers.IO){
            usuarioListLD.postValue(db.usuarioDao().getAllUsuarios())
        }
    }
    fun getUsuarioById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            usuario = db.usuarioDao().getUsuarioById(id)
            cargarUsuarioLD.postValue(usuario)
        }
    }
    fun add(usuario: UsuarioEntity){
        viewModelScope.launch(Dispatchers.IO){
            val id = db.usuarioDao().addUsuario(usuario)
            val recoveryUsuario = db.usuarioDao().getUsuarioById(id.toInt())
            insertUsuarioLD.postValue(recoveryUsuario)
        }
    }

    fun updateUsuario(usuario: UsuarioEntity){
        viewModelScope.launch(Dispatchers.IO){

              val res = db.usuarioDao().updateUsuario(usuario)
               if(res > 0) {
                   updateUsuarioLD.postValue(usuario)
               }else{
                   updateUsuarioLD.postValue(null)
               }

           }
    }

    fun deleteUsuario(usuarioEntity: UsuarioEntity){
        viewModelScope.launch(Dispatchers.IO){
              val res =  db.usuarioDao().deleteUsuario(usuarioEntity)
              if (res > 0){
                  deleteUsuarioLD.postValue(usuarioEntity.id)
              }else{
                  deleteUsuarioLD.postValue(-1)
              }
        }
    }

}