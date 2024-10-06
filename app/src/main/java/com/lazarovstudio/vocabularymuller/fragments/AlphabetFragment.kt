package com.lazarovstudio.vocabularymuller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazarovstudio.vocabularymuller.adapter.AdapterAlphabetFragment
import com.lazarovstudio.vocabularymuller.adapter.AdapterCallback
import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.databinding.RcFragmentAlphabetBinding
import com.lazarovstudio.vocabularymuller.extension.openFragment
import com.lazarovstudio.vocabularymuller.viewModel.MainViewModel
import java.util.Locale

//TODO: Добавить поиск по слову
//TODO: Оптимизировать поиск

class AlphabetFragment : Fragment() {
    private var _binding: RcFragmentAlphabetBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterAlphabetFragment
    private val model: MainViewModel by viewModels()
//    private val args: AlphabetFragmentArgs by navArgs()

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

//        setupSearchView()
    }

    private fun setupSearchView() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    model.liveDataWordsList.observe(viewLifecycleOwner) { wordList ->
                        val filteredList = wordList.filter {
                            it.word.lowercase(Locale.getDefault())
                                .startsWith(query.lowercase(Locale.getDefault()))
                        }
                        adapter.submitList(filteredList)
                        binding.rcFragmentAlphabet.scrollToPosition(0)
                    }
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    model.liveDataWordsList.observe(viewLifecycleOwner) { wordList ->
                        val filteredList = wordList.filter {
                            it.word.lowercase(Locale.getDefault())
                                .startsWith(query.lowercase(Locale.getDefault()))
                        }
                        adapter.submitList(filteredList)
                        binding.rcFragmentAlphabet.scrollToPosition(0)
                    }
                }
                return true
            }
        })
//        searchWord(args.letter)
    }

//    private fun searchWord(char: String) {
//
//    }

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