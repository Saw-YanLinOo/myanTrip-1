package com.vmyan.myantrip.asyncTask

import android.os.AsyncTask
import com.vmyan.myantrip.dao.CarRentalDao
import com.vmyan.myantrip.dao.TrainRecentDao
import com.vmyan.myantrip.model.train.TrainRecentItem

class DeleteAllTrainWordAsyncTask internal constructor(dao: TrainRecentDao) :
    AsyncTask<Void?, Void?, Void?>() {
    private val mAsyncTaskDao: TrainRecentDao = dao


    override fun doInBackground(vararg p0: Void?): Void? {
        mAsyncTaskDao.clearAll()
        return null
    }
}