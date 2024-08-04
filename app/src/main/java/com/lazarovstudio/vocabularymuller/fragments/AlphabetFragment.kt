package com.lazarovstudio.vocabularymuller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazarovstudio.vocabularymuller.adapter.AdapterAlphabetFragment
import com.lazarovstudio.vocabularymuller.adapter.AdapterCallback
import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO
import com.lazarovstudio.vocabularymuller.data.remote.vo.FavoriteVO
import com.lazarovstudio.vocabularymuller.databinding.RcFragmentAlphabetBinding
import com.lazarovstudio.vocabularymuller.extension.openFragment
import com.lazarovstudio.vocabularymuller.viewModel.FavoriteViewModel
import com.lazarovstudio.vocabularymuller.viewModel.MainViewModel
import java.util.Locale

class AlphabetFragment : Fragment() {
    private var _binding: RcFragmentAlphabetBinding? = null
    private val binding get() = _binding!!
    private val adapter = AdapterAlphabetFragment()

    private val model: MainViewModel by viewModels()
    private val favoriteModel: FavoriteViewModel by viewModels()

    private val args: AlphabetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = RcFragmentAlphabetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcFragmentAlphabet.layoutManager = LinearLayoutManager(context)
        binding.rcFragmentAlphabet.setHasFixedSize(true)
        binding.rcFragmentAlphabet.adapter = adapter
        adapter.attachCallback(object : AdapterCallback<DictionaryVO> {

            override fun showDetailFragment(model: DictionaryVO) {
                val bundle = Bundle()
                bundle.putStringArrayList(
                    "detailInfoWord",
                    arrayListOf(
                        model.id.toString(),
                        model.word,
                        model.description,
                        model.countSee,
                        model.save.toString()
                    )
                )
                wordViewed(model)
                openFragment(DetailWordFragment.getInstance(args = bundle))
            }

            override fun saveFavorite(model: DictionaryVO) {
                onSaveFavoriteClicked(model)
            }
        })

        setupSearchView()
    }

    private fun setupSearchView() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchWord(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    progress(showProgress = query.isEmpty(), showInfo = query.isEmpty())
                    searchWord(query)
                }
                return true
            }
        })

        progress(showProgress = args.letter.isEmpty(), showInfo = args.letter.isEmpty())
        searchWord(args.letter)
    }

    fun searchWord(char: String) {
        model.liveDataWordsList.observe(viewLifecycleOwner) {
            val filteredList = it.filter {
                it.word.lowercase(Locale.getDefault()).startsWith(char, ignoreCase = true)
            }
            adapter.submitList(filteredList)
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

    private fun onSaveFavoriteClicked(saveWord: DictionaryVO) {
        val favoriteItem = FavoriteVO(
            saveWord.id,
            saveWord.description,
            saveWord.word,
            saveWord.countSee,
            saveWord.save
        )
        favoriteModel.onSaveFavorite(favoriteItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}