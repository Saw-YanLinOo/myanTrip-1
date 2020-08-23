package com.vmyan.myantrip.ui.booking.bus.flight

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.booking.carRental.flight.FlightListRepositoryImpl
import com.vmyan.myantrip.ui.viewmodel.flight.FlightListVM
import com.vmyan.myantrip.ui.viewmodel.flight.FlightListVMFactory
import com.vmyan.myantrip.utils.Resource

import kotlinx.android.synthetic.main.fragment_confirm_flight.view.*


class ConfirmFlight : Fragment() {
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            FlightListVMFactory(FlightListRepositoryImpl())
        ).get(
            FlightListVM::class.java
        )
    }
    private var mListener: OnStepOneListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_confirm_flight, container, false)
        setAllFlightList(view)
        view.cardFlightContinue.setOnClickListener {
            if (mListener != null) {
                mListener!!.onNextPressed(this)
            }
        }


        return view
    }


        interface OnStepOneListener {
    //void onFragmentInteraction(Uri uri);
                    fun onNextPressed(fragment: Fragment?)
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
    @SuppressLint("ShowToast")
    private fun setAllFlightList(view : View) {
        //activity
        viewModel.fetchFlightList().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    println("loading.....PlaceCategoryList")
                }
                is Resource.Success -> {
                    for( d in it.data){
                        view.txtFlightName.text=d.flightName
                        view.txt_SPlace.text=d.flightFrom
                        view.txt_EndPlace.text=d.flightTo
                        view.txt_DepartTime.text=d.fTimeStart
                        view.txt_ArriveTime.text=d.fTimeStop
                        view.txt_ConfirmFlightDepartDate.text=d.flightDeparture
                        view.txtFlightBaggage.text=d.fBassageAllow
                        view.txtFlightPrice.text=d.flightPrice
                    }

                }
                is Resource.Failure -> {
                    println(it.message)
                    Toast.makeText(
                        activity,
                        "An error is ocurred:${it.message}",
                        Toast.LENGTH_SHORT
                    )
                }
            }
        })
    }




    companion object {
        @JvmStatic
        fun newInstance(): ConfirmFlight {
            return ConfirmFlight()
        }
    }



}