package com.vmyan.myantrip.ui.booking.bus

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract

class CityFromPostActivityContract : ActivityResultContract<Int, ArrayList<String>?>() {

    override fun createIntent(context: Context, input: Int): Intent {
        return CityFrom.getIntent(context, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ArrayList<String>? {
        val data = intent?.getStringExtra(CityFrom.TITLE)
        val img =intent?.getStringExtra(CityFrom.IMAGE)
        val list = arrayListOf<String>()
        if (data != null && img != null){
            list.add(data)
            list.add(img)
        }else{
            list.add("")
            list.add("")
        }
        return if (resultCode == Activity.RESULT_OK) list
        else null
    }

}