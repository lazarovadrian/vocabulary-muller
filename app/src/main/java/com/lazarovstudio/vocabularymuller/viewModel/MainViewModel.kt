package com.lazarovstudio.vocabularymuller.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lazarovstudio.vocabularymuller.model.Dictionary
import com.lazarovstudio.vocabularymuller.model.FireBaseData

class MainViewModel : ViewModel() {
    private val fireBaseData = FireBaseData()

    private val _liveDataFavorite = MutableLiveData<List<Dictionary>>()
    val liveDataFavorite: LiveData<List<Dictionary>> = _liveDataFavorite

    //из базы словарь загружается и хранится полный список
    private val _liveDataWordsList = MutableLiveData<List<Dictionary>>()
    val liveDataWordsList: LiveData<List<Dictionary>> = _liveDataWordsList

    init {
        loadListWord()
    }

    private fun loadListWord() {
        fireBaseData.getDictionary(object : FireBaseData.ReadDataInterface {
            override fun readData(list: List<Dictionary>) {
                _liveDataWordsList.postValue(list.sortedWith(compareBy { it.word }))
            }
        })
    }

    fun onSaveFavorite(favoriteWord: Dictionary) {
        val favoriteList = _liveDataFavorite.value ?: mutableListOf()
        val favoriteSet = HashSet(favoriteList)

        if (favoriteSet.contains(favoriteWord)) {
            favoriteSet.remove(favoriteWord)
            favoriteWord.save = false
        } else {
            favoriteSet.add(favoriteWord)
            favoriteWord.save = true
        }
        _liveDataFavorite.postValue(ArrayList(favoriteSet))
    }

    fun wordViewed(word: Dictionary) {
        var counter = word.countSee.toInt()
        counter += 1
        fireBaseData.wordViewed(word, counter)
        val list = _liveDataWordsList.value.orEmpty().toMutableList()
        val index = list.indexOfFirst { it.id == word.id }
        if(index != -1){
            val updateWord = word.copy(countSee = counter.toString())
            list[index] = updateWord
            _liveDataWordsList.postValue(list)
        }
    }
}
