package com.example.passwordmanager.screens.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentMainBinding
import com.example.passwordmanager.support.NavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.passwordmanager.dialogs.DeleteDialogFragment
import com.example.passwordmanager.models.Note
import com.example.passwordmanager.support.SwipeHelper
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

    private fun setRecyclerViewVisibilityGone(listIsEmpty: Boolean) {
        val text = viewBinding.searchView.query
        when (listIsEmpty) {
            true -> {
                if (text.isNotEmpty()) {
                    viewBinding.recyclerView.visibility = View.INVISIBLE
                    viewBinding.tvNoResult.visibility = View.VISIBLE
                    viewBinding.tvNoResult.text = "No results \n for \" ${text}\""
                } else {
                    viewBinding.tvNoResult.visibility = View.INVISIBLE
                }
            }
            false -> {
                viewBinding.recyclerView.visibility = View.VISIBLE
                viewBinding.tvNoResult.visibility = View.INVISIBLE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.recyclerView.adapter = adapter

        viewModel.notesLiveDate.observe(this.viewLifecycleOwner) {
            adapter.setNewList(ArrayList(it.toMutableList()))
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

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(viewBinding.recyclerView) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                return listOf(deleteButton(position))
            }
        })

        itemTouchHelper.attachToRecyclerView(viewBinding.recyclerView)

    }

    private fun deleteButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Delete",
            20.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    deleteNoteByPosition(position)
                }
            })
    }

    private fun deleteNoteByPosition(pos: Int) {
        fragmentManager?.let {
            DeleteDialogFragment(deleteCallback = { viewModel.deleteByPos(pos) }).show(it,
                DeleteDialogFragment.DIALOG_TAG)
        }

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