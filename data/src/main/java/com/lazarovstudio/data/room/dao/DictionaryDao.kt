package com.lazarovstudio.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lazarovstudio.data.remote.vo.DictionaryVO
import com.lazarovstudio.data.remote.vo.FavoriteVO
import kotlinx.coroutines.flow.Flow

/*
    Данный интерфейс будет взаимодействовать с базой данных.
*/

@Dao
interface DictionaryDao {

    @Query("SELECT * FROM dictionary ORDER BY word ASC")
    fun getAllDictionaryAsFlow(): Flow<List<DictionaryVO>>

    @Query("SELECT * FROM dictionary WHERE word LIKE :query || '%'  ORDER BY word ASC")
    suspend fun getSearchedDictionary(query: String): List<DictionaryVO>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = DictionaryVO::class)
    suspend fun insertAll(dictionary: List<DictionaryVO>)

    @Query("UPDATE dictionary SET countSee = :countSee WHERE uid = :uid")
    suspend fun updateCountSee(uid: Long?, countSee: String)

    @Query("SELECT COUNT(*) FROM dictionary")
    suspend fun getDictionaryCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteWord(dictionaryFavorites: FavoriteVO)

    @Query("SELECT * FROM favorite_words ORDER BY word ASC")
    fun getListFavorite(): Flow<List<FavoriteVO>>

    @Query("UPDATE dictionary SET isFavorite = :isFavorite WHERE uid = :uid")
    suspend fun updateFavorite(uid: Long?, isFavorite: Boolean)

    @Delete
    suspend fun removeFavoriteWord(rvWord: FavoriteVO)

    @Query("SELECT * FROM favorite_words WHERE uid = :uid")
    suspend fun getFavoriteWord(uid: Long?): FavoriteVO
}