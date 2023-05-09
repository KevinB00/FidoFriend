package com.kevinbuenano.fidofriend.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.kevinbuenano.fidofriend.R
import com.kevinbuenano.fidofriend.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMenuBinding.inflate(layoutInflater).also { binding = it }.root)
        setSupportActionBar(binding.toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment
        ),
        binding.menuLayout
            )

        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }
}