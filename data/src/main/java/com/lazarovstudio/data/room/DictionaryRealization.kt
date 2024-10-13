package com.lazarovstudio.data.room

import android.util.Log
import com.lazarovstudio.data.remote.vo.DictionaryVO
import com.lazarovstudio.data.remote.vo.FavoriteVO
import com.lazarovstudio.data.room.dao.DictionaryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DictionaryRealization(private val dictionaryDao: DictionaryDao) : DictionaryRepository {

    override fun allDictionaryAsFlow(): Flow<List<DictionaryVO>> {
        return dictionaryDao.getAllDictionaryAsFlow()
    }

    override suspend fun searchDictionary(query: String): List<DictionaryVO> {
        return dictionaryDao.getSearchedDictionary(query)
    }

    override fun getListFavorite(): Flow<List<FavoriteVO>> {
        return dictionaryDao.getListFavorite()
    }

    override suspend fun saveFavoriteWord(dataFavorite: FavoriteVO) {
        return withContext(Dispatchers.IO) {
            return@withContext dictionaryDao.saveFavoriteWord(dataFavorite)
        }
    }

    override suspend fun getFavorite(uid: Long?): FavoriteVO {
        return withContext(Dispatchers.IO) {
            return@withContext dictionaryDao.getFavoriteWord(uid)
        }
    }

    override suspend fun removeFavoriteWord(rvWord: FavoriteVO) {
        return withContext(Dispatchers.IO) {
            return@withContext dictionaryDao.removeFavoriteWord(rvWord)
        }
    }

    override suspend fun insertDictionary(data: List<DictionaryVO>) {
        return withContext(Dispatchers.IO) {
            return@withContext dictionaryDao.insertAll(data)
        }
    }

    override suspend fun getDictionaryCount(): Int {
        return withContext(Dispatchers.IO) {
            return@withContext dictionaryDao.getDictionaryCount()
        }
    }

    override suspend fun updateDictionary(uid: Long?, data: DictionaryVO) {
        return withContext(Dispatchers.IO) {
            return@withContext dictionaryDao.updateCountSee(uid, data.countSee)
        }
    }


    override suspend fun addOrRemoveFavorite(word: FavoriteVO, isFavorite: Boolean) {

        dictionaryDao.updateFavorite(word.uid, isFavorite)

        if (isFavorite) {
            val favoriteEntity = FavoriteVO(
                uid = word.uid,
                id = word.id,
                description = word.description,
                word = word.word,
                countSee = word.countSee,
                isFavorite = isFavorite,
            )
            dictionaryDao.saveFavoriteWord(favoriteEntity)
        } else {
            val favoriteEntity = dictionaryDao.getFavoriteWord(word.uid)
            dictionaryDao.removeFavoriteWord(favoriteEntity)
        }

    }
}