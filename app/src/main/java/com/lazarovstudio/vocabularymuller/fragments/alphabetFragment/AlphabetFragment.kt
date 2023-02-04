package com.lazarovstudio.vocabularymuller.fragments.alphabetFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazarovstudio.vocabularymuller.adapter.AdapterAlphabetFragment
import com.lazarovstudio.vocabularymuller.databinding.RcFragmentAlphabetBinding
import com.lazarovstudio.vocabularymuller.extension.openFragment
import com.lazarovstudio.vocabularymuller.fragments.DetailWordFragment
import com.lazarovstudio.vocabularymuller.model.Dictionary
import com.lazarovstudio.vocabularymuller.viewModel.MainViewModel

class AlphabetFragment : Fragment() {
    private var _binding: RcFragmentAlphabetBinding? = null
    private val binding get() = _binding!!
    private val adapter = AdapterAlphabetFragment(this)
    private val model: MainViewModel by activityViewModels()
    private lateinit var latterId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            latterId = it.getString(LETTER).toString()
        }
    }

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

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.search.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                model.getFilter().filter(newText!!)
                return false
            }
        })
//выполняем фильтр по букве
        if (model.liveDataWordsList.value != null) {
            model.getFilter().filter(latterId)
        }
//получаем полный список при запуске
        model.liveDataWordsList.observe(viewLifecycleOwner) { item ->
            if (item != null) {
                binding.progress.visibility = View.GONE
                binding.txtInfo.visibility = View.GONE
                adapter.submitList(item)
            } else {
                binding.progress.visibility = View.VISIBLE
                binding.txtInfo.visibility = View.VISIBLE
            }
        }
//поиск
        model.liveDataSearchWordsList.observe(viewLifecycleOwner) { item ->
            binding.progress.visibility = View.GONE
            binding.txtInfo.visibility = View.GONE
            adapter.submitList(item.toMutableList())
            binding.rcFragmentAlphabet.scrollToPosition(0)
        }
    }

    fun showDetailFragment(item: Dictionary) {
        val bundle = Bundle()
        bundle.putStringArrayList(
            "detailInfoWord",
            arrayListOf(
                item.id.toString(),
                item.word,
                item.description,
                item.countSee,
                item.save.toString()
            )
        )
        model.wordViewed(item)
        openFragment(DetailWordFragment.getInstance(bundle))
    }

    fun onSaveFavoriteClicked(saveWord: Dictionary) {
        model.onSaveFavorite(saveWord)
    }

    companion object {
        const val LETTER = "letter"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}