package com.example.nightlifeapp

import android.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.nightlifeapp.fragments.HomeFragment
import com.example.nightlifeapp.fragments.ProfileFragment
import com.example.nightlifeapp.fragments.ReportFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item ->

            var fragmentToShow: Fragment? = null
            when (item.itemId) {
                R.id.nav_home -> {
                    fragmentToShow = HomeFragment()
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT)
                }
                R.id.nav_map -> {
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