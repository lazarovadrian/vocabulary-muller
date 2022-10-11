package com.lazarovstudio.vocabularymuller.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lazarovstudio.vocabularymuller.R

fun AppCompatActivity.openFragment(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.block_alphabet_symbol, fragment)
        .addToBackStack(fragment::class.simpleName)
        .commit()
}

//для фрагментов
fun Fragment.openFragment(fragment: Fragment) {
    activity?.supportFragmentManager
        ?.beginTransaction()
        ?.replace(R.id.block_alphabet_symbol, fragment)
        ?.addToBackStack(fragment::class.simpleName)
        ?.commit()
}

