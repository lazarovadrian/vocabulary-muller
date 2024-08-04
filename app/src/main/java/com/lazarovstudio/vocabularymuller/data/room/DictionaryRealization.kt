package com.lazarovstudio.vocabularymuller.data.room

import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO
import com.lazarovstudio.vocabularymuller.data.room.dao.DictionaryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DictionaryRealization(private val dictionaryDao: DictionaryDao) : DictionaryRepository {

    override suspend fun allDictionary(): List<DictionaryVO> {
        return withContext(Dispatchers.IO) {
            return@withContext dictionaryDao.getAllDictionary()
        }
    }

    override suspend fun getListFavorite(): List<FavoriteVO> {
        return withContext(Dispatchers.IO) {
            return@withContext dictionaryDao.getListFavorite()
        }
    }

    override suspend fun removeFavoriteWord(rvWord: FavoriteVO) {
        return withContext(Dispatchers.IO){
            return@withContext dictionaryDao.removeFavoriteWord(rvWord)
        }
    }

    override suspend fun insertDictionary(
        data: List<DictionaryVO>,
        onSuccess: () -> Unit
    ) {
        return withContext(Dispatchers.IO) {
            return@withContext dictionaryDao.insertAll(data)
        }
    }

    override suspend fun getDictionaryCount(): Int {
        return withContext(Dispatchers.IO){
         return@withContext dictionaryDao.getDictionaryCount()
        }
    }

    override suspend fun saveFavoriteWord(
        dataFavorite: FavoriteVO,
        onSuccess: () -> Unit
    ) {
        return withContext(Dispatchers.IO) {
            return@withContext dictionaryDao.saveFavoriteWord(dataFavorite)
        }
    }

}