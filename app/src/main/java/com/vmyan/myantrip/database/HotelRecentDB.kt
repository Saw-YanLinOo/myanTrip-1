package com.vmyan.myantrip.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vmyan.myantrip.dao.RecentDao
import com.vmyan.myantrip.model.hotel.HotelRecentItem
import kotlinx.coroutines.CoroutineScope

@Database(entities = [HotelRecentItem::class],version = 3)
abstract class HotelRecentDB : RoomDatabase(){
    abstract fun recentDao() : RecentDao

    companion object{
        @Volatile
        private var INSTANCE : HotelRecentDB?=null

        fun getDatabase(
            context : Context,
            scope: CoroutineScope
        ): HotelRecentDB {
            return INSTANCE ?: synchronized(this){
                val instance=Room.databaseBuilder(context.applicationContext,
                    HotelRecentDB::class.java,"hotel_recentDB")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE =instance
                instance


            }
        }

//        private class HotelRecentDBCallback(
//            private val scope:CoroutineScope
//        ):RoomDatabase.Callback(){
//            override fun onOpen(db: SupportSQLiteDatabase) {
//                super.onOpen(db)
//                INSTANCE?.let { database ->
//                    scope.launch(Dispatchers.IO) {
//                        populateDatabase(database.recentDao())
//                    }
//                }
//            }
//            fun populateDatabase(recentDao: RecentDao) {
//                // Start the app with a clean database every time.
//                // Not needed if you only populate on creation.
//                recentDao.clearAll()
//
//                var items1 = HotelRecentItem("Yangon","12/1/2020","23/3/2020","4","2")
//                recentDao.insert(items1)
//                 items1 = HotelRecentItem("Ye","12/1/2020","23/3/2020","4","2")
//
//                recentDao.insert(items1)
//
//            }
//        }



    }


}