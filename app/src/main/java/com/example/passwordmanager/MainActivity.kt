package com.example.passwordmanager

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.passwordmanager.databinding.ActivityMainBinding
import com.example.passwordmanager.support.SupportActivityInset
import com.example.passwordmanager.support.setWindowTransparency

class MainActivity : SupportActivityInset<ActivityMainBinding>() {

    private val navHostFragment by lazy { (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment) }

    override lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setWindowTransparency(this)
    }

    //  hide keyboard when touch the screen
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun getActiveFragment(): Fragment? {
        return navHostFragment.childFragmentManager.fragments[0]
    }

}