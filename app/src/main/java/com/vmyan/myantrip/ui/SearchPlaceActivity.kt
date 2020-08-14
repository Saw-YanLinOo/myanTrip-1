package com.vmyan.myantrip.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.ui.adapter.SearchPlaceListAdapter
import com.vmyan.myantrip.ui.fragment.FilterDialogFragment
import com.vmyan.myantrip.ui.viewmodel.SearchPlaceVMFactory
import com.vmyan.myantrip.ui.viewmodel.SearchPlaceViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_search_place.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance
import java.util.*


class SearchPlaceActivity : AppCompatActivity(), SearchPlaceListAdapter.ItemClickListener,FilterDialogFragment.DialogListener, DIAware {

    override val di: DI by closestDI()
    private val viewModelFactory : SearchPlaceVMFactory by instance()

    private lateinit var viewModel: SearchPlaceViewModel
    private lateinit var searchPlaceListAdapter: SearchPlaceListAdapter
    private val placeList = mutableListOf<Place>()
    private var sort = ""
    private var state = ""

    companion object {
        private const val REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_place)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchPlaceViewModel::class.java)
        setUpSearchResultRecycler()
        setUpObserver()

        search_input.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchPlaces(search_input.text.toString())
            }

        })
        s_btn_ly.setOnClickListener {
            openVoiceCommand()
        }
        s_btn.setOnClickListener {
            openVoiceCommand()
        }

        filter_back_btn.setOnClickListener {
            showFilterDialog()
        }
        filter_back_btn_ly.setOnClickListener {
            showFilterDialog()
        }

    }

    private fun openVoiceCommand() {
        val sttintent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        sttintent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        sttintent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US)
        sttintent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")
        try {
            startActivityForResult(sttintent, REQUEST_CODE)
        }catch (e: ActivityNotFoundException){
            e.printStackTrace()
            Toast.makeText(this, "Your device doesnot support STT", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null){
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    result?.let {
                        val resulttxt = it[0]
                        search_input.setText(resulttxt)
                    }
                }
            }
        }
    }

    private fun showFilterDialog() {
        val fragmentManager = supportFragmentManager
        val newFragment = FilterDialogFragment()
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction
            .add(android.R.id.content, newFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun searchPlaces(nameS: String) {
        var name = nameS
        if (name.isNotEmpty()) name =
            name.substring(0, 1).toUpperCase(Locale.ROOT) + name.substring(1).toLowerCase(Locale.ROOT)
        val results: MutableList<Place> = mutableListOf()
        for (user in placeList) {
            if (user.name.contains(name)) {
                results.add(user)
            }
        }
        when (state) {
            "" -> sortResult(results)
            else -> sortResult(filterState(results,state))
        }
    }

    private fun sortResult(list: MutableList<Place>) {
        when (sort) {
            "" -> updatePlaceList(ascPlaceList(list))
            "A-Z" -> updatePlaceList(ascPlaceList(list))
            "Z-A" -> updatePlaceList(descPlaceList(list))
            "High Rating" -> updatePlaceList(highRPlaceList(list))
            "Low Rating" -> updatePlaceList(lowRPlaceList(list))
            else -> updatePlaceList(ascPlaceList(list))
        }
    }


    private fun updatePlaceList(list: MutableList<Place>){
        searchPlaceListAdapter.setItems(list)
    }



    private fun ascPlaceList(list: MutableList<Place>): MutableList<Place>{
        list.sortWith(Comparator { p0, p1 ->
            var res = -1
            if (p0!!.name > p1!!.name) {
                res = 1
            }
            res
        })

        return list
    }

    private fun descPlaceList(list: MutableList<Place>): MutableList<Place>{
        list.sortWith(Comparator { p0, p1 ->
            var res = -1
            if (p0!!.name < p1!!.name) {
                res = 1
            }
            res
        })

        return list
    }

    private fun highRPlaceList(list: MutableList<Place>): MutableList<Place>{
        list.sortWith(Comparator { p0, p1 ->
            var res = -1
            if (p0!!.ratingValue < p1!!.ratingValue) {
                res = 1
            }
            res
        })

        return list
    }

    private fun lowRPlaceList(list: MutableList<Place>): MutableList<Place>{
        list.sortWith(Comparator { p0, p1 ->
            var res = -1
            if (p0!!.ratingValue > p1!!.ratingValue) {
                res = 1
            }
            res
        })

        return list
    }

    private fun filterState(list: MutableList<Place>,state: String) : MutableList<Place>{
        val resultList = mutableListOf<Place>()
        for (data in list){
            if (data.state == state){
                resultList.add(data)
            }
        }
        return resultList
    }




    private fun setUpSearchResultRecycler(){
        searchPlaceListAdapter = SearchPlaceListAdapter(this, mutableListOf())
        search_recycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        search_recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.TOP)
        snapHelperStart.attachToRecyclerView(search_recycler)
        search_recycler.isNestedScrollingEnabled = false
        search_recycler.adapter = searchPlaceListAdapter

    }

    @SuppressLint("ShowToast")
    private fun setUpObserver() {
        viewModel.fetchPlaceBySearch().observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    search_placeholder.startShimmer()
                    search_placeholder.visibility = View.VISIBLE
                    search_recycler.visibility = View.GONE
                }
                is Resource.Success -> {
                    search_placeholder.stopShimmer()
                    search_placeholder.visibility = View.GONE
                    search_recycler.visibility = View.VISIBLE
//                    searchPlaceListAdapter.setItems(it.data)
                    placeList.clear()
                    for (data in it.data){
                        if (placeList.size < it.data.size ){
                            placeList.add(data)
                        }
                    }
                    searchPlaces(search_input.text.toString())
                }
                is Resource.Failure -> {
                    search_placeholder.stopShimmer()
                    search_placeholder.visibility = View.GONE
                    search_recycler.visibility = View.GONE
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
        val i = Intent(this, PlaceDetailsActivity::class.java)
        i.putExtra("place_id", place_id)
        startActivity(i)
    }

    override fun onFinishDialog(sort: String, staet: String) {
        this.sort = sort
        this.state = staet
        searchPlaces("")
    }

    override fun onResume() {
        super.onResume()
        search_placeholder.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        search_placeholder.stopShimmer()
    }
}