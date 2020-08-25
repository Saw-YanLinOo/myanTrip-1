package com.vmyan.myantrip.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vmyan.myantrip.dao.CarRentalDao
import com.vmyan.myantrip.dao.RecentDao
import com.vmyan.myantrip.model.carRental.CarRentailsItem
import com.vmyan.myantrip.model.carRental.CarRentalRecentItem
import com.vmyan.myantrip.model.hotel.HotelRecentItem
import kotlinx.coroutines.CoroutineScope

@Database(entities = [CarRentalRecentItem::class],version = 2)
abstract class CarRecentDB : RoomDatabase(){
    abstract fun recentDao() : CarRentalDao

    companion object{
        @Volatile
        private var INSTANCE : CarRecentDB?=null

        fun getDatabase(
            context : Context,
            scope: CoroutineScope
        ): CarRecentDB {
            return INSTANCE ?: synchronized(this){
                val instance= Room.databaseBuilder(context.applicationContext,
                    CarRecentDB::class.java,"car_recentDB")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE =instance
                instance


            }
        }

    }
}