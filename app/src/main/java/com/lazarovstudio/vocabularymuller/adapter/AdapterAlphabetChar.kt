package com.lazarovstudio.vocabularymuller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        private val btnChar: Button = binding.btnChar

        fun setData(item: Char) {
            btnChar.text = item.toString()
            btnChar.setOnClickListener {
                val action = ListAlphabetCharDirections
                    .actionListAlphabetCharToMainSwitchLayout(letter = btnChar.text.toString())
                view.findNavController().navigate(action)
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