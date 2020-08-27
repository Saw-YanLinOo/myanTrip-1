package com.vmyan.myantrip.ui.booking.bus

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.fragment_select_seat_bus.*
import kotlinx.android.synthetic.main.fragment_select_seat_bus.view.*


class SelectSeatBus : Fragment() ,View.OnClickListener{
    private var mListener: OnStepOneListener? = null
    var layout: ViewGroup? = null

    var seats = ("AA__AA/"
            + "AA__AA/"
            + "AU__AA/"
            + "AA__AA/"
            + "AA__UA/"
            + "AA__AA/"
            + "AA__AA/"
            + "AA__UA/"
            + "AA__AA/"
            + "AA__AA/"
            + "AA__AA/"
            + "AA__AA/"
            + "bbbb/")
    val seatViewList:ArrayList<TextView> = ArrayList()
    var seatSize = 120
    var seatGaping = 10
    var STATUS_AVAILABLE = 1
    var STATUS_BOOKED = 2
    var STATUS_RESERVED = 3
    var selectedIds = ""
    private lateinit var nextBT: CardView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val  root =inflater.inflate(R.layout.fragment_select_seat_bus, container, false)
        nextBT=root.findViewById(R.id.card_NextBusStepper)
        nextBT.setOnClickListener (this )
      root.card_NextBusStepper.setOnClickListener {
          if (mListener != null) {
              mListener!!.onNextPressed(this)
          }
      }
        layout = root.findViewById(R.id.layoutSeat)
      //  root.txt_seatNumber.text=selectedIds

        seats = "/$seats"

        val layoutSeat = LinearLayout(context)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layoutSeat.orientation = LinearLayout.VERTICAL
        layoutSeat.layoutParams = params
        layoutSeat.setPadding(8 * seatGaping, 8 * 1, 8 * seatGaping, 8 * seatGaping)
        layout!!.addView(layoutSeat)

        var layout: LinearLayout? = null

        var count = 0

        for (index in 0 until seats.length) {
            if (seats[index] == '/') {
                layout = LinearLayout(context)
                layout.orientation = LinearLayout.HORIZONTAL
                layoutSeat.addView(layout)
            } else if (seats[index] == 'U') {
                count++
                val view = TextView(context)
                val layoutParams =
                    LinearLayout.LayoutParams(seatSize, seatSize)
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                view.layoutParams = layoutParams
                view.setPadding(0, 0, 0, 2 * seatGaping)
                view.id = count
                view.gravity = Gravity.CENTER
                view.setBackgroundResource(R.drawable.ic_seats_booked)
                view.setTextColor(Color.WHITE)
                view.tag = STATUS_BOOKED
                view.text = count.toString() + ""
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
                layout!!.addView(view)
                seatViewList.add(view)
                view.setOnClickListener(this)
            } else if (seats[index] == 'A') {
                count++
                val view = TextView(context)
                val layoutParams =
                    LinearLayout.LayoutParams(seatSize, seatSize)
                layoutParams.setMargins(seatGaping, seatGaping , seatGaping, seatGaping)
                view.layoutParams = layoutParams
                view.setPadding(0, 0, 0, 2 * seatGaping)
                view.id = count
                view.gravity = Gravity.CENTER
                view.setBackgroundResource(R.drawable.ic_seats_book)
                view.text = count.toString() + ""
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
                view.setTextColor(Color.BLACK)
                view.tag = STATUS_AVAILABLE
                layout!!.addView(view)
                seatViewList.add(view)
                view.setOnClickListener(this)
            } else if (seats[index] == 'R') {
                count++
                val view = TextView(context)
                val layoutParams =
                    LinearLayout.LayoutParams(seatSize, seatSize)
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                view.layoutParams = layoutParams
                view.setPadding(0, 0, 0, 2 * seatGaping)
                view.id = count
                view.gravity = Gravity.CENTER
                view.setBackgroundResource(R.drawable.ic_seats_reserved)
                view.text = count.toString() + ""
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
                view.setTextColor(Color.WHITE)
                view.tag = STATUS_RESERVED
                layout!!.addView(view)
                seatViewList.add(view)
                view.setOnClickListener(this)
            } else if (seats[index] == '_') {
                val view = TextView(context)
                val layoutParams =
                    LinearLayout.LayoutParams(70, 70)
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                view.layoutParams = layoutParams
                view.setBackgroundColor(Color.TRANSPARENT)
                view.text = ""
                layout!!.addView(view)
            } else if (seats[index] == 'b') {
                count++
                val view = TextView(context)
                val layoutParams = LinearLayout.LayoutParams(175, 160)
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                view.layoutParams = layoutParams
                view.setPadding(0, 0, 0, 2 * seatGaping)
                view.id = count
                view.gravity = Gravity.CENTER
                view.setBackgroundResource(R.drawable.ic_seats_book)
                view.text = count.toString() + ""
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
                view.setTextColor(Color.WHITE)
                view.tag = STATUS_RESERVED
                layout!!.addView(view)
                seatViewList.add(view)
                view.setOnClickListener(this)
            }
        }

        return root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnStepOneListener) {
            context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener")
        }
    }
    override fun onClick(view: View?) {
        if (view != null) {
            if (view.tag as Int == STATUS_AVAILABLE) {
                if (selectedIds.contains(view.id.toString() + ",")) {
                    selectedIds = selectedIds.replace(view.id.toString() + ",", "")
                    view.setBackgroundResource(R.drawable.ic_seats_book)
                } else {
                    selectedIds = selectedIds + view.id + ","
                    txt_seatNumber.text=selectedIds
                    view.setBackgroundResource(R.drawable.ic_seats_selected)
                }
            } else if (view.tag as Int == STATUS_BOOKED) {

            } else if (view.tag as Int == STATUS_RESERVED) {

            }
        }



        }

    interface OnStepOneListener {
        //void onFragmentInteraction(Uri uri);
        fun onNextPressed(fragment: Fragment?)
    }

    companion object {
        @JvmStatic
        fun newInstance(): SelectSeatBus {
            return SelectSeatBus()
        }
    }

}