package com.lazarovstudio.vocabularymuller.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO
import com.lazarovstudio.vocabularymuller.databinding.ListAlphabetBinding
import com.lazarovstudio.vocabularymuller.mappers.toFavorite

class AdapterAlphabetFragment(
    private val onFavoriteClick: (isFavorite: Boolean, favoriteWord: FavoriteVO) -> Unit
) :
    ListAdapter<DictionaryVO, AdapterAlphabetFragment.AlphabetHolder>(Comparator.DiffCallback),
    View.OnClickListener {

    private var wordCard: AdapterCallback<DictionaryVO>? = null

    fun attachCallback(callback: AdapterCallback<DictionaryVO>) {
        this.wordCard = callback
    }

    inner class AlphabetHolder(val binding: ListAlphabetBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetHolder {

        val binding = ListAlphabetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        binding.card.setOnClickListener(this)
        binding.saveFavorite.setOnClickListener(this)

        return AlphabetHolder(binding)
    }

    override fun onBindViewHolder(holder: AlphabetHolder, position: Int) {
        val itemWord = getItem(position)

        with(holder.binding) {
            word.text = itemWord.word
            descriptions.text = itemWord.description
            countViewed.text = itemWord.countSee

            card.tag = itemWord.id
            saveFavorite.tag = itemWord.uid

            Log.d("DATA_ADAPTER", itemWord.id.toString())

            if (itemWord.isFavorite) {
                saveFavorite.setImageResource(R.drawable.favorite_active)
            } else {
                saveFavorite.setImageResource(R.drawable.favorite_no_active)
            }

            card.setOnClickListener {
                val word = getItem(holder.bindingAdapterPosition)
                wordCard?.showDetailFragment(word)
            }

            saveFavorite.setOnClickListener {
                val word =
                    getItem(currentList.indexOfFirst { it.uid == (saveFavorite.tag as Long) })
                word.isFavorite = !word.isFavorite
                if (word.isFavorite) {
                    saveFavorite.setImageResource(R.drawable.favorite_active)
                } else {
                    saveFavorite.setImageResource(R.drawable.favorite_no_active)
                }
                onFavoriteClick(word.isFavorite, word.toFavorite())
            }

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.card -> {
                val word = getItem(currentList.indexOfFirst { it.id == (v.tag as Long) })
                wordCard?.showDetailFragment(word)
            }
        }
    }

    data class Comparator(val id: Int, val name: String) {
        companion object {
            val DiffCallback = object : DiffUtil.ItemCallback<DictionaryVO>() {
                override fun areItemsTheSame(oldItem: DictionaryVO, newItem: DictionaryVO) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: DictionaryVO, newItem: DictionaryVO) =
                    oldItem == newItem

            }
        }
    }
}
