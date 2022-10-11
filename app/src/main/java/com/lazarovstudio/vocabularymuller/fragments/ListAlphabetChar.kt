package com.lazarovstudio.vocabularymuller.fragments

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.adapter.AdapterAlphabetChar
import com.lazarovstudio.vocabularymuller.databinding.RcFragmentAlphabetCharBinding
import com.lazarovstudio.vocabularymuller.extension.openFragment
import com.lazarovstudio.vocabularymuller.fragments.alphabetFragment.AlphabetFragment

class ListAlphabetChar : Fragment(){
    private lateinit var _binding: RcFragmentAlphabetCharBinding
    private val binding get() = _binding
    private lateinit var rcFragmentAlphabetChar: RecyclerView
    private var adapter = AdapterAlphabetChar(this)
    private var isLinearLayoutManager = true

    private fun chooseLayout() {
        if (isLinearLayoutManager) {
            rcFragmentAlphabetChar.layoutManager = GridLayoutManager(context, 4)
        } else {
            rcFragmentAlphabetChar.layoutManager = LinearLayoutManager(context)
        }
        rcFragmentAlphabetChar.adapter = adapter
        rcFragmentAlphabetChar.setHasFixedSize(true)
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return
        menuItem.icon = if (isLinearLayoutManager)
//            context?.let { ContextCompat.getDrawable(it, R.drawable.ic_grid_layout) }
            ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_layout)
        else
            context?.let { ContextCompat.getDrawable(it, R.drawable.ic_linear_layout) }
    }

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

        rcFragmentAlphabetChar = binding.rcAlphabetChar
        setHasOptionsMenu(true)//требуется для отображения меню
        chooseLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
        val layoutButton = menu.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                isLinearLayoutManager = !isLinearLayoutManager
                chooseLayout()
                setIcon(item)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //передаем букву в фрагмен AlphabetFragment
    fun filterChar(item: Char) {
        val bundle = Bundle()
        bundle.putChar("filterChar", item)
        val alphabetFragment = AlphabetFragment.getInstance(args = bundle)
        openFragment(alphabetFragment)
    }

    companion object {
        fun newInstance() = ListAlphabetChar()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding
    }

}