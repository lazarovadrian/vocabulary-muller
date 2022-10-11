package com.lazarovstudio.vocabularymuller.model

import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@IgnoreExtraProperties
@Serializable
data class Dictionary(
    val id: Int? = null,
    @SerialName("description")
    val description: String = "",
    @SerialName("word")
    val word: String = "",
    @SerialName("countSee")
    var countSee: String = "0",
    @SerialName("save")
    var save: Boolean = false,
)