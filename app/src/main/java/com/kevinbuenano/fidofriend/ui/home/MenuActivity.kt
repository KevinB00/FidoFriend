package com.kevinbuenano.fidofriend.ui.home

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.kevinbuenano.fidofriend.R
import com.kevinbuenano.fidofriend.database.viewmodel.MascotaViewModel
import com.kevinbuenano.fidofriend.database.viewmodel.UsuarioViewModel
import com.kevinbuenano.fidofriend.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var usuarioViewModel: UsuarioViewModel
    private lateinit var mascotaViewModel: MascotaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMenuBinding.inflate(layoutInflater).also { binding = it }.root)

        setSupportActionBar(binding.toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        try {
            val viewModelProvider = ViewModelProvider(
                navController.getViewModelStoreOwner(R.id.menu_graph_xml),
                ViewModelProvider.AndroidViewModelFactory(application)
            )
            usuarioViewModel = viewModelProvider[UsuarioViewModel::class.java]
            mascotaViewModel = viewModelProvider[MascotaViewModel::class.java]

        }catch (e: IllegalArgumentException){
            e.printStackTrace()
        }

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment
        ),
        binding.menuLayout
            )

        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)

        var nombreUsuario = intent.getStringExtra("nombreUsuario")
        val bundle = Bundle();
        bundle.putString("nombreUsuario", nombreUsuario );
        val homeFragment = HomeFragment();
        homeFragment.arguments = bundle;
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, homeFragment).commit()

    }

    fun getUsuarioViewModel(): UsuarioViewModel{
        return usuarioViewModel
    }

    fun getMascotaViewModel(): MascotaViewModel{
        return mascotaViewModel
    }

}