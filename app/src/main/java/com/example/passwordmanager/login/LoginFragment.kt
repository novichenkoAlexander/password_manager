package com.example.passwordmanager.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.R
import com.example.passwordmanager.support.NavigationFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.passwordmanager.databinding.FragmentLoginBinding
import com.example.passwordmanager.support.navigateSafe

class LoginFragment : NavigationFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override val viewBinding: FragmentLoginBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.loginFragmentEtPassword.onDone { submitForm() }

    }

    private fun submitForm() {
        findNavController().navigateSafe(LoginFragmentDirections.toMainFragment())
    }

    private fun EditText.onDone(callback: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                callback.invoke()
                true
            } else {
                false
            }
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }
}