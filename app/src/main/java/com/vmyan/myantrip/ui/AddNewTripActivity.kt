package com.vmyan.myantrip.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.viewmodel.AddNewTripViewModel
import com.vmyan.myantrip.utils.DateRange
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_add_new_trip.*
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.ExperimentalTime


@ExperimentalTime
class AddNewTripActivity : AppCompatActivity() {


    private val viewModel: AddNewTripViewModel by inject()

    private var imgUri: ByteArray? = null
    private var startDate: Timestamp? = null
    private var endDate: Timestamp? = null

    @RequiresApi(Build.VERSION_CODES.O)
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
            datePicker("start")

        }

        enddate_input.setOnClickListener {
            datePicker("end")
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
                    userId, userName, userImg,0
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

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalTime
    private fun datePicker(status: String){
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setCalendarConstraints(DateRange.limitRange(SimpleDateFormat("dd-MM-yyyy").format(Date()), "31-12-2022" ,"datefun")!!.build())
        val picker = builder.build()
        picker.show(supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            when(status){
                "start" -> {
                    startdate_input.text = picker.headerText
                    startDate = Timestamp(Date(it))
                }
                "end" -> {
                    enddate_input.text = picker.headerText
                    endDate = Timestamp(Date(it))
                }
            }
        }
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
        userId: String,
        userName: String,
        userImg: String,
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
            userName,
            userImg,
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