package com.example.passwordmanager.screens.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentMainBinding
import com.example.passwordmanager.support.NavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.passwordmanager.models.Note
import com.example.passwordmanager.support.navigateSafe
import com.example.passwordmanager.support.setVerticalMargin

class MainFragment : NavigationFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val viewBinding: FragmentMainBinding by viewBinding()

    private val viewModel: MainViewModel by viewModel()

    private val adapter = ItemRecyclerViewAdapter(
        onClick = ::onItemClick,
        emptySearchListCallback = ::setRecyclerViewVisibilityGone,
    )

    private fun onItemClick(note: Note) {
        findNavController().navigateSafe(MainFragmentDirections.toEditNoteFragment(note))
    }

    private fun setRecyclerViewVisibilityGone(flag: Boolean) {
        if (flag) {
            viewBinding.recyclerView.visibility = View.INVISIBLE
            viewBinding.tvNoResult.visibility = View.VISIBLE
            viewBinding.tvNoResult.text = "No results \n for \" ${viewBinding.searchView.query}\""
        } else {
            viewBinding.recyclerView.visibility = View.VISIBLE
            viewBinding.tvNoResult.visibility = View.INVISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.recyclerView.adapter = adapter

        viewModel.notesLiveDate.observe(this.viewLifecycleOwner) {
            adapter.setNewList(it.toMutableList())
        }


        viewBinding.fabAdd.setOnClickListener {
            findNavController().navigateSafe(MainFragmentDirections.toAddNoteFragment())
        }

        viewBinding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarMain.setVerticalMargin(marginTop = top)
        viewBinding.recyclerView.setPadding(0, 0, 0, bottom)
    }

    override val backPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }
}