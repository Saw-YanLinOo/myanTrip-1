package com.vmyan.myantrip.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vmyan.myantrip.dao.BusRecentDao
import com.vmyan.myantrip.dao.FlightRecentDao
import com.vmyan.myantrip.model.bus.BusRecentItem
import com.vmyan.myantrip.model.flight.FlightRecentItem
import kotlinx.coroutines.CoroutineScope

@Database(entities = [BusRecentItem::class],version = 1)
abstract class BusRecentDB : RoomDatabase() {
    abstract fun busRecentDao():BusRecentDao

    companion object {
        @Volatile
        private var INSTANCE: BusRecentDB? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): BusRecentDB {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        BusRecentDB::class.java,
                        "bus_recentDB"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                instance


            }
        }
    }
}