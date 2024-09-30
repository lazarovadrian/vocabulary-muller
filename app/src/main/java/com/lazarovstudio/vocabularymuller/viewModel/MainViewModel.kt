package com.lazarovstudio.vocabularymuller.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazarovstudio.vocabularymuller.data.remote.DictionaryApi
import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.data.room.Dependencies.dictionaryRealization
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    private val dictionaryApi = DictionaryApi()
    private val _liveDataWordsList = MutableLiveData<List<DictionaryVO>>()
    val liveDataWordsList: LiveData<List<DictionaryVO>> = _liveDataWordsList

    private fun loadListWord() {
        dictionaryApi.getDictionary(object : DictionaryApi.ReadDataInterface {
            override fun readData(list: List<DictionaryVO>) {
                insert(list) {}
            }
        })
    }

    init {
        checkDataBase()
    }

    private fun checkDataBase() {
        viewModelScope.launch {
            try {
                val countDictionary = dictionaryRealization.getDictionaryCount()
                if (countDictionary > 0) {
                    _liveDataWordsList.value = dictionaryRealization.allDictionary()
                } else {
                    loadListWord()
                }
            } catch (e: Exception) {
                Log.e("DATA_ERROR", "An error occurred: ${e.message}")
            }
        }
    }

    private fun insert(data: List<DictionaryVO>, onSuccess: () -> Unit) {
        viewModelScope.launch {
            dictionaryRealization.insertDictionary(data) {
                onSuccess()
            }
        }
    }

    fun viewWord(word: DictionaryVO) {
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
