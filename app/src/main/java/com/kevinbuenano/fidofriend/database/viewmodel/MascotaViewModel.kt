package com.kevinbuenano.fidofriend.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.repository.mascotaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MascotaViewModel(application: Application): AndroidViewModel(application){
    val repository: mascotaRepository
    val context = application
init {
    var db: appDatabase = appDatabase.getDatabase(application)
    val mascotaDAO = db.mascotaDao()
    repository = mascotaRepository(mascotaDAO)
}

    val tipoMascotaLD: MutableLiveData<MutableList<MascotaEntity>> = MutableLiveData()

    fun getPerroGato(tipoMascota: Int){
        viewModelScope.launch(Dispatchers.IO){
            tipoMascotaLD.postValue(repository.getPerroGato(tipoMascota))
        }
    }
}
