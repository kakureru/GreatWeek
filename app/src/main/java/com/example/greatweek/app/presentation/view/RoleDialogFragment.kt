package com.example.greatweek.app.presentation.view

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
import com.example.greatweek.R
import com.example.greatweek.app.Constants
import com.example.greatweek.app.presentation.viewmodel.RoleDialogFragmentViewModel
import com.example.greatweek.databinding.RoleDialogLayoutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoleDialogFragment : DialogFragment() {

    private val viewModel by viewModel<RoleDialogFragmentViewModel>()

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
            when (requestKey) {
                Constants.KEY_RENAME_ROLE_REQUEST_KEY -> viewModel.renameRole(oldName = roleName, newName = enteredText)
                Constants.KEY_ADD_ROLE_REQUEST_KEY -> viewModel.addRole(name = enteredText)
            }
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
        private val ARG_ROLE_NAME = "ARG_ROLE_NAME"

        @JvmStatic
        private val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"

        fun show(
            manager: FragmentManager,
            roleName: String = "",
            requestKey: String
        ) {
            val dialogFragment = RoleDialogFragment()
            dialogFragment.arguments = bundleOf(
                ARG_ROLE_NAME to roleName,
                ARG_REQUEST_KEY to requestKey
            )
            dialogFragment.show(manager, TAG)
        }
    }
}