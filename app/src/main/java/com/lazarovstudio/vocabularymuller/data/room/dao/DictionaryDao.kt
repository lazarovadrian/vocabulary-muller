package com.lazarovstudio.vocabularymuller.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO

/*
Данный интерфейс будет взаимодействовать с базой данных с помощью специальных методов.
*/

@Dao
interface DictionaryDao {

    @Query("SELECT * FROM dictionary ORDER BY word ASC")
    suspend fun getAllDictionary(): List<DictionaryVO>

    @Insert(entity = DictionaryVO::class)
    suspend fun insertAll(dictionary: List<DictionaryVO>)

    @Query("SELECT COUNT(*) FROM dictionary")
    suspend fun getDictionaryCount(): Int

    @Insert
    suspend fun saveFavoriteWord(dictionaryFavorites: FavoriteVO)

    @Query("SELECT * FROM favorite_words ORDER BY word ASC")
    suspend fun getListFavorite(): List<FavoriteVO>

    @Delete
    suspend fun removeFavoriteWord(rvWord: FavoriteVO)
}