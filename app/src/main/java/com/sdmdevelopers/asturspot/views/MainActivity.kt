package com.sdmdevelopers.asturspot.views

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.sdmdevelopers.asturspot.R

class MainActivity : AppCompatActivity() {

    companion object {
        const val CLAVE_EMAIL : String = "email"
    }

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializamos las vistas
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        drawerLayout = findViewById(R.id.main)
        navigationView = findViewById(R.id.nav_view)

        // Configuramos la toolbar como ActionBar
        setSupportActionBar(toolbar)

        // Configuramos el botón para abrir y cerrar el DrawerLayout
        val drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        vincularElementosMenu()
    }

    private fun vincularElementosMenu() {
        val menuLateral = findViewById<NavigationView>(R.id.nav_view)
        val navHostFragment = findNavController(R.id.fragmentContainerView)

        appBarConfiguration =AppBarConfiguration(navHostFragment.graph)

        menuLateral.setupWithNavController(navHostFragment)

        //si no ha iniciado sesión cambiamos el icono y texto del botón de menú
        if(FirebaseAuth.getInstance().currentUser == null){
            navigationView.menu.findItem(R.id.inicioSesionActivity).title = "Iniciar sesión"
            navigationView.menu.findItem(R.id.inicioSesionActivity).icon = AppCompatResources.getDrawable(this,
                R.drawable.baseline_person_24
            );

        }
    }

    override fun onBackPressed() {
        // Cerrar el drawer si está abierto, en lugar de cerrar la actividad
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = findNavController(R.id.fragmentContainerView)

        return navHostFragment.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}