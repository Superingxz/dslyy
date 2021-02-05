package com.dsl.base.widget

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.dsl.base.R
import com.dsl.base.fragment.BaseDialogFragment

/**
 * 警告的dialog
 * @author dsl-abben
 * on 2019/08/05.
 */
class WarnDialog : BaseDialogFragment() {
    companion object {
        private const val KEY_WARNTEXT = "KEY_WARNTEXT"
        private const val KEY_POSITIVE_TEXT = "KEY_POSITIVE_TEXT"

        fun newInstance(warnText: String, positiveText: String = "确认"): WarnDialog {
            val warnDialog = WarnDialog()
            val bundle = Bundle()
            bundle.putString(KEY_WARNTEXT, warnText)
            bundle.putString(KEY_POSITIVE_TEXT, positiveText)
            warnDialog.arguments = bundle
            return warnDialog
        }
    }

    interface OnWarnDialogClickListener {
        fun onPositive()
    }

    var cancelableSet: Boolean = true
    var onWarnDialogClickListener: OnWarnDialogClickListener? = null

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext(), R.style.CustomDialog)
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_warn, null, false)
        val warnText = view.findViewById<TextView>(R.id.warn_text)
        val positiveButton = view.findViewById<TextView>(R.id.positive_button)
        arguments?.let {
            warnText.text = it.getString(KEY_WARNTEXT, "")
            positiveButton.text = it.getString(KEY_POSITIVE_TEXT, "")
        }
        positiveButton.setOnClickListener {
            onWarnDialogClickListener?.onPositive()
            dismiss()
        }
        isCancelable = cancelableSet
        dialog.setContentView(view)
        return dialog
    }
}
