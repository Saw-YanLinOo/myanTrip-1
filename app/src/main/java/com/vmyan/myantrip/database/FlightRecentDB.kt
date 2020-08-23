package com.vmyan.myantrip.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vmyan.myantrip.dao.FlightRecentDao
import com.vmyan.myantrip.model.flight.FlightRecentItem
import kotlinx.coroutines.CoroutineScope

@Database(entities = [FlightRecentItem::class],version = 1)
abstract class FlightRecentDB : RoomDatabase() {
    abstract fun flightRecentDao(): FlightRecentDao

    companion object {
        @Volatile
        private var INSTANCE: FlightRecentDB? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): FlightRecentDB {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        FlightRecentDB::class.java,
                        "flight_recentDB"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                instance


            }
        }
    }
}