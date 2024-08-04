package com.lazarovstudio.vocabularymuller.data.room

import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO

interface DictionaryRepository {
    suspend fun allDictionary(): List<DictionaryVO>
    suspend fun insertDictionary(data: List<DictionaryVO>, onSuccess:() -> Unit)
    suspend fun getDictionaryCount(): Int
    suspend fun saveFavoriteWord(dataFavorite: FavoriteVO, onSuccess:() -> Unit)
    suspend fun getListFavorite():List<FavoriteVO>
    suspend fun removeFavoriteWord(rvWord: FavoriteVO)
}