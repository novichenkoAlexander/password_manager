package com.example.passwordmanager.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.passwordmanager.databinding.FragmentCreatePasswordBinding
import com.example.passwordmanager.support.NavigationFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.passwordmanager.support.navigateSafe

class CreatePasswordFragment :
    NavigationFragment<FragmentCreatePasswordBinding>(R.layout.fragment_create_password) {

    override val viewBinding: FragmentCreatePasswordBinding by viewBinding()

    private val viewModel: CreatePasswordViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.apply {
            alpha = 0f
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.statusLiveData.observe(this.viewLifecycleOwner) { status ->
            val error = CreatePasswordViewModel.ERROR_CREATING_PASSWORD
            val success = CreatePasswordViewModel.PASSWORD_CREATED_SUCCESSFUL
            when (status) {
                error -> viewBinding.tvErrorMessage.text = error
                success -> Toast.makeText(
                    context,
                    status,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        viewModel.passwordCreated.observe(this.viewLifecycleOwner) { passwordCreated ->
            if (passwordCreated) {
                findNavController().navigateSafe(CreatePasswordFragmentDirections.toLoginFragment())
            } else {
                viewBinding.root.alpha = 1f
            }
        }

        viewBinding.btnCreatePassword.setOnClickListener {
            viewModel.createPassword(viewBinding.etCreatePassword.text.toString())
        }

        viewBinding.etCreatePassword.doAfterTextChanged {
            viewBinding.tvErrorMessage.text = ""
        }

    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }
}