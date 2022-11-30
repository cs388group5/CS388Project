package com.example.nightlifeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.nightlifeapp.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val fragmentManager: FragmentManager = supportFragmentManager
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item ->

            var fragmentToShow: Fragment? = null
            when (item.itemId) {
                R.id.nav_login -> {
                    fragmentToShow = LoginFragment()
                    Toast.makeText(this, "Login", Toast.LENGTH_SHORT)
                }
                R.id.nav_home -> {
                    fragmentToShow = HomeFragment()
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT)
                }
                R.id.nav_map -> {
                    fragmentToShow = MapFragment()
                    Toast.makeText(this, "Map", Toast.LENGTH_SHORT)
                }
                R.id.nav_report -> {
                    fragmentToShow = ReportFragment()
                    Toast.makeText(this, "Report", Toast.LENGTH_SHORT)
                }
                R.id.nav_profile -> {
                    fragmentToShow = ProfileFragment()
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT)
                }
            }

            if (fragmentToShow != null){

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()

            }

            true
        }

    }
}