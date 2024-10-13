package com.lazarovstudio.vocabularymuller.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lazarovstudio.data.remote.DictionaryApi
import com.lazarovstudio.data.remote.vo.DictionaryVO
import com.lazarovstudio.data.room.Dependencies.dictionaryRealization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainViewModel : ViewModel() {
    private val dictionaryApi = DictionaryApi()
    private val repository = dictionaryRealization
    private val _liveDataWordsListNew = repository.allDictionaryAsFlow().asLiveData()
    private val _searchWord = MutableStateFlow(listOf(DictionaryVO()))
    val searchWord: StateFlow<List<DictionaryVO>> = _searchWord.asStateFlow()
    val liveDataWordsList: LiveData<List<DictionaryVO>> = _liveDataWordsListNew

    init {
        checkDataBase()
    }

    private fun checkDataBase() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val countDictionary = dictionaryRealization.getDictionaryCount()
                if (countDictionary == 0) {
                    // Асинхронно получаем данные с API
                    val dictionaryList = getDictionaryFromApi()
                    // Вставляем данные в базу данных
                    dictionaryRealization.insertDictionary(dictionaryList)
                }
            } catch (e: Exception) {
                Log.e("DATA_ERROR", "An error occurred: ${e.message}")
            }
        }
    }

    private suspend fun getDictionaryFromApi(): List<DictionaryVO> =
        suspendCoroutine { continuation ->
            dictionaryApi.getDictionary(object :
                DictionaryApi.ReadDataInterface {
                override fun readData(list: List<DictionaryVO>) {
                    continuation.resume(list)
                }
            })
        }

    fun searchWord(char: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchWord.value = repository.searchDictionary(char)
            } catch (e: Exception) {
                Log.e("DATA_ERROR", "An error occurred: ${e.message}")
            }
        }
    }

    fun onFavoriteClick(
        isFavorite: Boolean,
        favoriteWord: com.lazarovstudio.data.remote.vo.FavoriteVO
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOrRemoveFavorite(word = favoriteWord, isFavorite = isFavorite)
        }
    }

    fun viewWord(word: DictionaryVO) {
        viewModelScope.launch(Dispatchers.IO) {
            val counter = word.countSee.toInt() + 1
            val countSee = counter.toString()
            val updateWord = word.copy(countSee = countSee)
            repository.updateDictionary(uid = word.uid, data = updateWord)
            dictionaryApi.wordViewed(word = updateWord)
        }
    }
}
