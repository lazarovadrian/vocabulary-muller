@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package com.lazarovstudio.vocabularymuller.data.remote.vo

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@IgnoreExtraProperties
@Serializable
@Entity (
    tableName = "dictionary",
// Ускоряет поиск
    indices = [
        Index("word")
    ]
)
data class DictionaryVO(
    @SerialName("id")
    @PrimaryKey val id: Int? = null,
    @SerialName("description")
    val description: String = "",
    @SerialName("word")
    val word: String = "",
    @SerialName("countSee")
    var countSee: String = "0",
    @SerialName("save")
    var save: Boolean = false,
)

