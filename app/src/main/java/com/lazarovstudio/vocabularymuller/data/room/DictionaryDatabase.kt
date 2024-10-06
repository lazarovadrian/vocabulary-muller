package com.lazarovstudio.vocabularymuller.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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

@Database(
    entities = [DictionaryVO::class, FavoriteVO::class],
    version = 6,
)
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
        ).addMigrations(MIGRATION_5_6).build()
    }

    val dictionaryRealization: DictionaryRepository by lazy {
        DictionaryRealization(appDatabase.dictionaryDao())
    }
}


val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Создаем новую таблицу с правильной структурой
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS dictionary_new (
                description TEXT NOT NULL,
                uid INTEGER PRIMARY KEY AUTOINCREMENT,
                id INTEGER,
                word TEXT NOT NULL,
                countSee TEXT NOT NULL,
                isFavorite INTEGER NOT NULL
            )
        """
        )
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS favorite_words_new (
                description TEXT NOT NULL,
                uid INTEGER PRIMARY KEY AUTOINCREMENT,
                id INTEGER,
                word TEXT NOT NULL,
                countSee TEXT NOT NULL,
                isFavorite INTEGER NOT NULL,
                FOREIGN KEY(uid) REFERENCES dictionary(uid) ON DELETE CASCADE ON UPDATE CASCADE
            )
        """
        )
        // Копируем данные из старой таблицы в новую
        db.execSQL(
            """
            INSERT INTO dictionary_new (description, id, word, countSee, isFavorite)
            SELECT description, id, word, countSee, save AS isFavorite
            FROM dictionary
        """
        )
        db.execSQL(
            """
            INSERT INTO favorite_words_new (description, id, word, countSee, isFavorite)
            SELECT description, id, word, countSee, save AS isFavorite
            FROM favorite_words
        """
        )
        // Удаляем старую таблицу
        db.execSQL("DROP TABLE dictionary")
        db.execSQL("DROP TABLE favorite_words")
        // Переименовываем новую таблицу в оригинальное имя
        db.execSQL("ALTER TABLE dictionary_new RENAME TO dictionary")
        // Переименовываем новую таблицу в оригинальное имя
        db.execSQL("ALTER TABLE favorite_words_new RENAME TO favorite_words")
        // Добавляем индекс, если он ожидался
        db.execSQL("CREATE INDEX IF NOT EXISTS index_dictionary_word ON dictionary (word)")
        // Добавляем индекс, если он ожидался
    }
}
