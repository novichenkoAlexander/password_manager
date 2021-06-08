package com.example.passwordmanager.screens.login

import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentLoginBinding
import com.example.passwordmanager.support.NavigationFragment
import by.kirich1409.viewbindingdelegate.viewBinding

class LoginFragment : NavigationFragment<FragmentLoginBinding>(R.layout.fragment_login) {


    override val viewBinding: FragmentLoginBinding by viewBinding()

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }
}