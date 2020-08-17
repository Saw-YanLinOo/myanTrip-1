package com.vmyan.myantrip.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Timestamp
import com.noowenz.customdatetimepicker.CustomDateTimePicker
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.viewmodel.AddNewTripVMFactory
import com.vmyan.myantrip.ui.viewmodel.AddNewTripViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_add_new_trip.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance
import java.io.ByteArrayOutputStream
import java.util.*


class AddNewTripActivity : AppCompatActivity(), DIAware {

    override val di: DI by closestDI()
    private val viewModelFactory : AddNewTripVMFactory by instance()

    private lateinit var viewModel: AddNewTripViewModel

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

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddNewTripViewModel::class.java)

        pickimg_card.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK)
            i.type = "image/*"
            startActivityForResult(i,1)
        }

        pickimg_ly.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK)
            i.type = "image/*"
            startActivityForResult(i,1)
        }

        pickimg_btn.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK)
            i.type = "image/*"
            startActivityForResult(i,1)
        }

        tripclear_btn.setOnClickListener {
            clearAll()
        }

        startdate_input.setOnClickListener {
            showDateTimePicker("start")

        }

        enddate_input.setOnClickListener {
            showDateTimePicker("end")

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
                    userId, userName, userImg
                )
            }
            clearAll()
        }



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

    private fun compressImage(imgUri: Uri){
        val bmp = MediaStore.Images.Media.getBitmap(contentResolver, imgUri)
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos)
        val data: ByteArray = baos.toByteArray()
        this.imgUri = data
    }

    private fun showDateTimePicker(status: String) {

        CustomDateTimePicker(this, object : CustomDateTimePicker.ICustomDateTimeListener{
            override fun onCancel() {

            }

            @SuppressLint("SetTextI18n")
            override fun onSet(
                dialog: Dialog,
                calendarSelected: Calendar,
                dateSelected: Date,
                year: Int,
                monthFullName: String,
                monthShortName: String,
                monthNumber: Int,
                day: Int,
                weekDayFullName: String,
                weekDayShortName: String,
                hour24: Int,
                hour12: Int,
                min: Int,
                sec: Int,
                AM_PM: String
            ) {
                when(status){
                    "start" -> {
                        startdate_input.text = "$monthShortName $day, $year"
                        startDate = Timestamp(dateSelected)
                    }
                    "end" -> {
                        enddate_input.text = "$monthShortName $day, $year"
                        endDate = Timestamp(dateSelected)
                    }
                }
            }
        }).apply {
            set24HourFormat(false)
            setMaxMinDisplayDate(
                minDate = Calendar.getInstance().apply { add(Calendar.MINUTE, 5) }.timeInMillis,
                maxDate = Calendar.getInstance().apply { add(Calendar.YEAR, 1) }.timeInMillis
            )
            setMaxMinDisplayedTime(5)
            showDialog()
        }

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
        userImg: String
    ){
        viewModel.addNewTrip(tripImgUri, tripDestination, tripStartDate, tripEndDate, tripType, tripName, tripDesc, userId, userName, userImg).observe(this, androidx.lifecycle.Observer {
            when(it) {
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