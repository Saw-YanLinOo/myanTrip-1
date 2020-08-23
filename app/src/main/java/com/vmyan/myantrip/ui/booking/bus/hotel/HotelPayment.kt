package com.vmyan.myantrip.ui.booking.bus.hotel

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
import com.vmyan.myantrip.ui.adapter.hotel.BankCardAdapter
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.BankPaymentRepositoryImpl
import com.vmyan.myantrip.ui.viewmodel.BankVM
import com.vmyan.myantrip.ui.viewmodel.BankVMFactory
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.fragment_hotel_payment.view.*
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout


class HotelPayment : Fragment() , BankCardAdapter.ItemClickListener,View.OnClickListener{
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
    private var mListener: OnStepOneListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_hotel_payment, container, false)
        view.cardPay.setOnClickListener(this)
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
        view.rv_hotelpayment.layoutManager = layoutManager

        view.rv_hotelpayment.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(view.rv_hotelpayment)
        view.rv_hotelpayment.isNestedScrollingEnabled = false

        view.rv_hotelpayment.adapter = bankListAdapter
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

    companion object {
        @JvmStatic
        fun newInstance(): HotelPayment {
            return HotelPayment()
        }
    }

    override fun onItemClick(id: String) {

    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when(p0.id){
                R.id.cardPay->{
                    if (mListener != null) {
                        mListener!!.onNextPressed(this)
                    }
                }
            }
        }

    }
}