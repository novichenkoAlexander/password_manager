package com.example.passwordmanager.screens.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
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
                done.apply {
                    isEnabled = true
                    @Suppress("DEPRECATION")
                    setTextColor(resources.getColor(R.color.white))
                }
            } else {
                done.apply {
                    isEnabled = false
                    setTextColor(resources.getColor(R.color.note_item_color))
                }
            }
        }

        website.afterTextChanged {
            checkFields(website, user, password)
        }

        user.afterTextChanged {
            checkFields(website, user, password)
        }

        password.afterTextChanged {
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

    private fun EditText.afterTextChanged(onTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                onTextChanged.invoke(s.toString())
            }

        })
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