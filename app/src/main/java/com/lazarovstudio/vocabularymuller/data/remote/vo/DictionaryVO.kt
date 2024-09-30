@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package com.lazarovstudio.vocabularymuller.data.remote.vo

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Entity (
    tableName = "dictionary",
// Ускоряет поиск
    indices = [
        Index("word")
    ]
)
data class DictionaryVO(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("description") val description: String = "",
    @SerialName("word") val word: String = "",
    @SerialName("countSee") var countSee: String = "0",
    @SerialName("save") var isFavorite: Boolean = false,
)