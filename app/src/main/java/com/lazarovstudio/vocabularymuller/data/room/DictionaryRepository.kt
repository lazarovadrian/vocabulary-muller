package com.lazarovstudio.vocabularymuller.data.room

import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    fun allDictionaryAsFlow(): Flow<List<DictionaryVO>>
    suspend fun insertDictionary(data: List<DictionaryVO>)
    suspend fun getDictionaryCount(): Int
    suspend fun updateDictionary(uid: Long?, data: DictionaryVO)

    suspend fun saveFavoriteWord(dataFavorite: FavoriteVO)
    fun getListFavorite(): Flow<List<FavoriteVO>>
    suspend fun getFavorite(uid: Long?): FavoriteVO
    suspend fun removeFavoriteWord(rvWord: FavoriteVO)
    suspend fun addOrRemoveFavorite(word: FavoriteVO, isFavorite: Boolean)
}
