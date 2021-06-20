package com.example.passwordmanager.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.example.passwordmanager.R


class DeleteDialogFragment(
    private val deleteCallback: () -> Unit,
) : DialogFragment(), View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog_background)
        val view = inflater.inflate(R.layout.fragment_delete_item_dialog, container, false)
        view.findViewById<AppCompatButton>(R.id.btnCancel).setOnClickListener(this)
        view.findViewById<AppCompatButton>(R.id.btnDelete).setOnClickListener(this)
        return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onClick(v: View?) {
        when ((v as AppCompatButton).id) {
            DELETE -> {
                deleteCallback()
                dismiss()
            }
            CANCEL -> {
                dismiss()
            }
        }
    }

    companion object {
        const val DELETE = R.id.btnDelete
        const val CANCEL = R.id.btnCancel
        const val DIALOG_TAG = "DELETE DIALOG FRAGMENT"
    }

}