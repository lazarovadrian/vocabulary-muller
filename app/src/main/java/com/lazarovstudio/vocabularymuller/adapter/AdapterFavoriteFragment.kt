package com.lazarovstudio.vocabularymuller.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.data.remote.vo.FavoriteVO
import com.lazarovstudio.vocabularymuller.databinding.ListAlphabetBinding

class AdapterFavoriteFragment(
    private val onFavoriteClick: (isFavorite: Boolean, favoriteWord: com.lazarovstudio.data.remote.vo.FavoriteVO) -> Unit
) :
    ListAdapter<com.lazarovstudio.data.remote.vo.FavoriteVO, AdapterFavoriteFragment.FavoriteHolder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(
            ListAlphabetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class FavoriteHolder(var binding: ListAlphabetBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val wordItem = getItem(position)

        with(holder.binding) {
            word.text = wordItem.word
            descriptions.text = wordItem.description
            countViewed.text = wordItem.countSee
            saveFavorite.tag = wordItem.uid

            if (wordItem.isFavorite) {
                saveFavorite.setImageResource(R.drawable.favorite_active)
            } else {
                saveFavorite.setImageResource(R.drawable.favorite_no_active)
            }

            saveFavorite.setOnClickListener {
                val word =
                    getItem(currentList.indexOfFirst { it.uid == (saveFavorite.tag as Long) })

                word.isFavorite = false
                saveFavorite.setImageResource(R.drawable.favorite_no_active)
                onFavoriteClick(word.isFavorite, word)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<com.lazarovstudio.data.remote.vo.FavoriteVO>() {
        override fun areItemsTheSame(oldItem: com.lazarovstudio.data.remote.vo.FavoriteVO, newItem: com.lazarovstudio.data.remote.vo.FavoriteVO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: com.lazarovstudio.data.remote.vo.FavoriteVO, newItem: com.lazarovstudio.data.remote.vo.FavoriteVO): Boolean {
            return oldItem == newItem
        }

    }
}