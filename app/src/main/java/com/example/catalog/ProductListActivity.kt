package com.example.catalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProductListActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_product_list) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.navigation_dashboard -> {
                    navController.navigate(R.id.dashboardFragment)
                    true
                }
                R.id.navigation_orders -> {
                    navController.navigate(R.id.ordersFragment)
                    true
                }
                R.id.navigation_notifications -> {
                    navController.navigate(R.id.notificationsFragment)
                    true
                }
                else -> false
            }
        }
    }
}
