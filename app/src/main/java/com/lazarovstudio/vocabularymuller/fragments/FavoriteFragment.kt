package com.lazarovstudio.vocabularymuller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.adapter.AdapterFavoriteFragment
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO
import com.lazarovstudio.vocabularymuller.databinding.FragmentFavoriteBinding
import com.lazarovstudio.vocabularymuller.viewModel.FavoriteViewModel

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private var adapter = AdapterFavoriteFragment(this)
    private val model: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcListFavorite.layoutManager = LinearLayoutManager(context)
        binding.rcListFavorite.adapter = adapter
        binding.rcListFavorite.setHasFixedSize(true)
        binding.title.text = getString(R.string.notFavorite)
//TODO(убрать обращение в viewmodal)
//        model.getListFavorite()

        model.liveDataGetFavorites.observe(viewLifecycleOwner) {
            val count = it.size
            val message = if (count == 0) {
                getString(R.string.notFavorite)
            } else {
                getString(R.string.favorite, count)
            }
            binding.title.text = message
            adapter.submitList(it.toMutableList())
        }
    }

    fun removeFavoriteWord(wordCard: FavoriteVO) {
//TODO(убрать обращение в viewmodal)
        model.onSaveFavorite(wordCard)
//        model.getListFavorite()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}