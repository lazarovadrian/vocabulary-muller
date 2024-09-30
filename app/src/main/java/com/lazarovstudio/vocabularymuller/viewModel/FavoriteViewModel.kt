package com.lazarovstudio.vocabularymuller.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO
import com.lazarovstudio.vocabularymuller.data.room.Dependencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {
    private val repository = Dependencies.dictionaryRealization
    private val _liveDataGetFavorites: LiveData<List<FavoriteVO>> = repository.getListFavorite().asLiveData()
    val liveDataGetFavorites: LiveData<List<FavoriteVO>> = _liveDataGetFavorites

    fun onFavoriteClick(isFavorite: Boolean,favoriteWord: FavoriteVO){
        viewModelScope.launch(Dispatchers.IO ) {
            if(isFavorite){
                repository.saveFavoriteWord(favoriteWord)
            }else{
                val rmFavorite = repository.getFavorite(favoriteWord.uid!!)
                repository.removeFavoriteWord(rmFavorite)
            }
        }
    }
}