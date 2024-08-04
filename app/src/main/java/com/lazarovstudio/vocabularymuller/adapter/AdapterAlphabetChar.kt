package com.lazarovstudio.vocabularymuller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.databinding.ListAlphabetCharBinding
import com.lazarovstudio.vocabularymuller.fragments.ListAlphabetCharDirections

class AdapterAlphabetChar :
    RecyclerView.Adapter<AdapterAlphabetChar.AdapterViewHolder>() {
    private val listChars = ('A').rangeTo('Z').toList()

    class AdapterViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListAlphabetCharBinding.bind(view)

        fun setData(item: Char) {
            binding.btnChar.text = item.toString()
            binding.btnChar.setOnClickListener {
                view.findNavController().navigate(
                    ListAlphabetCharDirections.actionListAlphabetCharToAlphabetSearchListFragment(
                        letter = item.toString()
                    )
                )
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
        holder.setData(listChars[position])

    override fun getItemCount() = listChars.size

}