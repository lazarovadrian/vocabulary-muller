package com.lazarovstudio.vocabularymuller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lazarovstudio.vocabularymuller.databinding.FragmentDetailWordBinding

class DetailWordFragment : Fragment() {
    private var _binding: FragmentDetailWordBinding? = null
    private val binding get() = _binding!!
    private val itemArray = ArrayList<String>()

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

        val frDetailWord = arguments?.getStringArray("detailInfoWord")
        if (frDetailWord != null) {
            for (item in frDetailWord) {
                itemArray.add(item)
            }
        }
        binding.idCard.text = itemArray[0]
        binding.word.text = itemArray[1]
        binding.desc.text = itemArray[2]
        binding.countSee.text = itemArray[3]
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