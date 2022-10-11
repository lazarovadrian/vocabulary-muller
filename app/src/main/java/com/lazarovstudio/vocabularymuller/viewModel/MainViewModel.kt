package com.lazarovstudio.vocabularymuller.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lazarovstudio.vocabularymuller.model.Dictionary
import com.lazarovstudio.vocabularymuller.model.FireBaseData

class MainViewModel : ViewModel() {
    private val fireBaseData = FireBaseData()

    private val _liveDataFavorite = MutableLiveData<List<Dictionary>>()
    val liveDataFavorite get() = _liveDataFavorite

    private val _liveDataWordsList = MutableLiveData<List<Dictionary>>()
    val liveDataWordsList get() = _liveDataWordsList

//    private val _liveDataCountSee = MutableLiveData(0)
//    val liveDataCountSee: LiveData<Int> get() = _liveDataCountSee

    fun loadListWord() {
        fireBaseData.getDictionary(object : FireBaseData.ReadDataInterface {
            override fun readData(list: List<Dictionary>) {
                _liveDataWordsList.value = list.sortedWith(compareBy { it.word })
            }
        })
    }

    fun saveFavorite(word: List<Dictionary>) {
        _liveDataFavorite.value = word
    }

    fun wordViewed(word: Dictionary) {
        var counter = word.countSee.toInt()
        counter++
        fireBaseData.wordViewed(word, counter)
    }

}