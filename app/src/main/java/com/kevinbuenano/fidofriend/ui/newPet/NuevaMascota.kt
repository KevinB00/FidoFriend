package com.kevinbuenano.fidofriend.ui.newPet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kevinbuenano.fidofriend.databinding.ActivityNuevaMascotaBinding

class NuevaMascota : AppCompatActivity() {
    private lateinit var binding: ActivityNuevaMascotaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityNuevaMascotaBinding.inflate(layoutInflater).also { binding = it }.root)

    }
}