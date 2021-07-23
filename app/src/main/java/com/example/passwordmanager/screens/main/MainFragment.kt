package com.example.passwordmanager.screens.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller

class MainFragment : NavigationFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val viewBinding: FragmentMainBinding by viewBinding()

    private val viewModel: MainViewModel by viewModel()

    private lateinit var adapter: ItemRecyclerViewAdapter

    private fun onItemClick(note: Note) {
        findNavController().navigateSafe(MainFragmentDirections.toEditNoteFragment(note))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.notesLiveData.observe(this.viewLifecycleOwner) {
            
            adapter = ItemRecyclerViewAdapter(
                onClick = ::onItemClick,
                emptySearchListCallback = ::setRecyclerViewVisibilityGone,
                it.toMutableList()
            )
            viewBinding.recyclerView.adapter = adapter
        }

        viewModel.markedAsDeletedNoteLiveData.observe(this.viewLifecycleOwner) { note ->
            if (note != null) {
                makeSnackBar(note)
            }
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

    @Suppress("DEPRECATION")
    private fun deleteNoteByPosition(pos: Int) {
        fragmentManager?.let {
            DeleteDialogFragment(deleteCallback = { viewModel.deleteWithUndo(pos) }).show(it,
                DeleteDialogFragment.DIALOG_TAG)
        }
    }

    @Suppress("DEPRECATION")
    private fun makeSnackBar(note: Note) {
        val snackBar = Snackbar
            .make(viewBinding.recyclerView, "Item removed", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                note.deleted = false
                viewModel.updateNote(note)
            }
            .setActionTextColor(resources.getColor(R.color.teal_200))
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onShown(transientBottomBar: Snackbar?) {
                    super.onShown(transientBottomBar)
                }

                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                        viewModel.clearTable()
                    }
                }
            })
        snackBar.show()
    }


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarMain.setVerticalMargin(marginTop = top)
        viewBinding.recyclerView.setPadding(0, 0, 0, bottom)
    }
}
