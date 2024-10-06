package com.lazarovstudio.vocabularymuller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.databinding.FragmentDetailWordBinding
import com.lazarovstudio.vocabularymuller.mappers.toFavorite
import com.lazarovstudio.vocabularymuller.viewModel.MainViewModel

//TODO: создать viewModel получать данные из базы
class DetailWordFragment : Fragment() {
    private var _binding: FragmentDetailWordBinding? = null
    private val binding get() = _binding!!
    private val model: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val frDetailWord = arguments?.getStringArrayList("detailInfoWord")

        val detailWord = frDetailWord?.get(0)?.let {
            DictionaryVO(
                countSee = frDetailWord[4],
                description = frDetailWord[2],
                isFavorite = frDetailWord[5].toBoolean(),
                id = frDetailWord[1].toLong(),
            )
        }

        if (detailWord != null) {
            binding.idCard.text = getString(R.string.idWord, detailWord.id)
            binding.desc.text = detailWord.description
            binding.word.text = detailWord.word
            binding.countSee.text = detailWord.countSee
            isFavorite(detailWord.isFavorite)
        }

        binding.favorite.setOnClickListener {
            if (detailWord != null) {
                detailWord.isFavorite = !detailWord.isFavorite
                model.onFavoriteClick(isFavorite = detailWord.isFavorite, favoriteWord = detailWord.toFavorite())
                isFavorite(detailWord.isFavorite)
            }
        }
    }

    private fun isFavorite(detailWord: Boolean) {
        if (detailWord) {
            binding.favorite.setImageResource(R.drawable.favorite_active)
        } else {
            binding.favorite.setImageResource(R.drawable.favorite_no_active)
        }
    }

    companion object {
        fun getInstance(args: Bundle?): DetailWordFragment {
            val detailWordFragment = DetailWordFragment()
            detailWordFragment.arguments = args
            return detailWordFragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}