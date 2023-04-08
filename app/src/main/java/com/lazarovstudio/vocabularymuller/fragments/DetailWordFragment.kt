package com.lazarovstudio.vocabularymuller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.databinding.FragmentDetailWordBinding
import com.lazarovstudio.vocabularymuller.model.Dictionary
import com.lazarovstudio.vocabularymuller.viewModel.MainViewModel

class DetailWordFragment : Fragment() {
    private var _binding: FragmentDetailWordBinding? = null
    private val binding get() = _binding!!
    private val model: MainViewModel by activityViewModels()

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

        val detailWord = Dictionary(
            frDetailWord?.get(0)?.toInt(),
            frDetailWord?.get(2).toString(),
            frDetailWord?.get(1).toString(),
            frDetailWord?.get(3).toString(),
            frDetailWord?.get(4).toBoolean()
        )

        binding.word.text = detailWord.word
        binding.desc.text = detailWord.description
        binding.countSee.text = detailWord.countSee
        binding.idCard.text = detailWord.id.toString()

        binding.favorite.setOnClickListener {
            model.onSaveFavorite(detailWord)
            binding.favorite.setImageResource(R.drawable.favorite_active)
        }

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