package com.vmyan.myantrip.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.customui.switchdatetime.SwitchDateTimeDialogFragment
import com.vmyan.myantrip.ui.viewmodel.AddNewTripViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_add_new_trip.*
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddNewTripActivity : AppCompatActivity() {


    private val viewModel: AddNewTripViewModel by inject()

    private var imgUri: ByteArray? = null
    private var startDate: Timestamp? = null
    private var endDate: Timestamp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_trip)
        val img = Uri.parse(
            "android.resource://" + R::class.java.getPackage()
                .name + "/" + R.drawable.trip_defaultimg
        )
        compressImage(img)


        pickimg_card.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK)
            i.type = "image/*"
            startActivityForResult(i, 1)
        }

        pickimg_ly.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK)
            i.type = "image/*"
            startActivityForResult(i, 1)
        }

        pickimg_btn.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK)
            i.type = "image/*"
            startActivityForResult(i, 1)
        }

        tripclear_btn.setOnClickListener {
            clearAll()
        }

        startdate_input.setOnClickListener {
            pickCustDate("start")

        }

        enddate_input.setOnClickListener {
            pickCustDate("end")
        }

        tripadd_btn.setOnClickListener {
            val userId = Hawk.get<String>("user_id")
            val userName = Hawk.get<String>("user_name")
            val userImg = Hawk.get<String>("user_profile")
            if (this.imgUri != null && destination_input.text !=null && this.startDate != null && this.endDate != null && triptype_input.text != null && tripname_input.text != null && description_input.text != null && userId != null && userName != null && userImg != null){
                addNewTrip(
                    this.imgUri!!,
                    destination_input.text.toString(),
                    this.startDate!!,
                    this.endDate!!,
                    triptype_input.text.toString(),
                    tripname_input.text.toString(),
                    description_input.text.toString(),
                    arrayListOf(userId)
                    ,0
                )
            }
            clearAll()
        }

        home.setOnClickListener { onBackPressed() }

    }

    private fun clearAll() {
        pickimg_ly.visibility = View.VISIBLE
        pickimg_btn.visibility = View.VISIBLE
        tripimg.visibility = View.GONE
        destination_input.setText("")
        startdate_input.text = "Start Date"
        enddate_input.text = "End Date"
        tripname_input.setText("")
        triptype_input.setText("")
        description_input.setText("")
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                pickimg_ly.visibility = View.GONE
                pickimg_btn.visibility = View.GONE
                tripimg.visibility = View.VISIBLE

                if (data != null) {
                    if (data.data !=null){
                        tripimg.setImageURI(data.data)
                        compressImage(data.data!!)
                    }
                }

            }
        }

    }


    private fun pickCustDate(status: String) {
        val picker = SwitchDateTimeDialogFragment.newInstance("Pick Date & Time For Your Plan", "SET", "CANCEL")
        picker.startAtCalendarView()
        picker.setTimeZone(TimeZone.getDefault())
        picker.minimumDateTime = Date()

        picker.setOnButtonClickListener(object : SwitchDateTimeDialogFragment.OnButtonClickListener{
            @SuppressLint("SimpleDateFormat")
            override fun onPositiveButtonClick(date: Date?) {
                when(status){
                    "start" -> {
                        startdate_input.text = SimpleDateFormat("MMM, dd yyyy").format(date)
                        startDate = Timestamp(date!!)
                    }
                    "end" -> {
                        enddate_input.text = SimpleDateFormat("MMM, dd yyyy").format(date)
                        endDate = Timestamp(date!!)
                    }
                }
            }

            override fun onNegativeButtonClick(date: Date?) {
                println()
            }

        })
        picker.show(supportFragmentManager,"pick date")
    }




    private fun compressImage(imgUri: Uri){
        val bmp = MediaStore.Images.Media.getBitmap(contentResolver, imgUri)
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos)
        val data: ByteArray = baos.toByteArray()
        this.imgUri = data
    }


    private fun addNewTrip(
        tripImgUri: ByteArray,
        tripDestination: String,
        tripStartDate: Timestamp,
        tripEndDate: Timestamp,
        tripType: String,
        tripName: String,
        tripDesc: String,
        userId: ArrayList<String>,
        tripCost: Int
    ){
        viewModel.addNewTrip(
            tripImgUri,
            tripDestination,
            tripStartDate,
            tripEndDate,
            tripType,
            tripName,
            tripDesc,
            userId,
            tripCost
        ).observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Loading -> {
                    formly.visibility = View.GONE
                    addtrip_progressbar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
//                    formly.visibility = View.VISIBLE
//                    addtrip_progressbar.visibility = View.GONE
                    onBackPressed()
                }

                is Resource.Failure -> {

                }
            }
        })
    }


}