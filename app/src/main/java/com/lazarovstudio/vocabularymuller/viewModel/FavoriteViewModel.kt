package com.lazarovstudio.vocabularymuller.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO
import com.lazarovstudio.vocabularymuller.data.room.Dependencies
import kotlinx.coroutines.launch

class FavoriteViewModel: ViewModel() {

    private val _liveDataGetFavorites = MutableLiveData<List<FavoriteVO>>()
    val liveDataGetFavorites: LiveData<List<FavoriteVO>> = _liveDataGetFavorites

    fun onSaveFavorite(favoriteWord: FavoriteVO) {

        val favoriteList = _liveDataGetFavorites.value ?: mutableListOf()
        val favoriteSet = HashSet(favoriteList)

        if (favoriteSet.contains(favoriteWord)) {
            favoriteSet.remove(favoriteWord)
            favoriteWord.save = false
            viewModelScope.launch {
                try {
                    Dependencies.dictionaryRealization.removeFavoriteWord(favoriteWord)
                } catch (e: Exception) {
                    Log.e("DATA_ERROR_DELETE_FAVORITE_WORD", "An error occurred: ${e.message}")
                }
            }
        } else {
            favoriteSet.add(favoriteWord)
            favoriteWord.save = true
            viewModelScope.launch {
                try {
                    favoriteWord.let {
                        Dependencies.dictionaryRealization.saveFavoriteWord(it) {}
                    }
                } catch (e: Exception) {
                    Log.d("DATA_ERROR_FAVORITE", "${e.message}")
                }
            }
        }
    }

    fun getListFavorite() {
        viewModelScope.launch {
            try {
                _liveDataGetFavorites.postValue(Dependencies.dictionaryRealization.getListFavorite())
            } catch (e: Exception) {
                Log.e("DATA_ERROR_GET_FAVORITE", "An error occurred: ${e.message}")
            }
        }
    }

}