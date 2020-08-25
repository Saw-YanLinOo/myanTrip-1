package com.vmyan.myantrip.ui.booking.bus

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.BankPaymentRepositoryImpl
import com.vmyan.myantrip.ui.adapter.hotel.BankCardAdapter
import com.vmyan.myantrip.ui.viewmodel.BankVM
import com.vmyan.myantrip.ui.viewmodel.BankVMFactory
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.fragment_bus_payment.view.*
import kotlinx.android.synthetic.main.fragment_flight_payment.view.*
import kotlinx.android.synthetic.main.fragment_flight_payment.view.rv_flightPayment
import kotlinx.android.synthetic.main.fragment_hotel_payment.view.*
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout


class BusPayment : Fragment() ,BankCardAdapter.ItemClickListener,View.OnClickListener{
    private var mListener: BusPayment.OnBusPaymentListener? = null
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            BankVMFactory(
                BankPaymentRepositoryImpl()
            )
        ).get(
            BankVM::class.java
        )
    }
    private lateinit var bankListAdapter : BankCardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bus_payment, container, false)
        view.cardBusPay.setOnClickListener(this)
        showBankList(view)
        setConfirm()
        return view
    }

    fun showBankList(view : View) {
        bankListAdapter = BankCardAdapter(this,mutableListOf())
        val layoutManager = activity?.let { ZoomRecyclerLayout(it) }
        layoutManager!!.orientation = LinearLayoutManager.HORIZONTAL
        layoutManager.reverseLayout = false
        layoutManager.stackFromEnd = false
        view.rv_flightPayment.layoutManager = layoutManager

        view.rv_flightPayment.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(view.rv_hotelpayment)
        view.rv_flightPayment.isNestedScrollingEnabled = false

        view.rv_flightPayment.adapter = bankListAdapter
    }
    @SuppressLint("ShowToast")
    fun setConfirm() {


        viewModel.fetchBankList().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    println("loading.....PlaceCategoryList")
                }
                is Resource.Success -> {
                    bankListAdapter.setItems(it.data)

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

    interface OnBusPaymentListener {
        //void onFragmentInteraction(Uri uri);
        fun onNextPressed(fragment: Fragment?)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is BusPayment.OnBusPaymentListener) {
            context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): BusPayment {
            return BusPayment()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
         R.id.cardBusPay ->{
             if (mListener != null) {
                 mListener!!.onNextPressed(this)
             }
         }
        }
    }

    override fun onItemClick(id: String) {
        TODO("Not yet implemented")
    }
}