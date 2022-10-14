package com.lazarovstudio.vocabularymuller.fragments.alphabetFragment

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.adapter.AdapterAlphabetFragment
import com.lazarovstudio.vocabularymuller.databinding.RcFragmentAlphabetBinding
import com.lazarovstudio.vocabularymuller.extension.openFragment
import com.lazarovstudio.vocabularymuller.fragments.DetailWordFragment
import com.lazarovstudio.vocabularymuller.fragments.FavoriteFragment
import com.lazarovstudio.vocabularymuller.model.Dictionary
import com.lazarovstudio.vocabularymuller.viewModel.MainViewModel

class AlphabetFragment : Fragment() {
    private lateinit var _binding: RcFragmentAlphabetBinding
    private val binding get() = _binding
    private lateinit var rcAlphabetFragment: RecyclerView
    private var adapter = AdapterAlphabetFragment(this)
    private lateinit var rcSearch: SearchView
    private lateinit var rcProgress: ProgressBar

    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = RcFragmentAlphabetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcAlphabetFragment = binding.rcFragmentAlphabet
        rcSearch = binding.search
        rcProgress = binding.progress
        rcAlphabetFragment.layoutManager = LinearLayoutManager(context)
        rcAlphabetFragment.adapter = adapter
        rcAlphabetFragment.setHasFixedSize(true)

//        val filterChar = arguments?.getChar("filterChar")
        rcSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        model.loadListWord()
        model.liveDataWordsList.observe(viewLifecycleOwner) { item ->
            rcProgress.visibility = View.GONE
            adapter.updateAdapter(item)
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
        fun getInstance(args: Bundle?): AlphabetFragment {
            val alphabetFragment = AlphabetFragment()
            alphabetFragment.arguments = args
            return alphabetFragment
        }
    }

//    fun searchWord(searchWord: List<Dictionary>){
//        rcSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                adapter.filter.filter(newText)
////                adapter.searchFilter(newText)
//                return false
//            }
//
//        })
//    }

//поиск, startsWith - начинает с первого символа
//    @SuppressLint("NotifyDataSetChanged")
//    fun filterSearch(searchWord: String?, listWord: ArrayList<Dictionary>){
//        val filterName: ArrayList<Dictionary> = ArrayList()
//        alphabetList.clear()
//        for(s : Dictionary in listWord){
//            if (searchWord != null) {
//                if(s.word?.lowercase()?.startsWith(searchWord.lowercase()) == true){
//                    filterName.add(s)
//                }
//            }
//        }
//    if(alphabetList.size == 0) filterName.add(R.string.not_word as Dictionary)
//    filterName
//    }

//    fun progress(){
//
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding
    }
}