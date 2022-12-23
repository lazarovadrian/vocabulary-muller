package com.lazarovstudio.vocabularymuller.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lazarovstudio.vocabularymuller.databinding.ListAlphabetBinding
import com.lazarovstudio.vocabularymuller.fragments.alphabetFragment.AlphabetFragment
import com.lazarovstudio.vocabularymuller.model.Dictionary

private val listWord = ArrayList<Dictionary>()

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

            if (item.save) {
                binding.saveFavorite.setColorFilter(Color.GREEN)
            }

            binding.card.setOnClickListener {
                wordCard.showDetailFragment(item)
            }
            binding.saveFavorite.setOnClickListener {
                listWord.add(item)
                wordCard.saveFavoriteWord(listWord)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<Dictionary>() {
        override fun areItemsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
            return oldItem == newItem
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
}