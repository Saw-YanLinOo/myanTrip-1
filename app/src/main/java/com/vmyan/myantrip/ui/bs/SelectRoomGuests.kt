package com.vmyan.myantrip.ui.bs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.vmyan.myantrip.ui.interfaceImpl.RoomGuest
import com.vmyan.myantrip.R
import com.vmyan.myantrip.databinding.SelectGuestsRoomsBinding
import com.vmyan.myantrip.ui.booking.bus.hotel.HotelBooking


class  SelectRoomGuests : BottomSheetDialogFragment() {



    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private lateinit var bi: SelectGuestsRoomsBinding
    lateinit var roomGusest  : RoomGuest
    private var roomValue : Int =0
    private var guestVlaue : Int =0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        roomGusest=(activity as HotelBooking)


        //inflating layout
        val view: View = View.inflate(context, R.layout.select_guests_rooms,null )

        //binding views to data binding.
        bi = DataBindingUtil.bind(view)!!

        //setting layout with bottom sheet
        bottomSheet.setContentView(view)
        bottomSheetBehavior = BottomSheetBehavior.from<View>(view.parent as View)

        //setting Peek at the 16:9 ratio keyline of its parent.
        bottomSheetBehavior!!.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        bi.numPickerRoom.maxValue=10
        bi.numPickerRoom.minValue=0
        bi.numPickerRoom.value=0
        bi.numPickerGuests.maxValue=10
        bi.numPickerGuests.minValue=0
        bi.numPickerGuests.value=0

        bi.numPickerRoom.setOnValueChangedListener { picker, oldVal, newVal ->
            roomValue =newVal

        }
        bi.numPickerGuests.setOnValueChangedListener { picker, oldVal, newVal ->
            guestVlaue=newVal
        }

        bi.cardRGDone.setOnClickListener {

            roomGusest.room(roomValue)
            roomGusest.guest(guestVlaue)

            dismiss()




        }


        return bottomSheet
    }



}