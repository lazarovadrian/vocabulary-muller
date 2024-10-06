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
    indices = [
        Index("word")
    ]
)
data class DictionaryVO(
    @SerialName("countSee") var countSee: String = "0",
    @SerialName("description") val description: String = "",
    @SerialName("save") var isFavorite: Boolean = false,
    @SerialName("id") val id: Long? = null,
    @PrimaryKey(autoGenerate = true) val uid: Long? = null,
    @SerialName("word") val word: String = "",
)