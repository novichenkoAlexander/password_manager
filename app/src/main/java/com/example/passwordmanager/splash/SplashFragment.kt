package com.example.passwordmanager.splash

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentSplashBinding
import com.example.passwordmanager.support.NavigationFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.passwordmanager.support.navigateSafe

class SplashFragment : NavigationFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }

    override val viewBinding: FragmentSplashBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.root.postDelayed(
            { findNavController().navigateSafe(SplashFragmentDirections.toCreatePasswordFragment()) },
            600
        )
    }

}