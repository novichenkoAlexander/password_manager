package com.example.passwordmanager.screens.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        onClick = ::onItemClick
    )

    private fun onItemClick(item: Note) {
//        findNavController().navigateSafe(MainFragmentDirections.toAddNoteFragment())
        //TODO: to note edit fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.recyclerView.adapter = adapter

        viewBinding.fabAdd.setOnClickListener {
            findNavController().navigateSafe(MainFragmentDirections.toAddNoteFragment())
        }

    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarMain.setVerticalMargin(marginTop = top)
        viewBinding.recyclerView.setPadding(0, 0, 0, bottom)
    }
}