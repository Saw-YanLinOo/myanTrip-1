package com.vmyan.myantrip.asyncTask

import android.os.AsyncTask
import com.vmyan.myantrip.dao.CarRentalDao
import com.vmyan.myantrip.dao.RecentDao

class DeleteAllCarRentalWordAsynTask internal constructor(dao: CarRentalDao) :
    AsyncTask<Void?, Void?, Void?>() {
    private val mAsyncTaskDao: CarRentalDao = dao


    override fun doInBackground(vararg p0: Void?): Void? {
        mAsyncTaskDao.clearAll()
        return null
    }
}