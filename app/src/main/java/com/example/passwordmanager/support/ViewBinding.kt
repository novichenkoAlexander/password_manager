package com.example.passwordmanager.support

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KCallable

interface ViewBindable<T : ViewBinding> {
    val viewBinding: T
}

inline fun <reified T : ViewBinding> ViewGroup.viewBinding(method: KCallable<*>): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) {
        method.call(LayoutInflater.from(context), this) as T
    }
