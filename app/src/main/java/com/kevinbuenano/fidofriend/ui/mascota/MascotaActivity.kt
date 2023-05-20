package com.kevinbuenano.fidofriend.ui.mascota

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.kevinbuenano.fidofriend.R
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.databinding.ActivityMascotaBinding

class MascotaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMascotaBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var db: appDatabase
    private var mascotaId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMascotaBinding.inflate(layoutInflater).also { binding = it }.root)
        db = appDatabase.getDatabase(applicationContext)
        mascotaId = intent.getIntExtra("idMascota", -1)
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.bottom_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.infoMascotaFragment,
            R.id.historialMedicoFragment,
            R.id.historialLimpiezaFragment,
            R.id.modificarMascotaFragment,
            R.id.mejoraMascotaFragment,
            R.id.mostrarMejoraFragment
        )
        )

        NavigationUI.setupWithNavController(binding.bottomNavView, navController)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
    }

    fun getDB(): appDatabase =  db
    fun getMascotaId(): Int = mascotaId
}