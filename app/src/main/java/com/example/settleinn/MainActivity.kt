package com.example.settleinn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigationView()

        if (savedInstanceState == null) {
            replaceFragment(homeScreenFragment())
            findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.home
        }
    }
    private fun setupBottomNavigationView() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.home -> homeScreenFragment()
                R.id.saved_homes -> SavedHousesFragment()
                R.id.settings -> settingsFragment()
                else -> null
            }
            selectedFragment?.let {
                replaceFragment(it)
            }
            selectedFragment != null
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }
}
