package com.vmyan.myantrip.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.hawk.Hawk
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.booking.carRental.bookingCate.BookingCategoriesRepositoryImpl
import com.vmyan.myantrip.model.bookingCate.BookingCateItem
import com.vmyan.myantrip.ui.adapter.ExpierPreViewPagerAdapter
import com.vmyan.myantrip.ui.adapter.bookingCateAdapter.BookingCateAdapter
import com.vmyan.myantrip.ui.adapter.promoImageAdapter.PromoSliderImageAdapter
import com.vmyan.myantrip.ui.booking.bus.BusBooking
import com.vmyan.myantrip.ui.booking.bus.carRental.CarRental
import com.vmyan.myantrip.ui.booking.bus.flight.FlightBooking
import com.vmyan.myantrip.ui.booking.bus.hotel.HotelBooking
import com.vmyan.myantrip.ui.booking.bus.train.TrainBooking
import com.vmyan.myantrip.ui.viewmodel.bookingCate.BookingCateVMFactory
import com.vmyan.myantrip.ui.viewmodel.bookingCate.BookingCateViewModel
import com.vmyan.myantrip.utils.Resource
import com.vmyan.myantrip.utils.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.fragment_booking.view.*


class BookingFragment : Fragment(),BookingCateAdapter.ItemClickListener {
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            BookingCateVMFactory(BookingCategoriesRepositoryImpl())
        ).get(
            BookingCateViewModel::class.java
        )
    }

    lateinit var  promoSliderList:ArrayList<String>
    lateinit var bookingCate : MutableList<BookingCateItem>
    private lateinit var sliderImageAdapter: PromoSliderImageAdapter
    private lateinit var bookingCateAdapter: BookingCateAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_booking, container, false)
        view.txtUserName.text= Hawk.get("user_name")
        showImageSlider(view)
        setUpObserver(view)
        setUpPlaceCategoryRecycler(view)

        view.viewPager2.adapter = ExpierPreViewPagerAdapter(requireActivity())
        view.viewPager2.setPageTransformer(ZoomOutPageTransformer())
        TabLayoutMediator(view.tabLayout, view.viewPager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when(position){
                    0 -> tab.text = "Upcoming"
                    1 -> tab.text = "Past"
                }
            }).attach()


        return view
    }
    private fun setUpPlaceCategoryRecycler(view: View){
        bookingCateAdapter = BookingCateAdapter(this, mutableListOf())
        view.rv_cateItem.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.HORIZONTAL,false)
        view.rv_cateItem.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.START)
        snapHelperStart.attachToRecyclerView(view.rv_cateItem)

        view.rv_cateItem.isNestedScrollingEnabled=false
        view.rv_cateItem.adapter = bookingCateAdapter

    }



    private fun showImageSlider(view : View){
        sliderImageAdapter = PromoSliderImageAdapter()
        view.sv_Promo.setSliderAdapter(sliderImageAdapter)        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!
        view.sv_Promo.setIndicatorAnimation(IndicatorAnimationType.WORM)
        view. sv_Promo.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        view. sv_Promo.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        view.sv_Promo.setIndicatorSelectedColor(Color.WHITE)
        view.sv_Promo.setIndicatorUnselectedColor(Color.GRAY)
        view.sv_Promo.setScrollTimeInSec(3)
        view.sv_Promo.setAutoCycle(true)
        view.sv_Promo.startAutoCycle()

    }


    override fun onItemClick(bookingName: String, id: String) {
        when(bookingName){
            "Hotel" -> {
                openActivity(HotelBooking::class.java,id)
            }
            "Flight"->{
               openActivity(FlightBooking::class.java, id)
            }
            "Bus"->{
                openActivity(BusBooking::class.java, id)
            }
            "Car Rental" ->{
               openActivity(CarRental::class.java,id)
            }
            "Train"-> {
                openActivity(TrainBooking::class.java,id)
            }

        }
    }
    @SuppressLint("ShowToast")
    private fun setUpObserver(view: View) {
        viewModel.fetchBookingCateList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    view.home_cateBooking_placeholder.startShimmer()
                    view.home_cateBooking_placeholder.visibility = View.VISIBLE
                    view.rv_cateItem.visibility = View.GONE
                    println("loading.....PlaceCategoryList")
                }
                is Resource.Success -> {
                    view.home_cateBooking_placeholder.stopShimmer()
                    view.home_cateBooking_placeholder.visibility = View.GONE
                    view.rv_cateItem.visibility = View.VISIBLE
                    bookingCateAdapter.setItems(it.data)

                }
                is Resource.Failure -> {
                    view.home_cateBooking_placeholder.startShimmer()
                    view.home_cateBooking_placeholder.visibility = View.GONE
                    view.home_cateBooking_placeholder.visibility = View.GONE
                    println(it.message)

                }
            }
        })

        viewModel.fetchPromoList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    println("loading.....PlaceCategoryList")
                }
                is Resource.Success -> {
                    sliderImageAdapter.setItem(it.data[0].imgUrl)

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

    private fun openActivity(intentC : Class<*>, id: String){
        val intent = Intent(activity,intentC)
        intent.putExtra("id", id)
        startActivity(intent)

    }


}