package com.vmyan.myantrip.ui.booking.bus

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class CityToPostActivityContract : ActivityResultContract<Int, ArrayList<String>?>() {
    override fun createIntent(context: Context, input: Int): Intent {
        return CityTo.getIntent(context, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ArrayList<String>? {
        val data = intent?.getStringExtra(CityTo.TITLE)
        val img =intent?.getStringExtra(CityTo.IMAGE)
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