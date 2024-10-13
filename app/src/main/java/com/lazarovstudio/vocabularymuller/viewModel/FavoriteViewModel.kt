package com.lazarovstudio.vocabularymuller.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lazarovstudio.data.remote.vo.FavoriteVO
import com.lazarovstudio.data.room.Dependencies.dictionaryRealization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {
    private val repository = dictionaryRealization
    private val _liveDataGetFavorites: LiveData<List<com.lazarovstudio.data.remote.vo.FavoriteVO>> =
        repository.getListFavorite().asLiveData()
    val liveDataGetFavorites: LiveData<List<com.lazarovstudio.data.remote.vo.FavoriteVO>> = _liveDataGetFavorites

    fun onFavoriteClick( isFavorite: Boolean, favoriteWord: com.lazarovstudio.data.remote.vo.FavoriteVO) {
        viewModelScope.launch(Dispatchers.IO) {
           repository.addOrRemoveFavorite(word = favoriteWord, isFavorite = isFavorite)
        }
    }
}