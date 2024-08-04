package com.lazarovstudio.vocabularymuller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lazarovstudio.vocabularymuller.data.room.Dependencies
import com.lazarovstudio.vocabularymuller.databinding.ActivityMainBinding
import com.lazarovstudio.vocabularymuller.extension.openHostFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        Dependencies.init(context = applicationContext)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val navView: BottomNavigationView = binding.navButton
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavHost) as NavHostFragment
        val buttonNav = navHostFragment.navController
        navView.setupWithNavController(buttonNav)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            if (navController != null) {
                openHostFragment(navController)
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() ?: super.onSupportNavigateUp()
    }
}