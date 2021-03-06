package com.minhle.flickkfinal


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.minhle.flickkfinal.ui.authentication.LoggedInViewModel
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.white)
        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        /* setSupportActionBar(toolbar)
         // show center aligned title and sub title
         supportActionBar?.apply {
             *//*   toolbarTitle.text = "Mov"
               title = ""*//*
            this.elevation = 15F
        }

        val navBottomView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.nav_host_fragment)

        navBottomView.setupWithNavController(navController)

        //show hide bottom nav
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_movies -> showTitleNav("The Flickk")
                R.id.nav_search -> showTitleNav("Search Movies")
                R.id.nav_user -> showTitleNav("Profile User")
                R.id.nav_detail -> showTitleNavDetail("Movies Detail")
                else -> {
                    showTitleNavDetail("The Flickk")
                }
            }
        }

        loggedInViewModel = ViewModelProvider(this).get(LoggedInViewModel::class.java)*/

    }


/*    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }*/

    /*private fun showTitleNav(txtTitle: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            toolbarTitle.text = txtTitle
            title = ""
            this.elevation = 15F
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)

        }
    }

    private fun showTitleNavDetail(txtTitle: String) {
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            toolbarTitle.text = txtTitle
            title = ""

            this.elevation = 15F
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }
*/
    /*override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
*/

//    //menu notification
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.main_menu, menu)
//        return true
//    }


}