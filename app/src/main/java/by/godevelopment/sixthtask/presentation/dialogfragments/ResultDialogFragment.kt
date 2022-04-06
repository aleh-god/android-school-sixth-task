package by.godevelopment.sixthtask.presentation.dialogfragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import by.godevelopment.sixthtask.R

class ResultDialogFragment : DialogFragment() {

    private val message: String by lazy {
        requireArguments().getString(ARG_MESSAGE)!!
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_title_text))
                .setMessage(message)
                .setNeutralButton(getString(R.string.dialog_neutral_btn_text)) { dialog, _ ->
                    dialog.cancel()
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        @JvmStatic private val ARG_MESSAGE = "ARG_MESSAGE"

        fun showDialog (manager: FragmentManager, message: String) {
            ResultDialogFragment().apply {
                arguments = bundleOf(ARG_MESSAGE to message)
                show(manager, null)
            }
        }
    }
}