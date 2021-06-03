package com.example.passwordmanager.screens.note

import android.os.Bundle
import android.view.View
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentNoteBinding
import com.example.passwordmanager.support.NavigationFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.passwordmanager.support.setVerticalMargin
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoteFragment : NavigationFragment<FragmentNoteBinding>(R.layout.fragment_note) {

    override val viewBinding: FragmentNoteBinding by viewBinding()

    private val viewModel: NoteViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setVerticalMargin(marginTop = top)
    }


}