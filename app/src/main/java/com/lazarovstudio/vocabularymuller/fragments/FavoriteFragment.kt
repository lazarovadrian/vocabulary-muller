package com.lazarovstudio.vocabularymuller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lazarovstudio.vocabularymuller.adapter.AdapterFavoriteFragment
import com.lazarovstudio.vocabularymuller.databinding.FragmentFavoriteBinding
import com.lazarovstudio.vocabularymuller.viewModel.MainViewModel

class FavoriteFragment : Fragment() {
    private lateinit var _binding: FragmentFavoriteBinding
    private val binding get() = _binding
    private lateinit var rcFavoriteFragment: RecyclerView
    private var adapter = AdapterFavoriteFragment()
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcFavoriteFragment = binding.rcListFavorite
        rcFavoriteFragment.layoutManager = LinearLayoutManager(context)
        rcFavoriteFragment.adapter = adapter
        rcFavoriteFragment.setHasFixedSize(true)

        model.liveDataFavorite.observe(viewLifecycleOwner) { wordCard ->
            adapter.submitList(wordCard)
        }
    }

    companion object {
        fun favoriteInstance() = FavoriteFragment()
    }
}