package com.lazarovstudio.mappers

import com.lazarovstudio.data.remote.vo.DictionaryVO
import com.lazarovstudio.data.remote.vo.FavoriteVO

fun DictionaryVO.toFavorite(): FavoriteVO {
    return FavoriteVO(
        countSee = countSee,
        description = description,
        isFavorite = isFavorite,
        id = id,
        uid = uid,
        word = word
    )
}