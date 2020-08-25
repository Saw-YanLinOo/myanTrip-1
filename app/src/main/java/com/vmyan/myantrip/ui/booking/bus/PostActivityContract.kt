package com.vmyan.myantrip.ui.booking.bus

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract


/**
 * Designed and developed by Andrew Japar (@andrewjapar)
 *
 */

class PostActivityContract() : ActivityResultContract<Int, ArrayList<String>?>(), Parcelable {

    constructor(parcel: Parcel) : this() {
    }

    override fun createIntent(context: Context, input: Int): Intent {
        return StayingPlaceActivity.getIntent(context, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ArrayList<String>? {
        val data = intent?.getStringExtra(StayingPlaceActivity.TITLE)
        val img =intent?.getStringExtra(StayingPlaceActivity.IMAGE)
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostActivityContract> {
        override fun createFromParcel(parcel: Parcel): PostActivityContract {
            return PostActivityContract(parcel)
        }

        override fun newArray(size: Int): Array<PostActivityContract?> {
            return arrayOfNulls(size)
        }
    }
}