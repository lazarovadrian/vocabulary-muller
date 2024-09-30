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
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("description") val description: String = "",
    @SerialName("word") val word: String = "",
    @SerialName("countSee") var countSee: String = "0",
    @SerialName("save") var isFavorite: Boolean = false,
)