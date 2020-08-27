package com.vmyan.myantrip.ui.booking.bus

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.booking.carRental.bookingCate.BookingCategoriesRepositoryImpl
import com.vmyan.myantrip.model.bookingCate.TownListItem
import com.vmyan.myantrip.ui.adapter.TownListAdapter
import com.vmyan.myantrip.ui.viewmodel.bookingCate.BookingCateVMFactory
import com.vmyan.myantrip.ui.viewmodel.bookingCate.BookingCateViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_city_from.*
import kotlinx.android.synthetic.main.activity_city_from.rv_CityFrom
import kotlinx.android.synthetic.main.activity_city_to.*
import kotlinx.android.synthetic.main.activity_staying_place.*
import kotlinx.android.synthetic.main.activity_staying_place.searchTown
import kotlinx.android.synthetic.main.activity_staying_place.townList_Placeholder
import java.util.*

class CityTo : AppCompatActivity() ,TownListAdapter.ItemClickListener{
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            BookingCateVMFactory(BookingCategoriesRepositoryImpl())
        ).get(
            BookingCateViewModel::class.java
        )
    }
    private val townList = mutableListOf<TownListItem>()
    private lateinit var layoutManager: LinearLayoutManager
    lateinit var cityListAdapter: TownListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_to)
        setUpObserver()
        setUpCityList()
        imgCityToBack.setOnClickListener {
            this.finish()
        }
        searchToTown.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchPlaces(searchToTown.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


        cityTo_S_btn_ly.setOnClickListener {
            openVoiceCommand()
        }
        cityTo_S_btn.setOnClickListener {
            openVoiceCommand()
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
                        searchTown.setText(resulttxt)
                    }
                }
            }
        }
    }


    private fun searchPlaces(nameS: String) {
        var name = nameS
        if (name.isNotEmpty()) name =
            name.substring(0, 1).toUpperCase(Locale.ROOT) + name.substring(1).toLowerCase(Locale.ROOT)
        val results: MutableList<TownListItem> = mutableListOf()
        for (user in townList) {
            if (user.townName.contains(name)) {
                results.add(user)
            }
        }

        cityListAdapter.setItems(results)

    }

    private fun setUpCityList(){
        cityListAdapter = TownListAdapter(this, mutableListOf())
        rv_CityTo.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rv_CityTo.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }
        rv_CityTo.isNestedScrollingEnabled=false
        rv_CityTo.adapter = cityListAdapter

    }
    @SuppressLint("ShowToast")
    private fun setUpObserver() {
        viewModel.fetchCityList().observe(this,{
            when(it){
                is Resource.Loading -> {
                    townList_Placeholder.startShimmer()
                    townList_Placeholder.visibility = View.VISIBLE
                    rv_CityTo.visibility = View.GONE
                }
                is Resource.Success -> {
                    townList_Placeholder.stopShimmer()
                    townList_Placeholder.visibility = View.GONE
                    rv_CityTo.visibility = View.VISIBLE
                    townList.addAll(it.data)
                    cityListAdapter.setItems(it.data)


                }
                is Resource.Failure -> {
                    townList_Placeholder.startShimmer()
                    townList_Placeholder.visibility = View.GONE
                    rv_CityTo.visibility = View.GONE
                }
            }
        })
    }



    override fun onItemClick(cityName: String,cityImg : String) {
        var name =cityName
        var img =cityImg

        val intent = Intent().apply {
            putExtra(TITLE, name)
            putExtra(IMAGE,img)
        }

        setResult(Activity.RESULT_OK, intent)
        finish()
    }
    companion object {
        private const val REQUEST_CODE = 1
        const val TITLE = "title"
        const val IMAGE ="cityImage"
        const val ID = "id"

        fun getIntent(context: Context, postId: Int): Intent {
            return Intent(context, CityTo::class.java).apply {
                putExtra(ID, postId)
            }
        }
    }
}