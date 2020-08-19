package com.vmyan.myantrip.utils

import android.annotation.SuppressLint
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.android.material.datepicker.CalendarConstraints
import java.text.SimpleDateFormat
import java.util.*

object DateRange {

    fun setAMPM(hour: Int, min: Int): String{
        var status = "AM"
        if (hour > 11) {
            status = "PM"
        }
        val hour_of_12_hour_format: Int

        if (hour > 11) {
            hour_of_12_hour_format = hour - 12
        } else {
            hour_of_12_hour_format = hour
        }

        return "$hour_of_12_hour_format:$min $status"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
     fun limitRange(sDate: String, eDate: String, type: String): CalendarConstraints.Builder? {
        val constraintsBuilderRange = CalendarConstraints.Builder()
        val calendarStart = Calendar.getInstance()
        val calendarEnd = Calendar.getInstance()

        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        val endD = formatter.parse(eDate)
        val sD = formatter.parse(sDate)

        val y = SimpleDateFormat("yyyy").format(sD!!)
        val m = SimpleDateFormat("MM").format(sD)
        val d = SimpleDateFormat("dd").format(sD)
        val startYear = y.toInt()
        val startMonth = m.toInt()
        val startDate = d.toInt()
        val ey = SimpleDateFormat("yyyy").format(endD!!)
        val em = SimpleDateFormat("MM").format(endD)
        val ed = SimpleDateFormat("dd").format(endD)
        println("$ey $em $ed")
        val endYear = ey.toInt()
        val endMonth = em.toInt()
        val endDate = ed.toInt()

        if (type == "datefun"){
            calendarStart[startYear, startMonth - 1] = startDate
            calendarEnd[endYear, endMonth - 1] = endDate
        }else{
            calendarStart[startYear, startMonth - 1] = startDate
            calendarEnd[endYear, endMonth - 1] = endDate +1
        }

        val minDate = calendarStart.timeInMillis
        val maxDate = calendarEnd.timeInMillis
        constraintsBuilderRange.setStart(minDate)
        constraintsBuilderRange.setEnd(maxDate)
        constraintsBuilderRange.setValidator(RangeValidator(minDate, maxDate))
        return constraintsBuilderRange
    }


    internal class RangeValidator : CalendarConstraints.DateValidator {
        var minDate: Long
        var maxDate: Long

        constructor(minDate: Long, maxDate: Long) {
            this.minDate = minDate
            this.maxDate = maxDate
        }

        constructor(parcel: Parcel) {
            minDate = parcel.readLong()
            maxDate = parcel.readLong()
        }

        override fun isValid(date: Long): Boolean {
            return !(minDate > date || maxDate < date)
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeLong(minDate)
            dest.writeLong(maxDate)
        }

        companion object {
            val CREATOR: Parcelable.Creator<RangeValidator?> =
                object : Parcelable.Creator<RangeValidator?> {
                    override fun createFromParcel(parcel: Parcel): RangeValidator? {
                        return RangeValidator(parcel)
                    }

                    override fun newArray(size: Int): Array<RangeValidator?> {
                        return arrayOfNulls(size)
                    }
                }
        }
    }
}