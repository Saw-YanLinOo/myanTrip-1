package com.vmyan.myantrip.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.ui.PlaceByCategoryActivity
import com.vmyan.myantrip.ui.PlaceDetailsActivity
import com.vmyan.myantrip.ui.SearchPlaceActivity
import com.vmyan.myantrip.ui.adapter.HomePlaceListAdapter
import com.vmyan.myantrip.ui.adapter.PlaceCategoryAdapter
import com.vmyan.myantrip.ui.viewmodel.HomeVMFactory
import com.vmyan.myantrip.ui.viewmodel.HomeViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(), PlaceCategoryAdapter.ItemClickListener, HomePlaceListAdapter.ItemClickListener,
    DIAware {

    override val di: DI by closestDI()
    private val viewModelFactory: HomeVMFactory by instance()

    private lateinit var viewModel: HomeViewModel

    private lateinit var placeCategoryAdapter: PlaceCategoryAdapter
    private lateinit var homePlaceListAdapter: HomePlaceListAdapter
    private val subPlaceCategoryList = ArrayList<String>()
    private val pList = mutableListOf<Place>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.home_search_btn.setOnClickListener {
            startActivity(Intent(activity,SearchPlaceActivity::class.java))
        }
        view.home_search_btn1.setOnClickListener {
            startActivity(Intent(activity,SearchPlaceActivity::class.java))
        }
        view.home_search_btn2.setOnClickListener {
            startActivity(Intent(activity,SearchPlaceActivity::class.java))
        }

        setUpPlaceCategoryRecycler(view)
        setUpHomePlaceRecycler(view)
        onSubTabClick(view)
        setUpObserver(view)

        return view
    }

    private fun setUpPlaceCategoryRecycler(view: View){
        placeCategoryAdapter = PlaceCategoryAdapter(this, mutableListOf())
        view.home_cat_recycler.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.HORIZONTAL,false)
        view.home_cat_recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.START)
        snapHelperStart.attachToRecyclerView(view.home_cat_recycler)
        view.home_cat_recycler.isNestedScrollingEnabled = false
        view.home_cat_recycler.adapter = placeCategoryAdapter
    }

    private fun setUpHomePlaceRecycler(view: View){
        homePlaceListAdapter = HomePlaceListAdapter(this, mutableListOf())
        val layoutManager = activity?.let { ZoomRecyclerLayout(it) }
        layoutManager!!.orientation = LinearLayoutManager.HORIZONTAL
        layoutManager.reverseLayout = false
        layoutManager.stackFromEnd = false
        view.home_place_list_recycler.layoutManager = layoutManager
        view.home_place_list_recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }



        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(view.home_place_list_recycler)
        view.home_place_list_recycler.isNestedScrollingEnabled = false

        view.home_place_list_recycler.adapter = homePlaceListAdapter

    }

    private fun updatePlaceList(list: MutableList<Place>){
        Collections.sort(
            list,
            object : Comparator<Place?> {
                override fun compare(p0: Place?, p1: Place?): Int {
                    var res = -1
                    if (p0!!.ratingValue < p1!!.ratingValue) {
                        res = 1
                    }
                    return res
                }
            })

        homePlaceListAdapter.setItems(list)
    }

    @SuppressLint("ShowToast")
    private fun setUpObserver(view: View) {
        viewModel.fetchPlaceCategoryList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    view.homecat_placeholder.startShimmer()
                    view.homecat_placeholder.visibility = View.VISIBLE
                    view.home_cat_recycler.visibility = View.GONE
                }
                is Resource.Success -> {
                    view.homecat_placeholder.stopShimmer()
                    view.homecat_placeholder.visibility = View.GONE
                    view.home_cat_recycler.visibility = View.VISIBLE
                    placeCategoryAdapter.setItems(it.data)
                }
                is Resource.Failure -> {

                    view.home_placeitem_placeholder.startShimmer()
                    view.home_placeitem_placeholder.visibility = View.VISIBLE
                    view.home_place_list_recycler.visibility = View.GONE
                    println(it.message)

                }
            }
        })

        viewModel.fetchSubPlaceCategoryList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {

                    subPlaceCategoryList.clear()
                    for (data in it.data!!){
                        if (subPlaceCategoryList.size < it.data.size ){
                            subPlaceCategoryList.add(data.name)
                        }
                    }
                    view.sub_cat_tab_layout.setTabData(subPlaceCategoryList)
                }
                is Resource.Failure -> {

                    println(it.message)

                }
            }
        })
    }

    private fun onSubTabClick(view: View) {
        viewModel.fetchPlaceBySubCategory("1").observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    view.home_placeitem_placeholder.startShimmer()
                    view.home_placeitem_placeholder.visibility = View.VISIBLE
                    view.home_place_list_recycler.visibility = View.GONE
                }
                is Resource.Success -> {
                    view.home_placeitem_placeholder.stopShimmer()
                    view.home_placeitem_placeholder.visibility = View.GONE
                    view.home_place_list_recycler.visibility = View.VISIBLE
//                    homePlaceListAdapter.setItems(it.data)
                    pList.clear()
                    for (data in it.data){
                        if (pList.size < it.data.size ){
                            pList.add(data)
                        }
                    }
                    updatePlaceList(pList)
                }
                is Resource.Failure -> {

                    view.home_placeitem_placeholder.startShimmer()
                    view.home_placeitem_placeholder.visibility = View.VISIBLE
                    view.home_place_list_recycler.visibility = View.GONE
                    println(it.message)

                }
            }
        })
        view.sub_cat_tab_layout.setOnTabSelectListener {
            val id = (it+1).toString()
            Toast.makeText(activity, id,Toast.LENGTH_SHORT).show()
            viewModel.fetchPlaceBySubCategory(id).observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Resource.Loading -> {
                        view.home_placeitem_placeholder.startShimmer()
                        view.home_placeitem_placeholder.visibility = View.VISIBLE
                        view.home_place_list_recycler.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        view.home_placeitem_placeholder.stopShimmer()
                        view.home_placeitem_placeholder.visibility = View.GONE
                        view.home_place_list_recycler.visibility = View.VISIBLE
//                        homePlaceListAdapter.setItems(it.data)
                        pList.clear()
                        for (data in it.data){
                            if (pList.size < it.data.size ){
                                pList.add(data)
                            }
                        }
                        updatePlaceList(pList)
                    }
                    is Resource.Failure -> {
                        view.home_placeitem_placeholder.startShimmer()
                        view.home_placeitem_placeholder.visibility = View.VISIBLE
                        view.home_place_list_recycler.visibility = View.GONE
                        println(it.message)

                    }
                }
            })
        }
    }

    override fun onItemClick(cat_id: String, cat_name: String) {
        val i = Intent(activity,PlaceByCategoryActivity::class.java)
        i.putExtra("cat_id",cat_id)
        i.putExtra("cat_name",cat_name)
        startActivity(i)
    }

    override fun onPlaceClick(place_id: String) {
        val i = Intent(activity, PlaceDetailsActivity::class.java)
        i.putExtra("place_id",place_id)
        startActivity(i)
    }

    override fun onResume() {
        super.onResume()
        view?.home_placeitem_placeholder?.startShimmer()
        view?.homecat_placeholder?.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        view?.home_placeitem_placeholder?.stopShimmer()
        view?.homecat_placeholder?.stopShimmer()
    }

}