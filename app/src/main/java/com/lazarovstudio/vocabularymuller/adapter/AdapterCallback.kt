package com.lazarovstudio.vocabularymuller.adapter

interface AdapterCallback<T>{
    fun showDetailFragment(model: T)
}