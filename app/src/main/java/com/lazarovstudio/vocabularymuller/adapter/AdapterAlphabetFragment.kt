package com.lazarovstudio.vocabularymuller.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.databinding.ListAlphabetBinding
import com.lazarovstudio.vocabularymuller.fragments.alphabetFragment.AlphabetFragment
import com.lazarovstudio.vocabularymuller.model.Dictionary
import java.util.*

private val listWord = ArrayList<Dictionary>()
class AdapterAlphabetFragment(
    private val wordCard: AlphabetFragment
) :
    RecyclerView.Adapter<AdapterAlphabetFragment.AdapterViewHolder>(),
    Filterable {
    private var countryList = ArrayList<Dictionary>()
    private var alphabetList = ArrayList<Dictionary>()

    init {
        alphabetList = countryList
    }

    class AdapterViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ListAlphabetBinding.bind(item)
        private val title = binding.word
        private val desc = binding.descriptions
        private val itemWord = binding.card
        private val viewed = binding.countViewed
        private val btnSaveFavorite = binding.saveFavorite

        fun setData(
            item: Dictionary,
            wordCard: AlphabetFragment,
        ) {
            title.text = item.word
            desc.text = item.description
            viewed.text = item.countSee

            itemWord.setOnClickListener {
                wordCard.showDetailFragment(item)
            }

            btnSaveFavorite.setOnClickListener {
                listWord.add(item)
                wordCard.saveFavoriteWord(listWord)
                btnSaveFavorite.setColorFilter(Color.GREEN)
            }
        }
    }

    // отвечает за представление, оборачивает его в холдер и возвращает результат,
    // наполняем list_alphabet и передает в AdapterViewHolder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_alphabet, parent, false)
        return AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) =
        holder.setData(alphabetList[position], wordCard)

    // возвращает количество элементов
    override fun getItemCount() = alphabetList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(newList: List<Dictionary>) {
        alphabetList.clear()
        alphabetList.addAll(newList)
        notifyDataSetChanged()
//        notifyItemChanged()
    }

    //поиск
    override fun getFilter(): Filter {
        return object : Filter() {
            //проверяет набран ли текст, если нет вернуть все элементы
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                alphabetList = if (charSearch.isEmpty()) {
//                    если countList(поле ввода поиск) == пустое, то отображается весь список
                    countryList
                } else {
                    val resultList = ArrayList<Dictionary>()
                    val searchWord = charSearch.lowercase(Locale.ROOT)
                    for (item in countryList) {
                        if (
                            item.word.startsWith(searchWord)
                            || item.description.startsWith(searchWord)
                        ) {
                            resultList.add(item)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = alphabetList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                alphabetList = results?.values as ArrayList<Dictionary>
                notifyDataSetChanged()
//                notifyItemChanged()
            }

        }
    }
}