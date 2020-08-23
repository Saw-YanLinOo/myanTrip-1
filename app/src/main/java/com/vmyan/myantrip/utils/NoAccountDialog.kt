package com.vmyan.myantrip.utils

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.Login
import com.vmyan.myantrip.ui.Register
import kotlinx.android.synthetic.main.noaccount_loginview_dialog.view.*


class NoAccountDialog (private val context: Context){
    private lateinit var dialog: AlertDialog

    fun noAccountDialog(){
        val builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.noaccount_loginview_dialog, null)
        view.nologin.setOnClickListener {
            context.startActivity(Intent(context, Login::class.java))
        }
        view.nosignup.setOnClickListener {
            context.startActivity(Intent(context, Register::class.java))
        }
        builder.setView(view)
        builder.setCancelable(false)


        dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }
}