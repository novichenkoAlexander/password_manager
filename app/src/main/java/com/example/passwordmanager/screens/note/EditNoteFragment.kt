package com.example.passwordmanager.screens.note

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentEditNoteBinding
import com.example.passwordmanager.support.NavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.passwordmanager.support.setVerticalMargin

class EditNoteFragment : NavigationFragment<FragmentEditNoteBinding>(R.layout.fragment_edit_note) {

    override val viewBinding: FragmentEditNoteBinding by viewBinding()

    private val viewModel: NoteViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.ivCursorBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.tvEdit.setOnClickListener {
            viewBinding.etUserName.isEnabled = true
            viewBinding.etPassword.isEnabled = true
            viewBinding.etSiteUrl.isEnabled = true

            viewBinding.tvCancel.visibility = View.VISIBLE
            viewBinding.tvDone.visibility = View.VISIBLE

            viewBinding.ivCursorBack.visibility = View.GONE
            viewBinding.tvEdit.visibility = View.GONE
        }

        viewBinding.tvDone.setOnClickListener {
            //TODO: save data in fields
            setViewDisabled()

            setVisibilityGone()
            setVisibilityVisible()
        }

        viewBinding.tvCancel.setOnClickListener {
            //TODO: cancel all changes
            setVisibilityGone()
            setVisibilityVisible()

            setViewDisabled()
        }

    }

    private fun setVisibilityGone() {
        viewBinding.tvDone.visibility = View.GONE
        viewBinding.tvCancel.visibility = View.GONE
    }

    private fun setVisibilityVisible() {
        viewBinding.ivCursorBack.visibility = View.VISIBLE
        viewBinding.tvEdit.visibility = View.VISIBLE
    }

    private fun setViewDisabled() {
        viewBinding.etUserName.isEnabled = false
        viewBinding.etPassword.isEnabled = false
        viewBinding.etSiteUrl.isEnabled = false
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setVerticalMargin(marginTop = top)
    }

    override val backPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
}