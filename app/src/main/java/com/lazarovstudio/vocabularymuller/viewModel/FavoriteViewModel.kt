package com.lazarovstudio.vocabularymuller.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO
import com.lazarovstudio.vocabularymuller.data.room.Dependencies.dictionaryRealization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {
    private val repository = dictionaryRealization
    private val _liveDataGetFavorites: LiveData<List<FavoriteVO>> =
        repository.getListFavorite().asLiveData()
    val liveDataGetFavorites: LiveData<List<FavoriteVO>> = _liveDataGetFavorites

    fun onFavoriteClick( isFavorite: Boolean, favoriteWord: FavoriteVO) {
        viewModelScope.launch(Dispatchers.IO) {
           repository.addOrRemoveFavorite(word = favoriteWord, isFavorite = isFavorite)
        }
    }
}