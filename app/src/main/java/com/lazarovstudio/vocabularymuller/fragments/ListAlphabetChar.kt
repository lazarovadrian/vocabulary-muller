package com.lazarovstudio.vocabularymuller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.lazarovstudio.vocabularymuller.adapter.AdapterAlphabetChar
import com.lazarovstudio.vocabularymuller.databinding.RcFragmentAlphabetCharBinding
import com.lazarovstudio.vocabularymuller.viewModel.MainViewModel

class ListAlphabetChar : Fragment() {
    private var _binding: RcFragmentAlphabetCharBinding? = null
    private val binding get() = _binding!!
    private var adapter = AdapterAlphabetChar()
    private val model: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RcFragmentAlphabetCharBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcAlphabetChar.layoutManager = GridLayoutManager(context, 4)
        binding.rcAlphabetChar.adapter = adapter
        binding.rcAlphabetChar.setHasFixedSize(true)

        model.liveDataWordsList.observe(viewLifecycleOwner) { item ->
            progress(
                showProgress = item.isEmpty(),
                showInfo = item.isEmpty(),
                showAlphabet = item.isNotEmpty()
            )
        }
    }

    private fun progress(showProgress: Boolean, showInfo: Boolean, showAlphabet: Boolean) {
        binding.progress.visibility = if (showProgress) View.VISIBLE else View.GONE
        binding.txtInfo.visibility = if (showInfo) View.VISIBLE else View.GONE
        binding.rcAlphabetChar.visibility = if (showAlphabet) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}