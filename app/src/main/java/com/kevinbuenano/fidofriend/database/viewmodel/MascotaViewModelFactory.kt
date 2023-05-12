package com.kevinbuenano.fidofriend.database.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MascotaViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MascotaViewModel::class.java)) {
            return MascotaViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}