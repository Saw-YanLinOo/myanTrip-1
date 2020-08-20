package com.vmyan.myantrip.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.orhanobut.hawk.Hawk
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.vmyan.myantrip.R
import com.vmyan.myantrip.customui.cardslider.CardSliderLayoutManager
import com.vmyan.myantrip.customui.cardslider.CardSnapHelper
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.model.PlaceDetails
import com.vmyan.myantrip.model.Review
import com.vmyan.myantrip.ui.adapter.*
import com.vmyan.myantrip.ui.fragment.AddReviewDialogFragment
import com.vmyan.myantrip.ui.fragment.ReviewAllDialogFragment
import com.vmyan.myantrip.ui.fragment.UpdateReviewDialogFragment
import com.vmyan.myantrip.ui.viewmodel.PlaceDetailsViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_place_details.*
import kotlinx.android.synthetic.main.location_card.*
import kotlinx.android.synthetic.main.rating_reviews_place_details.*
import org.koin.android.ext.android.inject
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class PlaceDetailsActivity : AppCompatActivity(),PCPlaceListAdapter.ItemClickListener, AddReviewDialogFragment.DialogListener, UpdateReviewDialogFragment.DialogListener {


    private val viewModel: PlaceDetailsViewModel by inject()
    private lateinit var placeImgSliderAdapter: PlaceImgSliderAdapter
    private lateinit var layoutManger: CardSliderLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var placeDetailsGalleryAdapter: PlaceDetailsGalleryAdapter
    private lateinit var reviewListAdapter: ReviewListAdapter
    private lateinit var pcPlaceListAdapter: PCPlaceListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_details)


        val placeId = intent.getStringExtra("place_id")
        setUpToolbar()
        setUpImageSlider()
        initRecyclerView()
        setUpReviewListRecycler()
        setUpNearbyRecycler()
        if (placeId != null) {
            setUpObserver(placeId)
        }

        place_mapView.onCreate(savedInstanceState)
        place_mapView.onResume()
        detail_s_btn.setOnClickListener {
            startActivity(Intent(this, SearchPlaceActivity::class.java))
        }
        detail_s_btnly.setOnClickListener {
            startActivity(Intent(this, SearchPlaceActivity::class.java))
        }

        detail_back_btnly.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        detail_back_btn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }

    private fun initRecyclerView() {
        placeDetailsGalleryAdapter = PlaceDetailsGalleryAdapter(arrayListOf())
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = placeDetailsGalleryAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.isNestedScrollingEnabled = false
        layoutManger = recyclerView.layoutManager as CardSliderLayoutManager
        CardSnapHelper().attachToRecyclerView(recyclerView)
    }

    private fun setUpReviewListRecycler(){
        reviewListAdapter = ReviewListAdapter(mutableListOf())
        review_recycler_litmit.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        review_recycler_litmit.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.TOP)
        snapHelperStart.attachToRecyclerView(review_recycler_litmit)
        review_recycler_litmit.isNestedScrollingEnabled = false
        review_recycler_litmit.adapter = reviewListAdapter

    }

    private fun setUpImageSlider(){
        placeImgSliderAdapter = PlaceImgSliderAdapter()
        imageSlider.setSliderAdapter(placeImgSliderAdapter)
        imageSlider.setIndicatorEnabled(true)
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        imageSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        imageSlider.indicatorSelectedColor = Color.WHITE
        imageSlider.indicatorUnselectedColor = Color.GRAY
        imageSlider.scrollTimeInSec = 3 //set scroll delay in seconds :
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }

    private fun setUpToolbar() {
        var isShow = true
        var scrollRange = -1
        appbar_layout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1){
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0){
                toolbar_title.visibility = View.VISIBLE
                isShow = true
            } else if (isShow){
                toolbar_title.visibility = View.GONE
                isShow = false
            }
        })
    }
    
    private fun getNearybyList(id: String, city: String){
        viewModel.fetchNearByPlace(city).observe(this, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    val list = mutableListOf<Place>()
                    for (data in it.data){
                        if (data.place_id != id){
                            list.add(data)
                        }
                    }
                    highRPlaceList(list)
                }
                is Resource.Failure -> {
                    println(it.message)
                    Toast.makeText(
                        this,
                        "An error is ocurred:${it.message}",
                        Toast.LENGTH_SHORT
                    )
                }
            }
        })
    }

    private fun highRPlaceList(list: MutableList<Place>){
        list.sortWith(Comparator { p0, p1 ->
            var res = -1
            if (p0!!.ratingValue < p1!!.ratingValue) {
                res = 1
            }
            res
        })

        pcPlaceListAdapter.setItems(list)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setUpUI(data: PlaceDetails){
        placeImgSliderAdapter.setItems(data.place.sliderImg)
        placeDetailsGalleryAdapter.setItems(data.place.gallery)
        toolbar_title.text = data.place.name
        details_name.text = data.place.name
        details_category.text = data.place.category
        details_buildDate.text = data.place.buildDate
        details_founder.text = data.place.founder
        Glide.with(this)
            .load(data.place.mainImg)
            .into(details_mainImg)
        info.text = data.place.info
        history.text = data.place.history
        country.text = data.place.country
        state.text = data.place.state
        city.text = data.place.city
        address.text = data.place.address
        lat.text = data.place.latlng.latitude.toString()+"°"
        lng.text = data.place.latlng.longitude.toString()+"°"
        place_mapView.getMapAsync {googleMap ->
            googleMap?.apply {
                mapType = GoogleMap.MAP_TYPE_HYBRID
                val placeLatlng = LatLng(data.place.latlng.latitude, data.place.latlng.longitude)

                val cameraPosition = CameraPosition.Builder()
                    .target(placeLatlng)
                    .zoom(15f).build()

                addMarker(
                    MarkerOptions()
                        .position(placeLatlng)
                        .title(data.place.name)
                )
                animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
        }

        var avgOne = 0
        var avgTwo = 0
        var avgThree = 0
        var avgFour = 0
        var avgFive = 0
        val total = data.reviewList.size
        var totalRVal = 0F
        var litmit = 0
        val reviewListLitmit = mutableListOf<Review>()
        val userReview = mutableListOf<Review>()
        for (r in data.reviewList){
            totalRVal+= r.rating_val
            litmit++
            if (litmit <= 3){
                reviewListLitmit.add(r)
            }

            if (r.user_id == Hawk.get("user_id")){
                userReview.add(r)
            }

            if (r.rating_val == 5F){
                avgFive++
            }else if (r.rating_val >=4 && r.rating_val <5){
                avgFour++
            }else if (r.rating_val >=3 && r.rating_val <4){
                avgThree++
            }
            else if (r.rating_val >=2 && r.rating_val <3){
                avgTwo++
            }
            else if (r.rating_val >=1 && r.rating_val <2){
                avgOne++
            }
        }
        val avgr = (totalRVal/total)
        avg_review_val.text = DecimalFormat("#.#").format(avgr).toString()
        avg_review_bar.rating = avgr
        totalreview.text = total.toString()
        avg_review_one.max = total.toFloat()
        avg_review_one.progress = avgOne.toFloat()
        avg_review_two.max = total.toFloat()
        avg_review_two.progress = avgTwo.toFloat()
        avg_review_three.max = total.toFloat()
        avg_review_three.progress = avgThree.toFloat()
        avg_review_four.max = total.toFloat()
        avg_review_four.progress = avgFour.toFloat()
        avg_review_five.max = total.toFloat()
        avg_review_five.progress = avgFive.toFloat()

        reviewListAdapter.setItems(reviewListLitmit)

        rbtn.rating = 0f
        write_review_btn.setOnClickListener {
            addReviewDialog(data,rbtn.rating,viewModel)
        }

        viewalluserreview_btn.setOnClickListener {
            showReviewAllDialog(data.reviewList,data.place)
        }


        rbtn.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            addReviewDialog(data,rating,viewModel)
        }

        getNearybyList(data.place.place_id, data.place.city)

        if (userReview.size == 0){
            ratethisplace_ly.visibility = View.VISIBLE
            write_review_btn.visibility = View.VISIBLE
            edit_review_btn.visibility = View.GONE
            yourreview_ly.visibility = View.GONE
        }else{
            for (review in userReview){
                yourreview_ly.visibility = View.VISIBLE
                ratethisplace_ly.visibility = View.GONE
                write_review_btn.visibility = View.INVISIBLE
                edit_review_btn.visibility = View.VISIBLE
                Glide.with(this)
                    .load(review.user_img)
                    .into(yourreview_userimg)
                val date = review.date.toDate()
                val pattern = "dd/MM/yyyy"
                val simpleDateFormat = SimpleDateFormat(pattern)
                val d = simpleDateFormat.format(date)
                yourreview_date.text = d
                yourreview_desc.text = review.desc
                yourreview_username.text = review.user_name
                yourreview_ratingbar.rating = review.rating_val
                edit_review_btn.setOnClickListener {
                    updateReviewDialog(data, review, viewModel)
                }
            }
        }

    }

    private fun showReviewAllDialog(list: MutableList<Review>, place: Place) {
        val fragmentManager = supportFragmentManager
        val newFragment = ReviewAllDialogFragment(list,place)
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction
            .add(android.R.id.content, newFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun addReviewDialog(place: PlaceDetails,rating: Float,viewModel: PlaceDetailsViewModel) {
        val fragmentManager = supportFragmentManager
        val newFragment = AddReviewDialogFragment(place,rating,viewModel)
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction
            .add(android.R.id.content, newFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun updateReviewDialog(place: PlaceDetails, review: Review,viewModel: PlaceDetailsViewModel) {
        val fragmentManager = supportFragmentManager
        val newFragment = UpdateReviewDialogFragment(place, review,viewModel)
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction
            .add(android.R.id.content, newFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setUpNearbyRecycler(){
        pcPlaceListAdapter = PCPlaceListAdapter(this, mutableListOf())
        details_nearyby_recycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)
        details_nearyby_recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.START)
        snapHelperStart.attachToRecyclerView(details_nearyby_recycler)
        details_nearyby_recycler.isNestedScrollingEnabled = false
        details_nearyby_recycler.adapter = pcPlaceListAdapter
    }

    @SuppressLint("ShowToast")
    private fun setUpObserver(id: String) {
        viewModel.fetchPlace(id).observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    mainly.visibility = View.GONE
                    appbar_layout.visibility = View.GONE
                    progress_bar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    mainly.visibility = View.VISIBLE
                    appbar_layout.visibility = View.VISIBLE
                    progress_bar.visibility = View.GONE
                    for (data in it.data){
                        setUpUI(data)
                    }
                }
                is Resource.Failure -> {
                    mainly.visibility = View.GONE
                    appbar_layout.visibility = View.GONE
                    progress_bar.visibility = View.GONE
                    println(it.message)
                    Toast.makeText(
                        this,
                        "An error is ocurred:${it.message}",
                        Toast.LENGTH_SHORT
                    )
                }
            }
        })
    }

    override fun onPlaceClick(place_id: String) {
        val i = Intent(this,PlaceDetailsActivity::class.java)
        i.putExtra("place_id", place_id)
        startActivity(i)
    }

    override fun onFinishDialog(id: String) {
        setUpObserver(id)
    }

    override fun onUpdateFinish(id: String) {
        setUpObserver(id)
    }


}