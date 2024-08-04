package com.lazarovstudio.vocabularymuller.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO
import com.lazarovstudio.vocabularymuller.data.room.dao.DictionaryDao

/*
    Данный класс помечен аннотацией @Database, в которой необходимо обязательно описать два свойства: entities и version.
    Первое свойство принимает все сущности, которые были описаны выше, а второе свойство задаёт версию базы данных.
    Версия базы данных используется для контроля базы данных, ее данных и т.д.
    Зачастую это используется при миграции базы данных с одной версии на другую
    В самом классе создан абстрактный метод, который возвращает dao-интерфейс.
 */

@Database(entities = [DictionaryVO::class, FavoriteVO::class], version = 5)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
}

object Dependencies {
    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: DictionaryDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            DictionaryDatabase::class.java,
            "dictionary.db"
        ).build()
    }

    val dictionaryRealization: DictionaryRepository by lazy {
        DictionaryRealization(appDatabase.dictionaryDao())
    }
}