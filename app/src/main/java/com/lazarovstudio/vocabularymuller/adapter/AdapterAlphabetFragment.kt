package com.lazarovstudio.vocabularymuller.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.databinding.ListAlphabetBinding
import com.lazarovstudio.vocabularymuller.fragments.alphabetFragment.AlphabetFragment
import com.lazarovstudio.vocabularymuller.model.Dictionary

class AdapterAlphabetFragment(private val wordCard: AlphabetFragment) :
    ListAdapter<Dictionary, AdapterAlphabetFragment.AlphabetHolder>(Comparator()) {

    class AlphabetHolder(private val binding: ListAlphabetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(
            item: Dictionary,
            wordCard: AlphabetFragment,
        ) = with(binding) {
            binding.word.text = item.word
            binding.descriptions.text = item.description
            binding.countViewed.text = item.countSee

            binding.card.setOnClickListener {
                wordCard.showDetailFragment(item)
            }

            binding.saveFavorite.setOnClickListener {
                wordCard.onSaveFavoriteClicked(item)
                binding.saveFavorite.setImageResource(R.drawable.favorite_active)
            }

            if (item.save) {
                binding.saveFavorite.setImageResource(R.drawable.favorite_active)
            } else {
                binding.saveFavorite.setImageResource(R.drawable.favorite_no_active)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetHolder {
        return AlphabetHolder(
            ListAlphabetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlphabetHolder, position: Int) {
        holder.setData(getItem(position), wordCard)
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