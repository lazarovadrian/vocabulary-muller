package com.lazarovstudio.vocabularymuller.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.databinding.ListAlphabetBinding
import com.lazarovstudio.vocabularymuller.fragments.FavoriteFragment
import com.lazarovstudio.vocabularymuller.model.Dictionary

class AdapterFavoriteFragment(private val wordCard: FavoriteFragment) :
    ListAdapter<Dictionary, AdapterFavoriteFragment.FavoriteHolder>(Comparator()) {

    class FavoriteHolder(private val binding: ListAlphabetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun itemCardWord(
            favoriteCard: Dictionary,
            wordCard: FavoriteFragment
        ) = with(binding) {
            word.text = favoriteCard.word
            descriptions.text = favoriteCard.description
            countViewed.text = favoriteCard.countSee

            if (favoriteCard.save) binding.saveFavorite.setImageResource(R.drawable.favorite_active)

            binding.saveFavorite.setOnClickListener {
                wordCard.removeFavoriteWord(favoriteCard)
                favoriteCard.save = false
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

    class Comparator : DiffUtil.ItemCallback<Dictionary>() {
        override fun areItemsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
            return oldItem == newItem
        }

    }
}