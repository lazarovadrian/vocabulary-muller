package com.lazarovstudio.vocabularymuller.fragments

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazarovstudio.vocabularymuller.R
import com.lazarovstudio.vocabularymuller.adapter.AdapterAlphabetChar
import com.lazarovstudio.vocabularymuller.databinding.RcFragmentAlphabetCharBinding

class ListAlphabetChar : Fragment() {
    private var _binding: RcFragmentAlphabetCharBinding? = null
    private val binding get() = _binding!!
    private var adapter = AdapterAlphabetChar()
    private var isLinearLayoutManager = true

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}