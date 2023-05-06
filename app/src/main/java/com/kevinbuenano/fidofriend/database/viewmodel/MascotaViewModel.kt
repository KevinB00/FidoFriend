package com.kevinbuenano.fidofriend.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MascotaViewModel(application: Application): AndroidViewModel(application){
    lateinit var mascotaEntity: MascotaEntity
    val context = application
    var db: appDatabase = appDatabase.getInstance(context)

    val tipoMascotaLD: MutableLiveData<MutableList<MascotaEntity>> = MutableLiveData()

    fun getPerroGato(tipoMascota: Int){
        viewModelScope.launch(Dispatchers.IO){
            tipoMascotaLD.postValue(db.mascotaDao().getPerroGato(tipoMascota))
        }
    }

    infix fun by(activityViewModels: Lazy<MascotaViewModel>) {

    }
}