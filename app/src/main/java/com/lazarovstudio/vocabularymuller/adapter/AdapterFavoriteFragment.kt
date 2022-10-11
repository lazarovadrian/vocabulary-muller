package com.lazarovstudio.vocabularymuller.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lazarovstudio.vocabularymuller.databinding.ListAlphabetBinding
import com.lazarovstudio.vocabularymuller.model.Dictionary

class AdapterFavoriteFragment :
    ListAdapter<Dictionary, AdapterFavoriteFragment.FavoriteHolder>(Comparator()) {

    class FavoriteHolder(private val binding: ListAlphabetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun itemCardWord(wordCard: Dictionary) = with(binding) {
            word.text = wordCard.word
            descriptions.text = wordCard.description
            countViewed.text = wordCard.countSee
        }
    }

    class Comparator : DiffUtil.ItemCallback<Dictionary>() {
        override fun areItemsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
            return oldItem.word == newItem.word
        }

        override fun areContentsTheSame(oldItem: Dictionary, newItem: Dictionary) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(
            ListAlphabetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.itemCardWord(getItem(position))
    }
}