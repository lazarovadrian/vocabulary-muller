package com.lazarovstudio.vocabularymuller.fragments.alphabetFragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.adapter.AdapterAlphabetFragment
import com.lazarovstudio.vocabularymuller.databinding.RcFragmentAlphabetBinding
import com.lazarovstudio.vocabularymuller.extension.openFragment
import com.lazarovstudio.vocabularymuller.fragments.DetailWordFragment
import com.lazarovstudio.vocabularymuller.fragments.FavoriteFragment
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
        arguments.let {
            if (it != null) {
                latterId = it.getString(LETTER).toString()
            }
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
        binding.rcFragmentAlphabet.adapter = adapter
        binding.rcFragmentAlphabet.setHasFixedSize(true)

        Log.d("CHARS", latterId)
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                model.filter(newText)
                return false
            }
        })
//загружаем список слов из базы
        model.loadListWord()
        if (model.liveDataWordsList.value != null){
            model.filter(latterId)
        }else{
          Log.d("CHARS", "NOT LOADING")
        }
//        model.liveDataWordsList.observe(viewLifecycleOwner) { item ->
//            binding.progress.visibility = View.GONE
//            adapter.submitList(item)
//        }
//выполняется при поиске
        model.liveDataFilterWordsList.observe(viewLifecycleOwner) { item ->
            if (item.isEmpty()) {
                binding.progress.visibility = View.VISIBLE
                binding.txtInfo.visibility = View.VISIBLE
            } else {
                binding.progress.visibility = View.GONE
                binding.txtInfo.visibility = View.GONE

            }
            adapter.submitList(item)
        }
        //новый API для вывода меню в actionBar
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_alphabet_fragment, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_favorite_layout -> {
                        openFragment(FavoriteFragment.favoriteInstance())
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    fun showDetailFragment(item: Dictionary) {
        val bundle = Bundle()
        bundle.putStringArray(
            "detailInfoWord",
            arrayOf(item.id.toString(), item.word, item.description, item.countSee)
        )
        model.wordViewed(item)
        openFragment(DetailWordFragment.getInstance(args = bundle))
    }

    fun saveFavoriteWord(saveWord: List<Dictionary>) {
        model.saveFavorite(saveWord)
    }

    companion object {
        const val LETTER = "letter"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}