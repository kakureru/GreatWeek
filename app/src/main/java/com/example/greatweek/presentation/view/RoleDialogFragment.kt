package com.example.greatweek.presentation.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.greatweek.R
import com.example.greatweek.databinding.RoleDialogLayoutBinding


typealias RoleDialogListener = (requestKey: String, roleId: Int, roleName: String) -> Unit

class RoleDialogFragment : DialogFragment() {

    private val roleId: Int
        get() = requireArguments().getInt(ARG_ROLE_ID)

    private val roleName: String
        get() = requireArguments().getString(ARG_ROLE_NAME).toString()

    private val requestKey: String
        get() = requireArguments().getString(ARG_REQUEST_KEY)!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = RoleDialogLayoutBinding.inflate(layoutInflater)
        dialogBinding.roleEditText.setText(roleName)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.confirmButton.setOnClickListener {
            val enteredText = dialogBinding.roleEditText.text.toString()
            if (enteredText.isBlank()) {
                dialogBinding.roleEditText.error = getString(R.string.empty_value)
                return@setOnClickListener
            }
            parentFragmentManager.setFragmentResult(
                requestKey, bundleOf(
                    KEY_ROLE_ID_RESPONSE to roleId,
                    KEY_ROLE_NAME_RESPONSE to enteredText
                )
            )
            dismiss()
        }

        dialogBinding.dismissButton.setOnClickListener {
            dismiss()
        }

        dialog.setOnDismissListener { hideKeyboard(dialogBinding.roleEditText) }

        dialog.setOnShowListener {
            dialogBinding.roleEditText.requestFocus()
            showKeyboard(dialogBinding.roleEditText)
        }

        return dialog
    }

    private fun showKeyboard(view: View) {
        view.post {
            getInputMethodManager(view).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        val context = view.context
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    companion object {
        @JvmStatic
        private val TAG = RoleDialogFragment::class.java.simpleName

        @JvmStatic
        private val KEY_ROLE_NAME_RESPONSE = "KEY_ROLE_NAME_RESPONSE"

        @JvmStatic
        private val KEY_ROLE_ID_RESPONSE = "KEY_ROLE_ID_RESPONSE"

        @JvmStatic
        private val ARG_ROLE_NAME = "ARG_ROLE_NAME"

        @JvmStatic
        private val ARG_ROLE_ID = "ARG_ROLE_ID"

        @JvmStatic
        private val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"

        fun show(
            manager: FragmentManager,
            roleId: Int = -1,
            roleName: String = "",
            requestKey: String
        ) {
            val dialogFragment = RoleDialogFragment()
            dialogFragment.arguments = bundleOf(
                ARG_ROLE_ID to roleId,
                ARG_ROLE_NAME to roleName,
                ARG_REQUEST_KEY to requestKey
            )
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            requestKey: String,
            listener: RoleDialogListener
        ) {
            manager.setFragmentResultListener(
                requestKey,
                lifecycleOwner,
                FragmentResultListener { key, result ->
                    listener.invoke(
                        key,
                        result.getInt(KEY_ROLE_ID_RESPONSE),
                        result.getString(KEY_ROLE_NAME_RESPONSE).toString()
                    )
                })
        }

    }
}