package com.vmyan.myantrip.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.Login
import kotlinx.android.synthetic.main.noaccount_loginview_dialog.view.*


class LoadingDialog(private val appCompatActivity: AppCompatActivity) {

    private lateinit var dialog: AlertDialog

    fun startLoading(){
        val builder = AlertDialog.Builder(appCompatActivity)
        val inflater = appCompatActivity.layoutInflater

        builder.setView(inflater.inflate(R.layout.loading_dialog, null))
        builder.setCancelable(true)

        dialog = builder.create()

        val window = dialog.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)

        dialog.show()

        val params: WindowManager.LayoutParams = dialog.window!!.attributes
        dialog.window!!.attributes = params
    }

    fun stopLoading() {
        dialog.dismiss()
    }

}