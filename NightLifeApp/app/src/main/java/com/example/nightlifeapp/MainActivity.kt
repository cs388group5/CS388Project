package com.example.nightlifeapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.nightlifeapp.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.ParseUser


class MainActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    fun onSettingsAction(mi: MenuItem) {
        val parseUser = ParseUser.getCurrentUser()
        val fragmentManager = supportFragmentManager

        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(
            com.example.nightlifeapp.R.id.flContainer,
            SettingsFragment()
        )
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val fragmentManager: FragmentManager = supportFragmentManager
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab: View = findViewById(R.id.fabEmergency)
        fab.setOnClickListener { view ->
            checkPermission()

        }


        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item ->
            val parseUser = ParseUser.getCurrentUser()

            var fragmentToShow: Fragment? = null
            when (item.itemId) {

                R.id.nav_home -> {
                    fragmentToShow = HomeFragment()
                    //Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_map -> {
                    fragmentToShow = MapFragment()
                    //Toast.makeText(this, "Map", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_report -> {
                    if (parseUser == null){
                        fragmentToShow = LoginFragment()
                    }else {
                        fragmentToShow = ReportFragment()
                        //Toast.makeText(this, "Report", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.nav_profile -> {
                    if (parseUser == null){
                        fragmentToShow = LoginFragment()

                    }else {
                        fragmentToShow = ProfileFragment()
                        //Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            if (fragmentToShow != null){

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()

            }

            true
        }

    }
    fun callPhone(){
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:" + 911)
        Toast.makeText(this, "Calling 911", Toast.LENGTH_SHORT).show()
        startActivity(callIntent)

    }
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    42)
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }
    private fun goToLogin(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(com.example.nightlifeapp.R.id.flContainer, LoginFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}