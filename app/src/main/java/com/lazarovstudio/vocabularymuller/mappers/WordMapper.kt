package com.lazarovstudio.vocabularymuller.mappers

import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO

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