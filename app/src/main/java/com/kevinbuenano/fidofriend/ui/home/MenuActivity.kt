package com.kevinbuenano.fidofriend.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.kevinbuenano.fidofriend.R
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.databinding.ActivityMenuBinding


class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var db: appDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMenuBinding.inflate(layoutInflater).also { binding = it }.root)
        db = appDatabase.getDatabase(applicationContext)
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.perfilFragment
        ),
        binding.menuLayout
            )

        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)

        /*try {
            val viewModelProvider = ViewModelProvider(
                navController.getViewModelStoreOwner(R.id.menu_graph_xml),
                ViewModelProvider.AndroidViewModelFactory(application)
            )
            usuarioViewModel = viewModelProvider[UsuarioViewModel::class.java]
            mascotaViewModel = viewModelProvider[MascotaViewModel::class.java]

        }catch (e: IllegalArgumentException){
            e.printStackTrace()
        }*/

    }

    fun getDB(): appDatabase =  db


}