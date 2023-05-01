package com.kevinbuenano.fidofriend.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kevinbuenano.fidofriend.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityRegistroBinding.inflate(layoutInflater).also { binding = it }.root)
    }
}