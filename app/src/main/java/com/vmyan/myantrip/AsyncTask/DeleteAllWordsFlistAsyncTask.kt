package com.vmyan.myantrip.AsyncTask

import android.os.AsyncTask
import com.vmyan.myantrip.dao.FlightRecentDao

class DeleteAllWordsFlistAsyncTask internal constructor(dao: FlightRecentDao) :
    AsyncTask<Void?, Void?, Void?>() {
    private val mAsyncTaskDao: FlightRecentDao = dao
    override fun doInBackground(vararg p0: Void?): Void? {
        mAsyncTaskDao.clearAll()
        return null
    }

}