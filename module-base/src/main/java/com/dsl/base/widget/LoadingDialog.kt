package com.dsl.base.widget

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.dsl.base.R
import com.dsl.base.fragment.BaseDialogFragment

/**
 * @author dsl-abben
 * on 2020/02/24.
 */
class LoadingDialog : BaseDialogFragment() {

    companion object {
        private const val KEY_MESSAGE = "KEY_MESSAGE"

        fun onNewInstance(message: String): LoadingDialog {
            val loadingDialog = LoadingDialog()
            val bundle = Bundle()
            bundle.putString(KEY_MESSAGE, message)
            loadingDialog.arguments = bundle
            return loadingDialog
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_loading, null, false)
        val messageView: TextView = view.findViewById(R.id.message)
        arguments?.let { messageView.text = it.getString(KEY_MESSAGE) }
        dialog.setContentView(view)
        isCancelable = false
        return dialog
    }
}
