package com.lazarovstudio.vocabularymuller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazarovstudio.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.adapter.AdapterAlphabetFragment
import com.lazarovstudio.vocabularymuller.adapter.AdapterCallback
import com.lazarovstudio.vocabularymuller.databinding.RcFragmentAlphabetBinding
import com.lazarovstudio.vocabularymuller.extension.openFragment
import com.lazarovstudio.vocabularymuller.viewModel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//TODO: Добавить поиск по слову
//TODO: Оптимизировать поиск

class AlphabetFragment : Fragment() {
    private var _binding: RcFragmentAlphabetBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterAlphabetFragment
    private val model: MainViewModel by viewModels()
    private val args: AlphabetFragmentArgs by navArgs()

    private var searchWord: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = RcFragmentAlphabetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AdapterAlphabetFragment(model::onFavoriteClick)
        binding.rcFragmentAlphabet.layoutManager = LinearLayoutManager(context)
        binding.rcFragmentAlphabet.setHasFixedSize(true)
        binding.rcFragmentAlphabet.adapter = adapter

        model.liveDataWordsList.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
            progress(
                showProgress = items.isEmpty(),
                showInfo = items.isEmpty()
            )
        }

        adapter.attachCallback(object : AdapterCallback<DictionaryVO> {
            override fun showDetailFragment(model: DictionaryVO) {
                val bundle = Bundle()
                bundle.putStringArrayList(
                    "detailInfoWord",
                    arrayListOf(
                        model.uid.toString(),
                        model.id.toString(),
                        model.description,
                        model.word,
                        model.countSee,
                        model.isFavorite.toString()
                    )
                )
                wordViewed(model)
                openFragment(DetailWordFragment.getInstance(args = bundle))
            }
        })

        if (args.letter.isNotEmpty()) {
            viewLifecycleOwner.lifecycleScope.launch {
                setupSearchView()
                searchWord(args.letter)
            }
        }
    }

    private fun setupSearchView() {

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                searchWord?.cancel()
                query?.let {
                    searchWord = model.viewModelScope.launch {
                        delay(100L)
                        if (query.isNotEmpty()) {
                            model.searchWord(query)
                        }
                    }
                }
                return true
            }
        })
    }

    private suspend fun searchWord(char: String) {
        model.searchWord(char)
        model.searchWord.collect { items ->
            adapter.submitList(items)
            binding.rcFragmentAlphabet.scrollToPosition(0)
        }
    }

    private fun progress(showProgress: Boolean, showInfo: Boolean) {
        binding.progress.visibility = if (showProgress) View.VISIBLE else View.GONE
        binding.txtInfo.visibility = if (showInfo) View.VISIBLE else View.GONE
    }

    private fun wordViewed(word: DictionaryVO) {
        model.viewWord(word)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}