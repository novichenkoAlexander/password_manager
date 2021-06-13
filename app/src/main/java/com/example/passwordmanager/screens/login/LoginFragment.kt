package com.example.passwordmanager.screens.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentLoginBinding
import com.example.passwordmanager.support.NavigationFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.passwordmanager.support.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : NavigationFragment<FragmentLoginBinding>(R.layout.fragment_login) {


    override val viewBinding: FragmentLoginBinding by viewBinding()

    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.statusLivaData.observe(this.viewLifecycleOwner) { status ->
            if (status.equals(LoginViewModel.SUCCESS)) {
                findNavController().navigateSafe(LoginFragmentDirections.toMainFragment())
            } else {
                viewBinding.tvWrongPassword.visibility = View.VISIBLE
            }
        }

        viewBinding.btnConfirm.setOnClickListener {
            viewModel.login(viewBinding.loginFragmentEtPassword.text.toString())
        }

        viewBinding.loginFragmentEtPassword.doAfterTextChanged {
            viewBinding.tvWrongPassword.visibility = View.INVISIBLE
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }
}