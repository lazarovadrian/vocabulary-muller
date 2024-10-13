package com.lazarovstudio.data.room

import com.lazarovstudio.data.remote.vo.DictionaryVO
import com.lazarovstudio.data.remote.vo.FavoriteVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface DictionaryRepository {
    fun allDictionaryAsFlow(): Flow<List<DictionaryVO>>
    suspend fun insertDictionary(data: List<DictionaryVO>)
    suspend fun getDictionaryCount(): Int
    suspend fun updateDictionary(uid: Long?, data: DictionaryVO)
    suspend fun searchDictionary(query: String): List<DictionaryVO>

    suspend fun saveFavoriteWord(dataFavorite: FavoriteVO)
    fun getListFavorite(): Flow<List<FavoriteVO>>
    suspend fun getFavorite(uid: Long?): FavoriteVO
    suspend fun removeFavoriteWord(rvWord: FavoriteVO)
    suspend fun addOrRemoveFavorite(word: FavoriteVO, isFavorite: Boolean)
}
