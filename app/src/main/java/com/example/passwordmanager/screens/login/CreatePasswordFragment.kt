package com.example.passwordmanager.screens.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentCreatePasswordBinding
import com.example.passwordmanager.support.NavigationFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.passwordmanager.support.navigateSafe

class CreatePasswordFragment :
    NavigationFragment<FragmentCreatePasswordBinding>(R.layout.fragment_create_password) {

    override val viewBinding: FragmentCreatePasswordBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnCreatePassword.setOnClickListener {
            findNavController().navigateSafe(CreatePasswordFragmentDirections.toLoginFragment())
        }

    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }
}