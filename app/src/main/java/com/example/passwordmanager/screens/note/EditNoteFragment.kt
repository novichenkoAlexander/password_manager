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
import com.example.passwordmanager.dialogs.DeleteDialogFragment
import com.example.passwordmanager.models.Note
import com.example.passwordmanager.support.setVerticalMargin

class EditNoteFragment : NavigationFragment<FragmentEditNoteBinding>(R.layout.fragment_edit_note) {

    override val viewBinding: FragmentEditNoteBinding by viewBinding()

    private val viewModel: EditNoteViewModel by viewModel()

    private val args: EditNoteFragmentArgs by navArgs()

    private lateinit var user: EditText
    private lateinit var password: EditText
    private lateinit var site: EditText
    private lateinit var toolbarTitle: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = viewBinding.etUserName
        password = viewBinding.etPassword
        site = viewBinding.etSiteUrl
        toolbarTitle = viewBinding.tvToolbarTitle

        var note = setDataToFields(user, password, site, toolbarTitle)

        viewBinding.ivCursorBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.tvEdit.setOnClickListener {
            note = Note(
                id = note.id,
                login = user.text.toString(),
                password = password.text.toString(),
                siteUrl = site.text.toString(),
                color = note.color
            )
            setViewsAvailability(true)
            setVisibilityToDoneAndCancel(View.VISIBLE)
            setVisibilityToEditAndBack(View.GONE)
        }

        viewBinding.tvDone.setOnClickListener {
            // Update note
            viewModel.updateNote(
                Note(
                    note.id,
                    login = user.text.toString(),
                    password = password.text.toString(),
                    siteUrl = site.text.toString(),
                    color = note.color
                )
            )

            setViewsAvailability(false)
            setVisibilityToDoneAndCancel(View.GONE)
            setVisibilityToEditAndBack(View.VISIBLE)
        }

        viewBinding.tvCancel.setOnClickListener {

            //Cancel changes
            cancelChanges(note)
            setVisibilityToDoneAndCancel(View.GONE)
            setVisibilityToEditAndBack(View.VISIBLE)
            setViewsAvailability(false)
        }

        viewBinding.tvDeleteNote.setOnClickListener {
            @Suppress("DEPRECATION")
            fragmentManager?.let { it1 ->
                DeleteDialogFragment(deleteCallback = { deleteNoteWithUndo(note) }).show(it1,
                    DeleteDialogFragment.DIALOG_TAG)
            }
        }
    }

    private fun cancelChanges(note: Note) {
        user.setText(note.login)
        password.setText(note.password)
        site.setText(note.siteUrl)
    }

    private fun deleteNoteWithUndo(note: Note) {
        note.deleted = true
        viewModel.updateNote(note)
        findNavController().popBackStack()
    }

    private fun setDataToFields(
        user: EditText,
        password: EditText,
        site: EditText,
        toolbarTitle: TextView,
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

    @Suppress("DEPRECATION")
    private fun setViewsAvailability(enable: Boolean) {
        viewBinding.etUserName.isEnabled = enable
        viewBinding.etPassword.isEnabled = enable
        viewBinding.etSiteUrl.isEnabled = enable
        if (enable) {
            viewBinding.etUserName.setTextColor(resources.getColor(R.color.blue))
            viewBinding.etPassword.setTextColor(resources.getColor(R.color.blue))
            viewBinding.etSiteUrl.setTextColor(resources.getColor(R.color.blue))
        } else {
            viewBinding.etUserName.setTextColor(resources.getColor(R.color.main_text_color))
            viewBinding.etPassword.setTextColor(resources.getColor(R.color.main_text_color))
            viewBinding.etSiteUrl.setTextColor(resources.getColor(R.color.main_text_color))
        }
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