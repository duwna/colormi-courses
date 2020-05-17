package com.duwna.colormi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.duwna.colormi.base.Notify
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.nav_host_fragment)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_news,
                R.id.navigation_search,
                R.id.navigation_current,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    fun renderNotification(notify: Notify) {
        val snackBar = Snackbar.make(container, notify.message, Snackbar.LENGTH_LONG).apply {
            anchorView = nav_view
        }
        when (notify) {
            is Notify.NoAuthentication ->  {
                snackBar.setAction(notify.actionLabel) {
                    navController.navigate(R.id.navigation_auth)
                }.duration = Snackbar.LENGTH_LONG
            }
            is Notify.ActionMessage ->
                snackBar.setAction(notify.actionLabel) { notify.actionHandler.invoke() }
        }
        snackBar.show()
    }

    fun showNavView() {
        nav_view.animate().translationY(0f).withStartAction {
            nav_view.isVisible = true
        }.duration = 300
    }

    fun hideNavView() {
        nav_view.animate().translationY(nav_view.height.toFloat()).withEndAction {
            nav_view.isVisible = false
        }.duration = 300
    }
}
