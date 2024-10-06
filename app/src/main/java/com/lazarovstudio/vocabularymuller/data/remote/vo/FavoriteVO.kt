package com.lazarovstudio.vocabularymuller.data.remote.vo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Entity(
    tableName = "favorite_words",
    foreignKeys = [ForeignKey(
        entity = DictionaryVO::class,
        parentColumns = ["uid"],
        childColumns = ["uid"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class FavoriteVO(
    @SerialName("countSee") var countSee: String = "0",
    @SerialName("description") val description: String = "",
    @SerialName("save") var isFavorite: Boolean = false,
    @SerialName("id") val id: Long? = null,
    @PrimaryKey(autoGenerate = true) val uid: Long? = null,
    @SerialName("word") val word: String = "",
)