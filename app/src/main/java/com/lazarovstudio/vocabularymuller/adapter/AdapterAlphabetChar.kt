package com.lazarovstudio.vocabularymuller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.databinding.ListAlphabetCharBinding
import com.lazarovstudio.vocabularymuller.fragments.ListAlphabetChar

class AdapterAlphabetChar(private var selectChar: ListAlphabetChar) :
    RecyclerView.Adapter<AdapterAlphabetChar.AdapterViewHolder>() {
    private val listChars = ('A').rangeTo('Z').toList()

    class AdapterViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ListAlphabetCharBinding.bind(item)
        private val char = binding.btnChar

        fun setData(item: Char, selectChar: ListAlphabetChar) {
            char.text = item.toString()
            char.setOnClickListener {
                selectChar.filterChar(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_alphabet_char, parent, false)
        return AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) =
        holder.setData(listChars[position], selectChar)

    override fun getItemCount() = listChars.size

}