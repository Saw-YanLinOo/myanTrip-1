package com.vmyan.myantrip.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.fragment_filter_dialog.*
import kotlinx.android.synthetic.main.fragment_filter_dialog.view.*


class FilterDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.filter_cancel_btn.setOnClickListener {
            dismiss()
        }
        var sort = ""
        var state = ""
        view.sort_chip_gp.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            sort = chip.text.toString()
        }
        view.state_chip_gp.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            state = chip.text.toString()
        }
        view.filter_apply_btn.setOnClickListener {
            val dialogListener = activity as DialogListener
            dialogListener.onFinishDialog(sort,state)
            dismiss()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)


        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    interface DialogListener {
        fun onFinishDialog(sort: String, staet: String)
    }
}