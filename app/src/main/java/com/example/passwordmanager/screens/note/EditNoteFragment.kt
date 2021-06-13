package com.example.passwordmanager.screens.note

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentEditNoteBinding
import com.example.passwordmanager.support.NavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.passwordmanager.models.Note
import com.example.passwordmanager.support.setVerticalMargin

class EditNoteFragment : NavigationFragment<FragmentEditNoteBinding>(R.layout.fragment_edit_note) {

    override val viewBinding: FragmentEditNoteBinding by viewBinding()

    private val viewModel: EditNoteViewModel by viewModel()

    private val args: EditNoteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = viewBinding.etUserName
        val password = viewBinding.etPassword
        val site = viewBinding.etSiteUrl
        val toolbarTitle = viewBinding.tvToolbarTitle

        val note = setDataToFields(user, password, site, toolbarTitle)

        viewBinding.ivCursorBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.tvEdit.setOnClickListener {
            setViewsAvailability(true)
            setVisibilityToDoneAndCancel(View.VISIBLE)
            setVisibilityToEditAndBack(View.GONE)
        }

        viewBinding.tvDone.setOnClickListener {
            // Update note
            val changedNote = Note(
                note.id,
                login = user.text.toString(),
                password = password.text.toString(),
                siteUrl = site.text.toString()
            )
            viewModel.updateNote(changedNote)

            setViewsAvailability(false)
            setVisibilityToDoneAndCancel(View.GONE)
            setVisibilityToEditAndBack(View.VISIBLE)
        }

        viewBinding.tvCancel.setOnClickListener {

            //Cancel changes
            setDataToFields(user, password, site, toolbarTitle)

            setVisibilityToDoneAndCancel(View.GONE)
            setVisibilityToEditAndBack(View.VISIBLE)
            setViewsAvailability(false)
        }

        viewBinding.tvDeleteNote.setOnClickListener {
            viewModel.deleteNote(note)
            findNavController().popBackStack()
        }

    }

    private fun setDataToFields(
        user: EditText,
        password: EditText,
        site: EditText,
        toolbarTitle: TextView
    ): Note {
        return args.note.apply {
            user.setText(this.login)
            password.setText(this.password)
            site.setText(this.siteUrl)
            toolbarTitle.text = this.siteUrl
        }
    }

    private fun setVisibilityToDoneAndCancel(visibility: Int) {
        viewBinding.tvDone.visibility = visibility
        viewBinding.tvCancel.visibility = visibility
    }

    private fun setVisibilityToEditAndBack(visibility: Int) {
        viewBinding.ivCursorBack.visibility = visibility
        viewBinding.tvEdit.visibility = visibility
    }

    private fun setViewsAvailability(enable: Boolean) {
        viewBinding.etUserName.isEnabled = enable
        viewBinding.etPassword.isEnabled = enable
        viewBinding.etSiteUrl.isEnabled = enable
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