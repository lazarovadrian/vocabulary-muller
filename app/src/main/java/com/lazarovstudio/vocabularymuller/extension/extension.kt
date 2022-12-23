package com.lazarovstudio.vocabularymuller.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.lazarovstudio.vocabularymuller.R

fun AppCompatActivity.openFragment(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.mainNavHost, fragment)
        .addToBackStack(fragment::class.simpleName)
        .commit()
}

//NavHostController
fun AppCompatActivity.openHostFragment(navController: NavController) {
    val navHostFragment = supportFragmentManager
        .findFragmentById(R.id.mainNavHost) as NavHostFragment
    navHostFragment.navController
    setupActionBarWithNavController(navController)
}

fun Fragment.openFragment(fragment: Fragment) {
    activity?.supportFragmentManager
        ?.beginTransaction()
        ?.replace(R.id.mainNavHost, fragment)
        ?.addToBackStack(fragment::class.simpleName)
        ?.commit()
}