package com.lazarovstudio.vocabularymuller.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lazarovstudio.vocabularymuller.data.remote.DictionaryApi
import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO

class MainViewModel : ViewModel() {
    private val dictionaryApi = DictionaryApi()

    private val _liveDataFavorite = MutableLiveData<List<DictionaryVO>>()
    val liveDataFavorite: LiveData<List<DictionaryVO>> = _liveDataFavorite

    //из базы словарь загружается и хранится полный список
    private val _liveDataWordsList = MutableLiveData<List<DictionaryVO>>()
    val liveDataWordsList: LiveData<List<DictionaryVO>> = _liveDataWordsList

    init {
        if (_liveDataWordsList.value.isNullOrEmpty()) {
            loadListWord()
            Log.d("DATA_LOAD", "LOAD")
        } else {
            Log.d("DATA_NOT_LOAD", "NOT_LOAD")
        }
    }

    private fun loadListWord() {
        dictionaryApi.getDictionary(object : DictionaryApi.ReadDataInterface {
            override fun readData(list: List<DictionaryVO>) {
                _liveDataWordsList.postValue(list.sortedWith(compareBy { it.word }))
            }
        })
    }

    fun onSaveFavorite(favoriteWord: DictionaryVO) {
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

    fun wordViewed(word: DictionaryVO) {
        var counter = word.countSee.toInt()
        counter += 1
        dictionaryApi.wordViewed(word, counter)
        val list = _liveDataWordsList.value.orEmpty().toMutableList()
        val index = list.indexOfFirst { it.id == word.id }
        if (index != -1) {
            val updateWord = word.copy(countSee = counter.toString())
            list[index] = updateWord
            _liveDataWordsList.postValue(list)
        }
    }
}