package com.lazarovstudio.vocabularymuller.viewModel

import android.widget.Filter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lazarovstudio.vocabularymuller.model.Dictionary
import com.lazarovstudio.vocabularymuller.model.FireBaseData

class MainViewModel : ViewModel() {
    private val fireBaseData = FireBaseData()

    private val _liveDataFavorite = MutableLiveData<ArrayList<Dictionary>>()
    val liveDataFavorite get() = _liveDataFavorite

    //из базы словарь загружается и хранится полный список
    private val _liveDataWordsList = MutableLiveData<List<Dictionary>>()
    val liveDataWordsList get() = _liveDataWordsList

    //используется при поиске, часто обновляется
    private val _liveDataSearchWordsList = MutableLiveData<List<Dictionary>>()
    val liveDataSearchWordsList get() = _liveDataSearchWordsList

    init {
        loadListWord()
    }

    private fun loadListWord() {
        fireBaseData.getDictionary(object : FireBaseData.ReadDataInterface {
            override fun readData(list: List<Dictionary>) {
                _liveDataWordsList.value = list.sortedWith(compareBy { it.word })
            }
        })
    }

    fun onSaveFavorite(favoriteWord: Dictionary) {
        if (liveDataFavorite.value == null) {
            _liveDataFavorite.value = ArrayList()
        }
        val favoriteList = liveDataFavorite.value!!
        if (favoriteList.contains(favoriteWord)) {
            favoriteList.remove(favoriteWord)
            favoriteWord.save = false
        } else {
            favoriteList.add(favoriteWord)
            favoriteWord.save = true
        }
        _liveDataFavorite.value = favoriteList
    }

    fun wordViewed(word: Dictionary) {
        var counter = word.countSee.toInt()
        counter++
        fireBaseData.wordViewed(word, counter)
    }

    fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<Dictionary>()
            if (constraint != null) {
                val filterPattern = constraint.toString().lowercase()
                for (item in liveDataWordsList.value!!) {
                    if (item.word.lowercase().startsWith(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            _liveDataSearchWordsList.value = results?.values as ArrayList<Dictionary>
        }
    }
}
