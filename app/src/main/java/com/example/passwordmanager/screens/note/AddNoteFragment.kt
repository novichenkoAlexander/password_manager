package com.example.passwordmanager.screens.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentAddNoteBinding
import com.example.passwordmanager.support.NavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.passwordmanager.models.Note
import com.example.passwordmanager.support.setVerticalMargin

class AddNoteFragment : NavigationFragment<FragmentAddNoteBinding>(R.layout.fragment_add_note) {


    override val viewBinding: FragmentAddNoteBinding by viewBinding()

    private val viewModel: AddNoteViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val website = viewBinding.fragmentAddNoteWebsite
        val user = viewBinding.fragmentAddNoteUserName
        val password = viewBinding.fragmentAddNotePassword
        val cancel = viewBinding.fragmentAddNoteTvCancel
        val done = viewBinding.fragmentAddNoteTvDone

        viewModel.textEnteredLiveData.observe(this.viewLifecycleOwner) { notEmpty ->
            if (notEmpty) {
                with(done) {
                    isEnabled = true
                    @Suppress("DEPRECATION")
                    setTextColor(resources.getColor(R.color.main_text_color))
                }
            } else {
                with(done) {
                    isEnabled = false
                    setTextColor(resources.getColor(R.color.note_item_color))
                }
            }
        }

        website.doAfterTextChanged {
            checkFields(website, user, password)
        }

        user.doAfterTextChanged {
            checkFields(website, user, password)
        }

        password.doAfterTextChanged {
            checkFields(website, user, password)
        }

        done.setOnClickListener {
            viewModel.saveNote(
                Note(
                    login = user.text.toString(),
                    password = password.text.toString(),
                    siteUrl = website.text.toString()
                )
            )
            findNavController().popBackStack()
        }

        cancel.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun checkFields(website: EditText, user: EditText, password: EditText) {
        with(viewModel) {
            checkForEmptyFields(
                website.text.toString(),
                user.text.toString(),
                password.text.toString()
            )
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.fragmentAddNoteToolbar.setVerticalMargin(marginTop = top)
    }

    override val backPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

}