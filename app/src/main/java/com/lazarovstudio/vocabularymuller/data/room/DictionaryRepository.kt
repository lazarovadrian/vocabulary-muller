package com.lazarovstudio.vocabularymuller.data.room

import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    suspend fun allDictionary(): List<DictionaryVO>
    suspend fun insertDictionary(data: List<DictionaryVO>, onSuccess:() -> Unit)
    suspend fun getDictionaryCount(): Int
    suspend fun saveFavoriteWord(dataFavorite: FavoriteVO)
    fun getListFavorite(): Flow<List<FavoriteVO>>
    suspend fun getFavorite(uid: Int): FavoriteVO
    suspend fun removeFavoriteWord(rvWord: FavoriteVO)
}