package com.minbanyar.testbooking.AsyncTask

import android.os.AsyncTask
import com.vmyan.myantrip.dao.RecentDao

 class DeleteAllWordsAsyncTask internal constructor(dao: RecentDao) :
    AsyncTask<Void?, Void?, Void?>() {
    private val mAsyncTaskDao: RecentDao = dao


     override fun doInBackground(vararg p0: Void?): Void? {
        mAsyncTaskDao.clearAll()
        return null
    }
}