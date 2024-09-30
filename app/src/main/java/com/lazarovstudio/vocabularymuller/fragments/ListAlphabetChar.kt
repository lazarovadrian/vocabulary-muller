package com.lazarovstudio.vocabularymuller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.adapter.AdapterAlphabetChar
import com.lazarovstudio.vocabularymuller.databinding.RcFragmentAlphabetCharBinding
import com.lazarovstudio.vocabularymuller.viewModel.FavoriteViewModel
import com.lazarovstudio.vocabularymuller.viewModel.MainViewModel

class ListAlphabetChar : Fragment() {
    private var _binding: RcFragmentAlphabetCharBinding? = null
    private val binding get() = _binding!!
    private var adapter = AdapterAlphabetChar()
    private var isLinearLayoutManager = true
    private val model: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RcFragmentAlphabetCharBinding.inflate(inflater, container, false)
        getList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcAlphabetChar
        chooseLayout()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_alphabet_fragment, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_switch_layout -> {
                        isLinearLayoutManager = !isLinearLayoutManager
                        chooseLayout()
                        setIcon(menuItem)
                        return true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun getList() {
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

    private fun chooseLayout() {
        if (isLinearLayoutManager) {
            binding.rcAlphabetChar.layoutManager = GridLayoutManager(context, 4)
        } else {
            binding.rcAlphabetChar.layoutManager = LinearLayoutManager(context)
        }
        binding.rcAlphabetChar.adapter = adapter
        binding.rcAlphabetChar.setHasFixedSize(true)
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return
        menuItem.icon = if (isLinearLayoutManager)
            ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_layout)
        else
            ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_linear_layout)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}