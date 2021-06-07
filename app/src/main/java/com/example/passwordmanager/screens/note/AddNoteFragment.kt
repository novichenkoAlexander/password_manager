package com.example.passwordmanager.screens.note

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentAddNoteBinding
import com.example.passwordmanager.support.NavigationFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.passwordmanager.support.setVerticalMargin

class AddNoteFragment : NavigationFragment<FragmentAddNoteBinding>(R.layout.fragment_add_note) {


    override val viewBinding: FragmentAddNoteBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveNote()

    }

    private fun saveNote() {
        val buttonDone = viewBinding.fragmentAddNoteTvDone
        val fieldUsername = viewBinding.fragmentAddNoteUserName
        val fieldWebsite = viewBinding.fragmentAddNoteWebsite
        val buttonCancel = viewBinding.fragmentAddNoteTvCancel

        //TODO: make livedata to check fields for emptyness
        if (fieldWebsite.text.isNotBlank() && fieldUsername.text.isNotBlank()) {
            buttonDone.apply {
                isEnabled = true
                setTextColor(resources.getColor(R.color.teal_200))
                setOnClickListener {

                }
            }
            //TODO: viewModel.saveNote()
        }


        buttonCancel.setOnClickListener {
            findNavController().popBackStack()
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