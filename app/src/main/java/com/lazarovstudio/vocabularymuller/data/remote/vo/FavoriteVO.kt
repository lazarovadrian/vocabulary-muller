package com.lazarovstudio.vocabularymuller.data.remote.vo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@IgnoreExtraProperties
@Serializable
@Entity(
    tableName = "favorite_words",
    foreignKeys = [ForeignKey(
        entity = DictionaryVO::class,
        parentColumns = ["id"],
        childColumns = ["id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class FavoriteVO(
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