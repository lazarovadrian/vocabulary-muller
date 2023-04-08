package com.lazarovstudio.vocabularymuller.adapter

interface AdapterCallback<T>{
    fun showDetailFragment(model: T)
    fun saveFavorite(model: T)
}