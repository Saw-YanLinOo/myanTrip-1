package com.vmyan.myantrip.ui.bs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.booking.bus.flight.FlightListView
import com.vmyan.myantrip.ui.booking.bus.train.TrainViewList
import com.vmyan.myantrip.ui.interfaceImpl.Sorting
import kotlinx.android.synthetic.main.sorting_bottom_sheet.view.*

class FlightSorting : BottomSheetDialogFragment() {
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    lateinit var sorting: Sorting
    /*  lateinit var roomGusest  : RoomGuest
      private var roomValue : Int =0
      private var guestVlaue : Int =0*/

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        sorting = (activity as FlightListView)


        //inflating layout
        val view: View = View.inflate(context, R.layout.sorting_bottom_sheet, null)
        view.txtLowestPrice.setOnClickListener {
            sorting.lowestPrice("LOWEST")
            dismiss()
        }
        view.txtHighestPrice.setOnClickListener {
            sorting.highestPrice("HIGHEST")
            dismiss()
        }

        //setting layout with bottom sheet
        bottomSheet.setContentView(view)
        bottomSheetBehavior = BottomSheetBehavior.from<View>(view.parent as View)

        //setting Peek at the 16:9 ratio keyline of its parent.
        bottomSheetBehavior!!.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO


        return bottomSheet
    }
}

