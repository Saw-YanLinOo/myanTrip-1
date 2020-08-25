package com.vmyan.myantrip.asyncTask

import android.os.AsyncTask
import com.vmyan.myantrip.dao.BusRecentDao

class DeleteAllBusWordsAsyncTask internal constructor(dao: BusRecentDao) :
    AsyncTask<Void?, Void?, Void?>() {
    private val mAsyncTaskDao: BusRecentDao = dao
    override fun doInBackground(vararg p0: Void?): Void? {
        mAsyncTaskDao.clearAll()
        return null
    }
}