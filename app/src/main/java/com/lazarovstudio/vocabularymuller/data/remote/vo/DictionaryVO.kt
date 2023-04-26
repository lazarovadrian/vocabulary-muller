@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package com.lazarovstudio.vocabularymuller.data.remote.vo

import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.serialization.*

@IgnoreExtraProperties
@Serializable
data class DictionaryVO(
    val id: Int = 0,
    @SerialName("description")
    val description: String = "",
    @SerialName("word")
    val word: String = "",
    @SerialName("countSee")
    var countSee: String = "0",
    @SerialName("save")
    var save: Boolean = false,
){}