package com.lazarovstudio.vocabularymuller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO
import com.lazarovstudio.vocabularymuller.databinding.FragmentDetailWordBinding
import com.lazarovstudio.vocabularymuller.viewModel.FavoriteViewModel

class DetailWordFragment : Fragment() {
    private var _binding: FragmentDetailWordBinding? = null
    private val binding get() = _binding!!
    private val model: FavoriteViewModel by viewModels()

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
                it.toInt(),
                frDetailWord[2].toString(),
                frDetailWord[1].toString(),
                frDetailWord[3].toString(),
                frDetailWord[4].toBoolean()
            )
        }

        if (detailWord != null) {
            binding.word.text = detailWord.word
            binding.desc.text = detailWord.description
            binding.countSee.text = detailWord.countSee
            binding.idCard.text = detailWord.id.toString()
            isFavorite(detailWord)
        }

        binding.favorite.setOnClickListener {
            if (detailWord != null) {
                val favoriteItem = FavoriteVO(
                    detailWord.id,
                    detailWord.description,
                    detailWord.word,
                    detailWord.countSee,
                    detailWord.save
                )
                model.onSaveFavorite(favoriteItem)

                isFavorite(detailWord)
            }
        }
    }

    private fun isFavorite(detailWord: DictionaryVO) {
        if (detailWord.save) {
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