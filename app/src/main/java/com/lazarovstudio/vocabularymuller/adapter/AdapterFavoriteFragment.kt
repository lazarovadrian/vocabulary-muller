package com.lazarovstudio.vocabularymuller.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.databinding.ListAlphabetBinding
import com.lazarovstudio.vocabularymuller.fragments.FavoriteFragment

class AdapterFavoriteFragment(private val wordCard: FavoriteFragment) :
    ListAdapter<DictionaryVO, AdapterFavoriteFragment.FavoriteHolder>(Comparator()) {

    class FavoriteHolder(private val binding: ListAlphabetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun itemCardWord(
            favoriteCard: DictionaryVO,
            wordCard: FavoriteFragment
        ) = with(binding) {
            word.text = favoriteCard.word
            descriptions.text = favoriteCard.description
            countViewed.text = favoriteCard.countSee

            if (favoriteCard.save){
                binding.saveFavorite.setImageResource(R.drawable.favorite_active)
            }else{
                wordCard.removeFavoriteWord(favoriteCard)
            }

            binding.saveFavorite.setOnClickListener {
                wordCard.removeFavoriteWord(favoriteCard)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(
            ListAlphabetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.itemCardWord(getItem(position), wordCard)
    }

    class Comparator : DiffUtil.ItemCallback<DictionaryVO>() {
        override fun areItemsTheSame(oldItem: DictionaryVO, newItem: DictionaryVO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DictionaryVO, newItem: DictionaryVO): Boolean {
            return oldItem == newItem
        }

    }
}