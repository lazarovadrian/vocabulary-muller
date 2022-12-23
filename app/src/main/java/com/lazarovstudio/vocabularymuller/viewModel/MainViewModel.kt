package com.lazarovstudio.vocabularymuller.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lazarovstudio.vocabularymuller.model.Dictionary
import com.lazarovstudio.vocabularymuller.model.FireBaseData

class MainViewModel : ViewModel() {
    private val fireBaseData = FireBaseData()

    private val _liveDataFavorite = MutableLiveData<List<Dictionary>>()
    val liveDataFavorite get() = _liveDataFavorite

    private val _liveDataWordsList = MutableLiveData<List<Dictionary>>()
//    val liveDataWordsList get() = _liveDataWordsList

    private val _liveDataFilterWordsList = MutableLiveData<List<Dictionary>>()
    val liveDataFilterWordsList get() = _liveDataFilterWordsList

    fun loadListWord() {
        fireBaseData.getDictionary(object : FireBaseData.ReadDataInterface {
            override fun readData(list: List<Dictionary>) {
                _liveDataWordsList.value = list.sortedWith(compareBy { it.word })
            }
        })
    }

    fun saveFavorite(word: List<Dictionary>) {
        val saveWord = ArrayList<Dictionary>()
        for (favorite in word) {
            saveWord.add(
                Dictionary(
                    favorite.id,
                    favorite.description,
                    favorite.word,
                    favorite.countSee,
                    save = true
                )
            )
        }
        _liveDataFavorite.value = saveWord
    }

    fun wordViewed(word: Dictionary) {
        var counter = word.countSee.toInt()
        counter++
        fireBaseData.wordViewed(word, counter)
    }

    fun filter(char: String) {
        _liveDataFilterWordsList.value = if (char.isEmpty()) {
                 listOf()
//            _liveDataWordsList.value
        } else {
            val searchWord = char.lowercase()
            val resultList = ArrayList<Dictionary>()
            for (item in _liveDataWordsList.value!!) {
                if (item.word.lowercase().startsWith(searchWord)) {
                    resultList.add(item)
                }
            }
            resultList
        }
        Log.d("PUB", liveDataFilterWordsList.value.toString())
    }


}