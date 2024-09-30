package com.lazarovstudio.vocabularymuller.mappers

import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO

fun DictionaryVO.toFavorite(): FavoriteVO {
    return FavoriteVO(
        uid = uid,
        id = id,
        description = description,
        word = word,
        countSee = countSee,
        isFavorite = isFavorite
    )
}