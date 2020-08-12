package com.vmyan.myantrip.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.vmyan.myantrip.R
import com.vmyan.myantrip.customui.cardslider.CardSliderLayoutManager
import com.vmyan.myantrip.customui.cardslider.CardSnapHelper
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.ui.adapter.PlaceDetailsGalleryAdapter
import com.vmyan.myantrip.ui.adapter.PlaceImgSliderAdapter
import com.vmyan.myantrip.ui.viewmodel.PlaceDetailsVMFactory
import com.vmyan.myantrip.ui.viewmodel.PlaceDetailsViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_place_details.*
import kotlinx.android.synthetic.main.location_card.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class PlaceDetailsActivity : AppCompatActivity(), DIAware {

    override val di: DI by closestDI()
    private val viewModelFactory : PlaceDetailsVMFactory by instance()

    private lateinit var viewModel: PlaceDetailsViewModel
    private lateinit var placeImgSliderAdapter: PlaceImgSliderAdapter
    private lateinit var layoutManger: CardSliderLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var placeDetailsGalleryAdapter: PlaceDetailsGalleryAdapter
    private val pics =
        intArrayOf(R.drawable.profile, R.drawable.profile, R.drawable.profile, R.drawable.profile, R.drawable.profile)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_details)

        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaceDetailsViewModel::class.java)

        val place_id = intent.getStringExtra("place_id")
        setUpToolbar()
        setUpImageSlider()
        initRecyclerView()
        if (place_id != null) {
            setUpObserver(place_id)
        }

        place_mapView.onCreate(savedInstanceState)
        place_mapView.onResume()

    }

    private fun initRecyclerView() {
        placeDetailsGalleryAdapter = PlaceDetailsGalleryAdapter(arrayListOf())
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = placeDetailsGalleryAdapter
        recyclerView.setHasFixedSize(true)
        layoutManger = recyclerView.layoutManager as CardSliderLayoutManager
        CardSnapHelper().attachToRecyclerView(recyclerView)
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

    private fun setUpUI(data: Place){
        placeImgSliderAdapter.setItems(data.sliderImg)
        placeDetailsGalleryAdapter.setItems(data.gallery)
        toolbar_title.text = data.name
        details_name.text = data.name
        details_category.text = data.category
        details_buildDate.text = data.buildDate
        details_founder.text = data.founder
        Glide.with(this)
            .load(data.mainImg)
            .into(details_mainImg)
        info.text = data.info
        history.text = data.history
        country.text = data.country
        state.text = data.state
        city.text = data.city
        address.text = data.city
        lat.text = data.latlng.latitude.toString()+"°"
        lng.text = data.latlng.longitude.toString()+"°"
        place_mapView.getMapAsync {googleMap ->
            googleMap?.apply {
                mapType = GoogleMap.MAP_TYPE_HYBRID
                val placeLatlng = LatLng(data.latlng.latitude, data.latlng.longitude)

                val cameraPosition = CameraPosition.Builder()
                    .target(placeLatlng)
                    .zoom(15f).build()

                addMarker(
                    MarkerOptions()
                        .position(placeLatlng)
                        .title(data.name)
                )
                animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
        }

    }

    private fun setUpObserver(id: String) {
        viewModel.fetchPlace(id).observe(this, Observer {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    for (data in it.data){
                        setUpUI(data)
                    }
                }
                is Resource.Failure -> {
                    println(it.throwable.message)
                    Toast.makeText(
                        this,
                        "An error is ocurred:${it.throwable.message}",
                        Toast.LENGTH_SHORT
                    )
                }
            }
        })
    }
}